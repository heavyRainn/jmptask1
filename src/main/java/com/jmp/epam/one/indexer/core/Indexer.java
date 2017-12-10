package com.jmp.epam.one.indexer.core;

import com.jmp.epam.one.indexer.scanner.impl.FileSystemScannerImpl;
import com.jmp.epam.one.indexer.searcher.FileSystemSearcher;
import com.jmp.epam.one.indexer.writer.FileSystemWriter;
import com.jmp.epam.one.ui.UserInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Indexer extends Thread {

    private static final Logger logger = Logger.getLogger(Indexer.class);

    @Value("${indexation.file.name}")
    private String indexationFilename;

    @Autowired
    private FileSystemWriter fileSystemWriter;

    @Autowired
    private FileSystemSearcher fileSystemSearcher;

    @Autowired
    private UserInterface userInterface;

    @Autowired
    private FileSystemScannerImpl fileSystemScanner;
    private Map<String, String> indexes = new HashMap<>();

    @Override
    public void run() {

        boolean isWorking = true;
        userInterface.greet();
        String userChoice = userInterface.waitingForUserEnter();

        while (isWorking) switch (Action.convertFromString(userChoice)) {
            case SCAN:
                scan();
                userChoice = userInterface.waitingForUserEnter();
                break;
            case SEARCH:
                if (indexes.isEmpty()) {
                    userInterface.invalidSearch();
                } else {
                    search(indexes);
                }

                userChoice = userInterface.waitingForUserEnter();
                break;
            case EXIT:
                userInterface.exit();
                Runtime.getRuntime().halt(0);
                isWorking = false;
                break;
            default:
                userChoice = userInterface.invalidUserInput();
        }

    }

    private void search(Map<String, String> indexes) {
        userInterface.search();

        String inputToSearch = userInterface.getUserInputToSearch();
        Map<String, String> searchingResults = fileSystemSearcher.find(indexes, inputToSearch);

        if (searchingResults.size() != 0) {
            UserInterface.notifyAboutFinishingSearching();
            userInterface.printResult(searchingResults);
        } else {
            userInterface.nothingFound();
        }
    }


    private void scan() {
        userInterface.scan();

        executeScan();

        fileSystemWriter.write(indexationFilename, indexes.toString());
        UserInterface.notifyAboutFinishingScanning();

        userInterface.printResult(indexes);
    }

    private void executeScan() {
        fileSystemScanner.setDaemon(true);
        fileSystemScanner.setIndexes(indexes);
        fileSystemScanner.run();
    }


}
