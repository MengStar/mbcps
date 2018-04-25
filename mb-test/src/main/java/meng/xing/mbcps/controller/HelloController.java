package meng.xing.mbcps.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class HelloController {
    @GetMapping("/hello/{latency}")
    public Flux<String> hello(@PathVariable String latency) {
        return Flux.just(latency + " ! Welcome to reactive world ~", "1", "2", "3").log();
    }
}