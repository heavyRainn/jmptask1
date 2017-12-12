package com.jmp.epam.one.indexer.writer.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmp.epam.one.indexer.scanner.impl.FileSystemScannerImpl;
import com.jmp.epam.one.indexer.writer.FileSystemWorker;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Component
public class FileSystemWorkerImpl implements FileSystemWorker {

    private static final Logger logger = Logger.getLogger(FileSystemScannerImpl.class);

    @Value("${indexation.file.name}")
    private String indexationFilename;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void write(String writePath, Map<String, String> content) {
        try {
            objectMapper.writeValue(new File(indexationFilename), content);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Override
    public Map<String, String> read() {
        Map<String, String> cachedIndexes = Collections.emptyMap();

        try {
            cachedIndexes = objectMapper.readValue(new File(indexationFilename), new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            logger.error(e);
        }

        return cachedIndexes;
    }

}
