package com.shubham.stockmonitoring.masterdata.repository;

import com.shubham.stockmonitoring.masterdata.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
    Optional<Sector> findByCode(String code);
    List<Sector> findByIsActiveTrue();
}
