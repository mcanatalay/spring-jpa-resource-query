package com.example;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository {
    @Query(value = "classpath:test.sql")
    String getDataWithResource();

    @Query(value = "classpath:unknown.sql")
    String getDataWithWrongResource();

    @Query(value = "SELECT data FROM test LIMIT 1")
    String getData();
}
