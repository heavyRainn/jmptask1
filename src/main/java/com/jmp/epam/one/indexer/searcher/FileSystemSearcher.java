package com.jmp.epam.one.indexer.searcher;

import java.util.Map;

public interface FileSystemSearcher {

    Map<String,String> find(Map<String, String> indexes, String inputToSearch);

}
