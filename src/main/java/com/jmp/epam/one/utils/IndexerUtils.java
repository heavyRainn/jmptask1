package com.jmp.epam.one.utils;

public class IndexerUtils {

    private static final Character SLASH = '\\';
    private static final int ZERO = 0;

    public static int getNumberOfSlashes(String s) {
        int counter = ZERO;

        for (int i = ZERO; i < s.length(); i++) {
            if (s.charAt(i) == SLASH) {
                counter++;
            }
        }

        return counter;
    }

    public static String validate(String inputToSearch, int length) {
        if(inputToSearch.length() > length){
            inputToSearch = inputToSearch.substring(ZERO, length);
        }

        return inputToSearch;
    }

}
