package com.jmp.epam.one.indexer.core;

import com.jmp.epam.one.indexer.scanner.impl.FileSystemScannerImpl;
import com.jmp.epam.one.indexer.searcher.FileSystemSearcher;
import com.jmp.epam.one.indexer.writer.FileSystemWriter;
import com.jmp.epam.one.ui.UserInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Indexer extends Thread {

    private static final Logger logger = Logger.getLogger(Indexer.class);

    private static final String SCAN = "scan";
    private static final String SEARCH = "search";
    private static final String EXIT = "exit";

    @Value("${indexation.file.name}")
    private String indexationFilename;

    @Value("${timeout.of.daemon}")
    private Long timeout;

    @Autowired
    private FileSystemWriter fileSystemWriter;

    @Autowired
    private FileSystemSearcher fileSystemSearcher;

    @Autowired
    private UserInterface userInterface;

    @Autowired
    private Map<String, String> indexes;

    @Autowired
    private FileSystemScannerImpl fileSystemScanner;

    @Override
    public void run() {

        boolean isWorking = true;
        userInterface.greet();
        String userChoice = userInterface.waitingForUserEnter();

        while (isWorking) switch (userChoice) {
            case SCAN:
                scan();
                userChoice = userInterface.waitingForUserEnter();
                break;
            case SEARCH:
                search(indexes);
                userChoice = userInterface.waitingForUserEnter();
                break;
            case EXIT:
                userInterface.exit();

                isWorking = false;
                break;
            default:
                userChoice = userInterface.waitingForUserEnter();
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

    private synchronized void executeScan() {
        fileSystemScanner.setDaemon(true);

        try {
            fileSystemScanner.start();
        } catch (Exception e) {
            logger.error(e);
        }


    }

}
