package io.tchepannou.k.travel.dao;

import io.tchepannou.k.travel.domain.Price;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceDao extends CrudRepository<Price, Integer>{
    Price findByProductIdAndPriceTypeId(Integer productId, Integer priceId);
    List<Price> findByProductId(Integer productId);
}
