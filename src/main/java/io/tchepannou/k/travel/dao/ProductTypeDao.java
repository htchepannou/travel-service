package io.tchepannou.k.travel.dao;

import io.tchepannou.k.travel.domain.ProductType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeDao extends CrudRepository<ProductType, Integer>{
    ProductType findByNameIgnoreCase(String name);
}
