package com.jmp.epam.one.indexer.core;

import com.jmp.epam.one.indexer.scanner.FileSystemScanner;
import com.jmp.epam.one.indexer.searcher.FileSystemSearcher;
import com.jmp.epam.one.indexer.writer.FileSystemWriter;
import com.jmp.epam.one.ui.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Indexer extends Thread {

    private static final String SCAN = "scan";
    private static final String SEARCH = "search";
    private static final String EXIT = "exit";

    @Value("${indexation.file.name}")
    private String indexationFilename;

    @Autowired
    private FileSystemScanner fileSystemScanner;

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
        UserInterface.notifyAboutFinishingSearching();

        userInterface.printResult(searchingResults);
    }


    private Map<String, String> scan() {
        userInterface.scan();

        Map<String, String> indexes = fileSystemScanner.scan();
        fileSystemWriter.write(indexationFilename, indexes.toString());
        UserInterface.notifyAboutFinishingScanning();

        userInterface.printResult(indexes);
        return indexes;
    }

}
