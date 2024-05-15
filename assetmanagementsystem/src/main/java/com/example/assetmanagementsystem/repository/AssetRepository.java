package com.example.assetmanagementsystem.repository;

import com.example.assetmanagementsystem.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Integer> {
}
