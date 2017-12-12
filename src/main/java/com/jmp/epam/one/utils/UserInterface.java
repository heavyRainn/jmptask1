package com.jmp.epam.one.utils;

import java.util.Map;
import java.util.Scanner;

import static java.lang.System.out;

public class UserInterface {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void greet() {
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("---------------------------------------- HI MAN !!! ---------------------------------------------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("");
    }

    public static String waitingForUserEnter() {
        out.print("Please enter your choice : ");

        return SCANNER.next().toUpperCase();
    }

    public static String invalidUserInput(){

        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("---------------------------------- YOU HAVE ENTERED INVALID ACTION !!! --------------------------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.print("Please enter valid action : ");

        return SCANNER.next().toUpperCase();
    }

    public static String getUserInputToSearch() {
        out.print("Please enter file name you want to find : ");

        return SCANNER.next();
    }

    public static void scan() {
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("---------------------------------- SCANNING ......................................... -----------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
    }

    public static void search() {
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("---------------------------------- SEARCHING ......................................... ----------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
    }

    public static void notifyAboutFinishingScanning() {
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("---------------------------------- SCAN SUCCESSFULLY FINISHED !!! -------------------------------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
    }

    public static void notifyAboutFinishingSearching() {
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("---------------------------------- SEARCHING SUCCESSFULLY FINISHED !!! --------------------------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
    }

    public static void printResult(Map<String, String> searchResults) {
        for (Map.Entry<String, String> stringStringEntry : searchResults.entrySet()) {
            out.println(stringStringEntry);
        }
    }

    public static void exit() {
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("------------------------------------------- BYE BYE !!! -----------------------------------------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.println("-------------------------------------------------------------------------------------------------------------------/");
    }

    public static void nothingFound() {
        out.println("NOTHING WERE FOUND !!!");
    }

    public static void invalidSearch() {
        out.println("NO DATA TO SEARCH, FIRSTLY SEARCHING YOU SHOULD SCAN !!!");
    }
}
