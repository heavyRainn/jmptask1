package com.jmp.epam.one.indexer.scanner.impl;

import com.jmp.epam.one.indexer.scanner.FileSystemScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

@Component
public class FileSystemScannerImpl implements FileSystemScanner {

    private static final Logger logger = Logger.getLogger(FileSystemScannerImpl.class);
    private static final Character SLASH = '\\';

    @Value("${directory.to.scan}")
    private String directoryToScan;

    @Value("${nesting.level}")
    private int nestingLevel;
    private int numberOfSlashesInPath;

    @Override
    public Map<String, String> scan() {
        Map<String, String> indexes = new HashMap<>();
        Path directory = Paths.get(directoryToScan);
        numberOfSlashesInPath = getNumberOfSlashes(directory.toString());

        scanDirectory(indexes, directory);

        return indexes;
    }

    private void scanDirectory(Map<String, String> indexes, Path directory) {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {

            for (Path child : directoryStream) {
                if (child.toFile().isDirectory() && isDeeperThanNestingLevel(child.toString())) {
                    scanDirectory(indexes, child);
                } else {
                    indexes.put(child.getFileName().toString(), child.toString());
                    logger.info(child.toString());
                }
            }

        } catch (IOException e) {
            logger.error(e);
        }
    }

    private boolean isDeeperThanNestingLevel(String scanningDirectory) {
        return getNumberOfSlashes(scanningDirectory) - numberOfSlashesInPath < nestingLevel;
    }



    private int getNumberOfSlashes(String s) {
        int counter = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == SLASH) {
                counter++;
            }
        }

        return counter;
    }


}
