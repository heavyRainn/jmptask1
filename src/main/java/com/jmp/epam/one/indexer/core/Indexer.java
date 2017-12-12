package com.jmp.epam.one.indexer.core;

import com.jmp.epam.one.indexer.scanner.impl.FileSystemScannerImpl;
import com.jmp.epam.one.indexer.searcher.FileSystemSearcher;
import com.jmp.epam.one.indexer.writer.FileSystemWorker;
import com.jmp.epam.one.utils.IndexerUtils;
import com.jmp.epam.one.utils.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Indexer extends Thread {

    @Value("${indexation.file.name}")
    private String indexationFilename;

    @Autowired
    private FileSystemWorker fileSystemWorker;

    @Autowired
    private FileSystemSearcher fileSystemSearcher;

    @Autowired
    private FileSystemScannerImpl fileSystemScanner;
    private Map<String, String> indexes = new HashMap<>();

    @Override
    public void run() {

        boolean isWorking = Boolean.TRUE;
        UserInterface.greet();
        String userChoice = UserInterface.waitingForUserEnter();

        while (isWorking) switch (Action.convertFromString(userChoice)) {
            case SCAN:
                scan();
                userChoice = UserInterface.waitingForUserEnter();
                break;
            case SEARCH:
                search(indexes);
                userChoice = UserInterface.waitingForUserEnter();
                break;
            case SHOW_CACHE:
                indexes = fileSystemWorker.read();
                UserInterface.printResult(indexes);
                userChoice = UserInterface.waitingForUserEnter();
                break;
            case EXIT:
                UserInterface.exit();
                Runtime.getRuntime().halt(IndexerUtils.ZERO);
                isWorking = Boolean.FALSE;
                break;
            case DEFAULT:
                userChoice = UserInterface.invalidUserInput();
        }

    }

    private void search(Map<String, String> indexes) {
        if (indexes.isEmpty()) {
            UserInterface.invalidSearch();
            return;
        }

        UserInterface.search();
        String inputToSearch = UserInterface.getUserInputToSearch();

        Map<String, String> searchingResults = fileSystemSearcher.find(indexes, inputToSearch);

        if (searchingResults.size() != 0) {
            UserInterface.notifyAboutFinishingSearching();
            UserInterface.printResult(searchingResults);
        } else {
            UserInterface.nothingFound();
        }
    }

    private void scan() {
        UserInterface.scan();

        executeScan();
        fileSystemWorker.write(indexationFilename, indexes);

        UserInterface.notifyAboutFinishingScanning();
        UserInterface.printResult(indexes);
    }

    private void executeScan() {
        fileSystemScanner.setDaemon(Boolean.TRUE);
        fileSystemScanner.setIndexes(indexes);
        fileSystemScanner.run();
    }

}
