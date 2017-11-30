package com.jmp.epam.one.indexer.searcher.impl;

import com.jmp.epam.one.indexer.searcher.FileSystemSearcher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class FileSystemSearcherImpl implements FileSystemSearcher {

    @Override
    public Map<String, String> find(Map<String, String> indexes, String inputToSearch) {
        return indexes
                .entrySet().stream()
                .filter(element -> checkByRegexp(inputToSearch, element.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private boolean checkByRegexp(String inputToSearch, String key) {
        Pattern pattern = Pattern.compile(inputToSearch);
        Matcher matcher = pattern.matcher(key);

        return matcher.find();
    }

}
