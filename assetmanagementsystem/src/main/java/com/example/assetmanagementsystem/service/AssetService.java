package com.example.assetmanagementsystem.service;

import com.example.assetmanagementsystem.entity.Asset;
import com.example.assetmanagementsystem.entity.AssetHistory;
import com.example.assetmanagementsystem.entity.User;
import com.example.assetmanagementsystem.exception.BadRequestException;
import com.example.assetmanagementsystem.exception.EntityNotFoundException;
import com.example.assetmanagementsystem.repository.AssetHistoryRepository;
import com.example.assetmanagementsystem.repository.AssetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AssetService {
    private AssetRepository assetRepository;
    private AssetHistoryRepository assetHistoryRepository;
    private UserService userService;

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Asset getAssetById(int assetId) {
        Optional<Asset> asset = assetRepository.findById(assetId);
        if (asset.isEmpty()) {
            throw new EntityNotFoundException("There is no asset with the ID of " + assetId);
        }
        return asset.get();
    }

    public Asset createAsset(Asset asset) {
        return assetRepository.save(asset);
    }

    public Asset updateAsset(int assetId, Asset updatedAsset) {
        Asset asset = getAssetById(assetId);
        updatedAsset.setAssetId(asset.getAssetId());
        return assetRepository.save(updatedAsset);
    }

    public void deleteAsset(int assetId) {
        assetRepository.delete(getAssetById(assetId));
    }

    public AssetHistory purchaseAsset(int assetId, int userId, int quantity) {
        Asset asset = getAssetById(assetId);
        User user = userService.getUserById(userId);
        if (quantity > asset.getQuantity()) {
            throw new BadRequestException("The required number of quantity is not available to allocate");
        }
        // reduce quantity
        asset.setQuantity(asset.getQuantity() - quantity);
        updateAsset(assetId, asset);
        // create asset history
        return assetHistoryRepository.save(getNewAssetHistoryObject(asset.getAssetId(), user.getUserId(), quantity, "purchase"));
    }

    public AssetHistory returnAsset(int assetId, int userId, int quantity) {
        Asset asset = getAssetById(assetId);
        User user = userService.getUserById(userId);
        // increase quantity
        asset.setQuantity(asset.getQuantity() + quantity);
        updateAsset(assetId, asset);
        // create asset history
        return assetHistoryRepository.save(getNewAssetHistoryObject(asset.getAssetId(), user.getUserId(), quantity, "return"));
    }

    private AssetHistory getNewAssetHistoryObject(int assetId, int userId, int quantity, String type) {
        AssetHistory assetHistory = new AssetHistory();
        assetHistory.setAssetId(assetId);
        assetHistory.setUserId(userId);
        assetHistory.setQuantity(quantity);
        assetHistory.setType(type);
        assetHistory.setCreatedOn(LocalDate.now());
        return assetHistory;
    }
}
