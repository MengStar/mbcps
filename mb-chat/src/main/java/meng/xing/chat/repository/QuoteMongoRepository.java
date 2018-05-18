package meng.xing.chat.repository;


import meng.xing.chat.entity.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
@Repository
public interface QuoteMongoRepository extends ReactiveCrudRepository<Quote, String> {

    @Query("{ seq: { $exists: true }}")
    Flux<Quote> retrieveAllQuotesPaged(final Pageable page);
}
