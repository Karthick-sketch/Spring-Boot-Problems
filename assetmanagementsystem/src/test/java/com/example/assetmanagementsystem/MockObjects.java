package com.example.assetmanagementsystem;

import com.example.assetmanagementsystem.entity.Asset;
import com.example.assetmanagementsystem.entity.AssetHistory;
import com.example.assetmanagementsystem.entity.User;

import java.time.LocalDate;

public class MockObjects {
    static User getMockUser() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("Connor");
        return user;
    }

    static User getUpdatedMockUser() {
        User user = getMockUser();
        user.setUsername("Haytham");
        return user;
    }

    static Asset getMockAsset() {
        Asset asset = new Asset();
        asset.setAssetId(1);
        asset.setAssetName("MacBook Air");
        asset.setAssetType("Laptop");
        asset.setQuantity(10);
        return asset;
    }

    static Asset getUpdatedMockAsset() {
        Asset asset = getMockAsset();
        asset.setAssetName("MacBook Air M1");
        asset.setQuantity(5);
        return asset;
    }

    static AssetHistory getMockAssetHistory(int quantity, String type) {
        AssetHistory assetHistory = new AssetHistory();
        assetHistory.setAssetId(1);
        assetHistory.setUserId(1);
        assetHistory.setQuantity(quantity);
        assetHistory.setType(type);
        assetHistory.setCreatedOn(LocalDate.now());
        return assetHistory;
    }
}
