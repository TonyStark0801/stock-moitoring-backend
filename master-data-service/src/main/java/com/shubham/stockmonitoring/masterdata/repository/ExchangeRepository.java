package com.shubham.stockmonitoring.masterdata.repository;

import com.shubham.stockmonitoring.masterdata.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
    Optional<Exchange> findByCode(String code);
    List<Exchange> findByIsActiveTrue();
    List<Exchange> findByCountry(String country);
}
