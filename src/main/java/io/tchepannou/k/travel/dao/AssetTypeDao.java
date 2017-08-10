package io.tchepannou.k.travel.dao;

import io.tchepannou.k.travel.domain.AssetType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetTypeDao extends CrudRepository<AssetType, Integer>{
    AssetType findByNameIgnoreCase(String name);
}
