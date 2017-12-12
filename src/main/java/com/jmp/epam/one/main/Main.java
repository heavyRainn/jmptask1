package com.jmp.epam.one.main;

import com.jmp.epam.one.config.SpringConfig;
import com.jmp.epam.one.indexer.core.Indexer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        Thread indexer = ctx.getBean(Indexer.class);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(indexer);
    }

}
