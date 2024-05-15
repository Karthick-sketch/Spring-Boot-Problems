package com.example.assetmanagementsystem.controller;

import com.example.assetmanagementsystem.entity.Asset;
import com.example.assetmanagementsystem.entity.AssetHistory;
import com.example.assetmanagementsystem.exception.ErrorResponse;
import com.example.assetmanagementsystem.service.AssetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Tag(name = "Asset Controller", description = "Manage, purchase and return assets.")
@RestController
@RequestMapping("/assetmanagement/asset")
public class AssetController {
    private AssetService assetService;

    @ApiResponse(responseCode = "200", description = "Successful retrieval of assets", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Asset.class))))
    @Operation(summary = "Retrieves asset record", description = "Provides a list of all assets.")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Asset>> getAllAssets() {
        return new ResponseEntity<>(assetService.getAllAssets(), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Asset doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of asset", content = @Content(schema = @Schema(implementation = Asset.class))),
    })
    @Operation(summary = "Get asset by ID", description = "Returns a asset record based on an ID.")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Asset> getAssetById(@PathVariable int id) {
        return new ResponseEntity<>(assetService.getAssetById(id), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "201", description = "Successful creation of asset", content = @Content(schema = @Schema(implementation = Asset.class)))
    @Operation(summary = "Create asset record", description = "Creates a asset record from the provided payload.")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Asset> createAsset(@RequestBody Asset asset) {
        return new ResponseEntity<>(assetService.createAsset(asset), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Asset doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful update of an asset", content = @Content(schema = @Schema(implementation = Asset.class)))
    })
    @Operation(summary = "Update a asset by ID", description = "Updates an existing asset record from the provided payload and ID.")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Asset> updateAsset(@PathVariable int id, @RequestBody Asset asset) {
        return new ResponseEntity<>(assetService.updateAsset(id, asset), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Asset doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "Successful deletion of an asset")
    })
    @Operation(summary = "Delete a asset by ID", description = "Deletes a asset record based on an ID.")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteAsset(@PathVariable int id) {
        assetService.deleteAsset(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Asset/User doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful purchase of an asset", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful purchase of an asset", content = @Content(schema = @Schema(implementation = AssetHistory.class)))
    })
    @Operation(summary = "Purchase an asset", description = "Purchase an existing asset.")
    @PutMapping(value = "/purchase/{assetId}/{userId}/{quantity}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AssetHistory> purchaseAsset(@PathVariable int assetId, @PathVariable int userId, @PathVariable int quantity) {
        return new ResponseEntity<>(assetService.purchaseAsset(assetId, userId, quantity), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Asset/User doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful return of an asset", content = @Content(schema = @Schema(implementation = AssetHistory.class)))
    })
    @Operation(summary = "Return the asset", description = "Return the purchased asset.")
    @PutMapping(value = "/return/{assetId}/{userId}/{quantity}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AssetHistory> returnAsset(@PathVariable int assetId, @PathVariable int userId, @PathVariable int quantity) {
        return new ResponseEntity<>(assetService.returnAsset(assetId, userId, quantity), HttpStatus.OK);
    }
}
