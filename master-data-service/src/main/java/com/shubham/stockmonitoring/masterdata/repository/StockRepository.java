package com.shubham.stockmonitoring.masterdata.repository;

import com.shubham.stockmonitoring.masterdata.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findBySymbol(String symbol);
    List<Stock> findBySector(String sector);
    List<Stock> findByExchange(String exchange);
    List<Stock> findByIsActiveTrue();
    
    @Query("SELECT s FROM Stock s WHERE s.name LIKE %:name% OR s.symbol LIKE %:name%")
    List<Stock> findByNameOrSymbolContaining(@Param("name") String name);
}
