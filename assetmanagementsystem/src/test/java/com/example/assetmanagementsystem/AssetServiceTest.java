package com.example.assetmanagementsystem;

import com.example.assetmanagementsystem.entity.Asset;
import com.example.assetmanagementsystem.entity.AssetHistory;
import com.example.assetmanagementsystem.entity.User;
import com.example.assetmanagementsystem.exception.BadRequestException;
import com.example.assetmanagementsystem.exception.EntityNotFoundException;
import com.example.assetmanagementsystem.repository.AssetHistoryRepository;
import com.example.assetmanagementsystem.repository.AssetRepository;
import com.example.assetmanagementsystem.service.AssetService;
import com.example.assetmanagementsystem.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AssetServiceTest {
    @Mock
    private AssetRepository assetRepository;
    @Mock
    private AssetHistoryRepository assetHistoryRepository;
    @Mock
    private UserService userService;

    @InjectMocks
    private AssetService assetService;

    @Test
    public void testGetAssetById() {
        int assetId = 1;
        Asset mockAsset = MockObjects.getMockAsset();
        Mockito.when(assetRepository.findById(assetId)).thenReturn(Optional.of(mockAsset));

        Asset validAsset = assetService.getAssetById(assetId);
        Executable invalidId = () -> assetService.getAssetById(2);

        Assertions.assertEquals(mockAsset, validAsset);
        Assertions.assertThrows(EntityNotFoundException.class, invalidId);
    }

    @Test
    public void testUpdateAsset() {
        int assetId = 1;
        Asset mockAsset = MockObjects.getMockAsset();
        Asset updatedMockAsset = MockObjects.getUpdatedMockAsset();
        Mockito.when(assetRepository.findById(assetId)).thenReturn(Optional.of(mockAsset));
        Mockito.when(assetRepository.save(updatedMockAsset)).thenReturn(updatedMockAsset);

        Executable invalidId = () -> assetService.getAssetById(2);
        Asset validAsset = assetService.updateAsset(assetId, updatedMockAsset);

        Assertions.assertThrows(EntityNotFoundException.class, invalidId);
        Assertions.assertEquals(updatedMockAsset, validAsset);
        Mockito.verify(assetRepository, Mockito.times(1)).save(Mockito.any(Asset.class));
    }

    @Test
    public void testDeleteAsset() {
        int assetId = 1;
        Asset mockAsset = MockObjects.getMockAsset();
        Mockito.when(assetRepository.findById(assetId)).thenReturn(Optional.of(mockAsset));
        assetService.deleteAsset(assetId);
        Mockito.verify(assetRepository, Mockito.times(1)).delete(mockAsset);
    }

    @Test
    public void testPurchaseAsset() {
        int assetId = 1, userId = 1, quantity = 5;
        Asset mockAsset = MockObjects.getMockAsset();
        User mockUser = MockObjects.getMockUser();
        Asset updatedMockAsset = MockObjects.getMockAsset();
        updatedMockAsset.setQuantity(updatedMockAsset.getQuantity() - quantity);
        AssetHistory mockAssetHistory = MockObjects.getMockAssetHistory("purchase");

        Mockito.when(assetRepository.findById(assetId)).thenReturn(Optional.of(mockAsset));
        Mockito.when(userService.getUserById(userId)).thenReturn(mockUser);
        Mockito.when(assetRepository.save(mockAsset)).thenReturn(updatedMockAsset);
        Mockito.when(assetHistoryRepository.save(Mockito.any(AssetHistory.class))).thenReturn(mockAssetHistory);

        Executable invalidQuantity = () -> assetService.purchaseAsset(assetId, userId, 15);
        AssetHistory validAssetHistory = assetService.purchaseAsset(assetId, userId, quantity);

        Assertions.assertThrows(BadRequestException.class, invalidQuantity);
        Assertions.assertEquals(mockAssetHistory, validAssetHistory);
        Mockito.verify(assetHistoryRepository, Mockito.times(1)).save(Mockito.any(AssetHistory.class));
    }

    @Test
    public void testReturnAsset() {
        int assetId = 1, userId = 1, quantity = 5;
        Asset mockAsset = MockObjects.getMockAsset();
        User mockUser = MockObjects.getMockUser();
        Asset updatedMockAsset = MockObjects.getMockAsset();
        updatedMockAsset.setQuantity(updatedMockAsset.getQuantity() + quantity);
        AssetHistory mockAssetHistory = MockObjects.getMockAssetHistory("return");

        Mockito.when(assetRepository.findById(assetId)).thenReturn(Optional.of(mockAsset));
        Mockito.when(userService.getUserById(userId)).thenReturn(mockUser);
        Mockito.when(assetRepository.save(mockAsset)).thenReturn(updatedMockAsset);
        Mockito.when(assetHistoryRepository.save(Mockito.any(AssetHistory.class))).thenReturn(mockAssetHistory);

        AssetHistory validAssetHistory = assetService.purchaseAsset(assetId, userId, quantity);
        Assertions.assertEquals(mockAssetHistory, validAssetHistory);
        Mockito.verify(assetHistoryRepository, Mockito.times(1)).save(Mockito.any(AssetHistory.class));
    }
}
