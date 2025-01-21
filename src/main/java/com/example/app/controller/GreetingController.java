package com.example.app.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.format.datetime.standard.DateTimeFormatterFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/")
    public String greeting(Model model) {
        String hostname = System.getenv("HOSTNAME");
        if(hostname == null) {
            hostname = "default";
        }

        LocalDateTime now = LocalDateTime.now();
        String nowStr = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        model.addAttribute("name", hostname + " executed on k8s Updated: " + nowStr);
        return "index";
    }

    @GetMapping("/cpu-intensive")
    public String increaseCpuLoadParallel(@RequestParam(defaultValue = "10") int seconds,
                                          @RequestParam(defaultValue = "4") int threads) {
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        long endTime = System.currentTimeMillis() + seconds * 1000;

        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                while (System.currentTimeMillis() < endTime) {
                    Math.pow(Math.random(), Math.random());
                }
            });
        }

        executor.shutdown();
        String message = "CPU load increased for " + seconds + " seconds with " + threads + " threads";
        System.out.println(message);

        return "index";
    }

}
