package io.tchepannou.k.travel.dao;

import io.tchepannou.k.travel.domain.PriceType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceTypeDao extends CrudRepository<PriceType, Integer>{
    PriceType findByNameIgnoreCase(String name);
}
