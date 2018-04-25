package meng.xing.mbcps.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.time.Duration;

@RestController
public class HelloController {
    @GetMapping("/hello/{latency}")
    public Flux<String> hello(@PathVariable int latency) {
        return Flux.just("Welcome to reactive world ~","1","2","3").log();
    }
}