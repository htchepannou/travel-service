package io.tchepannou.k.travel.client.response;

public class GetAssetResponse {
    private AssetDto asset;

    public AssetDto getAsset() {
        return asset;
    }

    public void setAsset(final AssetDto assetDTO) {
        this.asset = assetDTO;
    }
}
