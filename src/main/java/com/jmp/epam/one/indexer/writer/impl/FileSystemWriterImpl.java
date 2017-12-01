package com.jmp.epam.one.indexer.writer.impl;

import com.jmp.epam.one.indexer.scanner.impl.FileSystemScannerImpl;
import com.jmp.epam.one.indexer.writer.FileSystemWriter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileSystemWriterImpl implements FileSystemWriter {

    private static final Logger logger = Logger.getLogger(FileSystemScannerImpl.class);

    @Override
    public void write(String writePath, String message) {
        Path writeDirectory = Paths.get(writePath);

        try {
            Files.write(writeDirectory, message.getBytes());
        } catch (IOException e) {
            logger.error(e);
        }
    }

}
