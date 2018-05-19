package meng.xing.chat;

import meng.xing.chat.entity.Quote;
import meng.xing.chat.repository.QuoteMongoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.LongSupplier;

@Component
public class QuoteDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(QuoteDataLoader.class);
    private final QuoteMongoRepository quoteMongoRepository;

    @Autowired
    public QuoteDataLoader(QuoteMongoRepository quoteMongoRepository) {
        this.quoteMongoRepository = quoteMongoRepository;
    }

    @Override
    public void run(final String... args) {
        long count = quoteMongoRepository.count().block();
        if (count == 0L) {
            final LongSupplier longSupplier = new LongSupplier() {
                Long l = 0L;

                @Override
                public long getAsLong() {
                    return l++;
                }
            };
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(getClass().getClassLoader().getResourceAsStream("pg2000.txt")));
            Flux.fromStream(bufferedReader.lines().filter(l -> !l.trim().isEmpty())
                    .map(l -> quoteMongoRepository.save(new Quote(longSupplier.getAsLong(), "El Quijote", l))))
                    .subscribe(m -> log.info("New quote loaded: {}", m.block()));
            log.info("Mongo 现在有 {} 个entries.", quoteMongoRepository.count().block());
        } else {
            log.info("Mongo初始化完毕，现在有 {} 个entries.", count);
        }
    }

}
