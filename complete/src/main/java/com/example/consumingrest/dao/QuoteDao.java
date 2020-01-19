package com.example.consumingrest.dao;

import com.example.consumingrest.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteDao extends JpaRepository<Quote, Integer> {
    Quote findById(int id);

}
