package com.jetbrains.test.boot4.server.quote;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface QuoteRepository extends CrudRepository<QuoteEntity, Long> {

    @Query("SELECT * FROM quote ORDER BY RANDOM() LIMIT 1")
    Optional<QuoteEntity> findRandom();
}
