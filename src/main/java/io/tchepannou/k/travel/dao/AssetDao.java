package io.tchepannou.k.travel.dao;

import io.tchepannou.k.travel.domain.Asset;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetDao extends CrudRepository<Asset, Integer>{
}
