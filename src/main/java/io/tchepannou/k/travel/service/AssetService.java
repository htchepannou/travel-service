package io.tchepannou.k.travel.service;

import io.tchepannou.k.travel.client.request.CreateAssetRequest;
import io.tchepannou.k.travel.client.request.UpdateAssetRequest;
import io.tchepannou.k.travel.client.response.AssetDto;
import io.tchepannou.k.travel.client.response.AssetTypeDto;
import io.tchepannou.k.travel.client.response.CreateAssetResponse;
import io.tchepannou.k.travel.client.response.GetAssetResponse;
import io.tchepannou.k.travel.client.response.UpdateAssetResponse;
import io.tchepannou.k.travel.dao.AssetDao;
import io.tchepannou.k.travel.dao.AssetTypeDao;
import io.tchepannou.k.travel.domain.Asset;
import io.tchepannou.k.travel.domain.AssetType;
import io.tchepannou.k.travel.exception.BusinessErrors;
import io.tchepannou.k.travel.exception.InvalidRequestException;
import io.tchepannou.k.travel.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class AssetService {
    @Autowired
    private AssetTypeDao assetTypeDao;

    @Autowired
    private AssetDao assetDao;

    @Transactional
    public CreateAssetResponse create(CreateAssetRequest request){
        final AssetType assetType = assetTypeDao.findByNameIgnoreCase(request.getAssetTypeName());
        if (assetType == null){
            throw new InvalidRequestException(BusinessErrors.INVALID_ASSET_TYPE);
        }

        final Asset asset = new Asset ();
        asset.setAssetTypeId(assetType.getId());
        asset.setCapacity(request.getCapacity());
        asset.setImmatriculationNumber(request.getImmatriculationNumber());
        asset.setModel(request.getModel());
        asset.setTravelProviderId(request.getTravelProviderId());
        asset.setYear(request.getYear());
        assetDao.save(asset);

        CreateAssetResponse response = new CreateAssetResponse();
        response.setId(asset.getId());
        return response;
    }

    @Transactional
    public UpdateAssetResponse update(Integer id, UpdateAssetRequest request){
        final Asset asset = assetDao.findOne(id);
        if (asset == null){
            throw new NotFoundException(BusinessErrors.ASSET_NOT_FOUND);
        }

        asset.setCapacity(request.getCapacity());
        asset.setImmatriculationNumber(request.getImmatriculationNumber());
        asset.setModel(request.getModel());
        asset.setYear(request.getYear());
        assetDao.save(asset);

        UpdateAssetResponse response = new UpdateAssetResponse();
        response.setId(id);
        return response;
    }

    public GetAssetResponse findById(Integer id){
        final Asset asset = assetDao.findOne(id);
        if (asset == null){
            throw new NotFoundException(BusinessErrors.ASSET_NOT_FOUND);
        }

        final AssetType type = assetTypeDao.findOne(asset.getAssetTypeId());
        final AssetTypeDto typeDto = toDto(type);

        final AssetDto dto = toDto(asset, typeDto);
        final GetAssetResponse response = new GetAssetResponse();
        response.setAsset(dto);
        return response;
    }


    private AssetTypeDto toDto(AssetType assetType){
        final AssetTypeDto dto = new AssetTypeDto();
        dto.setDescription(assetType.getDescription());
        dto.setId(assetType.getId());
        dto.setName(assetType.getName());
        return dto;
    }

    private AssetDto toDto(Asset asset, AssetTypeDto typeDto){
        AssetDto dto = new AssetDto();
        dto.setAssetType(typeDto);
        dto.setCapacity(asset.getCapacity());
        dto.setId(asset.getId());
        dto.setImmatriculationNumber(asset.getImmatriculationNumber());
        dto.setModel(asset.getModel());
        dto.setTravelProviderId(asset.getTravelProviderId());
        dto.setYear(asset.getYear());
        return dto;
    }
}
