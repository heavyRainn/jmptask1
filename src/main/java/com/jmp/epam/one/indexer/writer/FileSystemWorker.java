package com.jmp.epam.one.indexer.writer;

import java.util.Map;

public interface FileSystemWorker {

    void write(String writePath, Map<String, String> message);

    Map<String, String> read();

}
