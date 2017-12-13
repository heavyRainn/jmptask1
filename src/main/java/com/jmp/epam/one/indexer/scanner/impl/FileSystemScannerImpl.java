package com.jmp.epam.one.indexer.scanner.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileSystemScannerImpl extends Thread {

    private static final Logger logger = Logger.getLogger(FileSystemScannerImpl.class);

    @Value("${directory.to.scan}")
    private String directoryToScan;

    @Value("${timeout.of.daemon}")
    private Long timeout;

    @Value("${nesting.level}")
    private int nestingLevel;
    private volatile Map<String, String> indexes = new HashMap<>();
    private final Object lock = new Object();

    @Override
    public void run() {
        if (StringUtils.isEmpty(directoryToScan)) {
            return;
        }

        scanDirectory(indexes, directoryToScan);
        synchronized (lock) {
            try {
                lock.wait(timeout);
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }

    }

    private void scanDirectory(Map<String, String> indexes, String directory) {
        try {
            Files.find(
                    Paths.get(directory),
                    nestingLevel,
                    (path, basicFileAttributes) -> basicFileAttributes.isRegularFile()).forEach(
                    path -> indexes.put(path.getFileName().toString(),
                            path.toString()
                    )
            );
            synchronized (lock) {
                lock.notify();
            }
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public void setIndexes(Map<String, String> indexes) {
        this.indexes = indexes;
    }

}
