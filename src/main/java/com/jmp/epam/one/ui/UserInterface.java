package com.jmp.epam.one.ui;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;

import static java.lang.System.out;

@Component
public class UserInterface {

    private Scanner scanner = new Scanner(System.in);

    public void greet() {
        out.println("HI MAN !!!");
    }

    public String waitingForUserEnter() {
        out.println("-------------------------------------------------------------------------------------------------------------------/");
        out.print("Please enter your choice : ");

        return scanner.next().toLowerCase();
    }

    public String getUserInputToSearch() {
        out.print("Please enter file name you want to find : ");

        return scanner.next();
    }

    public void scan() {
        out.println("SCANNING ............");
    }

    public void search() {
        out.println("SEARCHING ..............");
    }

    public static void notifyAboutFinishingScanning() {
        out.println("SCAN SUCCESSFULLY FINISHED !!!");
    }

    public static void notifyAboutFinishingSearching() {
        out.println("SEARCHING SUCCESSFULLY FINISHED !!!");
    }

    public void printResult(Map<String, String> searchResults) {
        out.println("Was/were founded : " + searchResults.toString());
    }

    public void exit() {
        out.println("BYE BYE !!!");
    }

}
