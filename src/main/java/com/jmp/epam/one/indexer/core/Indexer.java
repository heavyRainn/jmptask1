package com.jmp.epam.one.indexer.core;

import com.jmp.epam.one.indexer.searcher.FileSystemSearcher;
import com.jmp.epam.one.indexer.writer.FileSystemWriter;
import com.jmp.epam.one.ui.UserInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

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
    private Callable<Map<String, String>> fileSystemScanner;

    @Autowired
    private FileSystemWriter fileSystemWriter;

    @Autowired
    private FileSystemSearcher fileSystemSearcher;

    @Autowired
    private UserInterface userInterface;

    @Override
    public void run() {
        Map<String, String> indexes = new HashMap<>();

        boolean isWorking = true;
        userInterface.greet();
        String userChoice = userInterface.waitingForUserEnter();

        while (isWorking) switch (userChoice) {
            case SCAN:
                indexes = scan();
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


    private Map<String, String> scan() {
        userInterface.scan();

        Map<String, String> indexes = executeCallable();

        fileSystemWriter.write(indexationFilename, indexes.toString());
        UserInterface.notifyAboutFinishingScanning();

        userInterface.printResult(indexes);
        return indexes;
    }

    private Map<String,String> executeCallable() {
        Map<String, String> indexes = null;

        try {
            //((Thread)fileSystemScanner).setDaemon(true); TODO we should make setDaemon(true)
            indexes = fileSystemScanner.call();
            fileSystemScanner.wait(timeout);
        } catch (Exception e) {
            logger.error(e);
        }

        return indexes;
    }

}
