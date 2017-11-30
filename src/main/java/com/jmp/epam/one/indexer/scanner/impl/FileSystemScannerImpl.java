package com.jmp.epam.one.indexer.scanner.impl;

import com.jmp.epam.one.indexer.scanner.FileSystemScanner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileSystemScannerImpl implements FileSystemScanner {

    @Override
    public Map<String, String> scan(String directoryToScan, int nestingLevel) {
        Map<String, String> indexes = new HashMap<>();
        Path directory = Paths.get(directoryToScan);

        scanDirectory(indexes, directory);

        return indexes;
    }

    private static void scanDirectory(Map<String, String> indexes, Path directory) {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {

            for (Path child : directoryStream) {
                if (child.toFile().isDirectory()) {
                    scanDirectory(indexes, child);
                } else {
                    indexes.put(child.getFileName().toString(), child.toString());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
