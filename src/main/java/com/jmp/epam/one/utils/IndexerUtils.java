package com.jmp.epam.one.utils;

public class IndexerUtils {
    public static final int ZERO = 0;

    public static String validate(String inputToSearch, int length) {
        String input = inputToSearch;

        if (inputToSearch.length() > length) {
            input = inputToSearch.substring(ZERO, length);
        }

        return input;
    }

}
