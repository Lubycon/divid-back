package com.lubycon.ourney.domains.trip.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MappingRepository extends JpaRepository<Mapping, Long> {
}
