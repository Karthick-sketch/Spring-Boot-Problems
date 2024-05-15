package com.example.assetmanagementsystem.repository;

import com.example.assetmanagementsystem.entity.AssetHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetHistoryRepository extends JpaRepository<AssetHistory, Integer> {
}
