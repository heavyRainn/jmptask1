package com.jmp.epam.one.indexer.searcher.impl;

import com.jmp.epam.one.indexer.searcher.FileSystemSearcher;
import com.jmp.epam.one.ui.UserInterface;
import com.jmp.epam.one.utils.IndexerUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class FileSystemSearcherImpl implements FileSystemSearcher {

    @Value("${length.of.searching}")
    private int length;

    @Override
    public Map<String, String> find(Map<String, String> indexes, String inputToSearch) {
        return indexes
                .entrySet().stream()
                .filter(element -> checkByRegexp(inputToSearch, element.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private boolean checkByRegexp(String inputToSearch, String key) {
        String validatedInputToSearch = IndexerUtils.validate(inputToSearch, length);

        Pattern pattern = Pattern.compile(validatedInputToSearch);
        Matcher matcher = pattern.matcher(key);

        return matcher.find();
    }

}
