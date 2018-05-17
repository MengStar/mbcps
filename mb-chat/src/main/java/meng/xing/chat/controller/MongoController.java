package meng.xing.chat.controller;

import meng.xing.chat.entity.Quote;
import meng.xing.chat.repository.QuoteMongoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class MongoController {
    private static final Logger log = LoggerFactory.getLogger(MongoController.class);
    private final QuoteMongoRepository mongoRepository;

    @Autowired
    public MongoController(QuoteMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }
    @GetMapping("/quotes-reactive")
    public Flux<Quote> getQuoteFlux() {
        // If you want to use a shorter version of the Flux, use take(100) at the end of the statement below
        return mongoRepository.findAll();
    }

    @GetMapping("/quotes-reactive-paged")
    public Flux<Quote> getQuoteFlux(final @RequestParam(name = "page") int page,
                                    final @RequestParam(name = "size") int size) {
        return mongoRepository.retrieveAllQuotesPaged(PageRequest.of(page, size)).doOnComplete(()->log.info("分页查询完毕,page:{},size:{}",page,size));

    }
}
