package com.jmp.epam.one.main;

import com.jmp.epam.one.config.SpringConfig;
import com.jmp.epam.one.indexer.core.Indexer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final String FILENAME = "indexation.txt";

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        Thread indexer = ctx.getBean(Indexer.class);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(indexer);

        String content = new String(Files.readAllBytes(Paths.get(FILENAME)));
        System.out.println(content);
    }

}
