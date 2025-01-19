package com.example.app.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute("name", "Spring Boot");
        System.out.println("Greeting!!!!!!");
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
