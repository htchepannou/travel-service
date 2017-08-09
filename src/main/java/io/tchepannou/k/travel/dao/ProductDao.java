package io.tchepannou.k.travel.dao;

import io.tchepannou.k.travel.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends CrudRepository<Product, Integer>{
}
