package com.shubham.stockmonitoring.masterdata.controller;

import com.shubham.stockmonitoring.masterdata.dto.StockRequest;
import com.shubham.stockmonitoring.masterdata.dto.StockResponse;
import com.shubham.stockmonitoring.masterdata.service.StockService;
import com.shubham.stockmonitoring.commons.dto.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {
    
    private final StockService stockService;
    
    @GetMapping
    public ResponseEntity<BaseResponse<Page<StockResponse>>> getAllStocks(Pageable pageable) {
        Page<StockResponse> stocks = stockService.getAllStocks(pageable);
        return ResponseEntity.ok(BaseResponse.success(stocks));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<StockResponse>> getStockById(@PathVariable Long id) {
        StockResponse stock = stockService.getStockById(id);
        return ResponseEntity.ok(BaseResponse.success(stock));
    }
    
    @GetMapping("/symbol/{symbol}")
    public ResponseEntity<BaseResponse<StockResponse>> getStockBySymbol(@PathVariable String symbol) {
        StockResponse stock = stockService.getStockBySymbol(symbol);
        return ResponseEntity.ok(BaseResponse.success(stock));
    }
    
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<StockResponse>>> searchStocks(@RequestParam String query) {
        List<StockResponse> stocks = stockService.searchStocks(query);
        return ResponseEntity.ok(BaseResponse.success(stocks));
    }
    
    @GetMapping("/sector/{sector}")
    public ResponseEntity<BaseResponse<List<StockResponse>>> getStocksBySector(@PathVariable String sector) {
        List<StockResponse> stocks = stockService.getStocksBySector(sector);
        return ResponseEntity.ok(BaseResponse.success(stocks));
    }
    
    @PostMapping
    public ResponseEntity<BaseResponse<StockResponse>> createStock(@Valid @RequestBody StockRequest request) {
        StockResponse stock = stockService.createStock(request);
        return ResponseEntity.ok(BaseResponse.success("Stock created successfully", stock));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<StockResponse>> updateStock(
            @PathVariable Long id,
            @Valid @RequestBody StockRequest request) {
        StockResponse stock = stockService.updateStock(id, request);
        return ResponseEntity.ok(BaseResponse.success("Stock updated successfully", stock));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.ok(BaseResponse.success("Stock deleted successfully", "OK"));
    }
    
    @GetMapping("/health")
    public ResponseEntity<BaseResponse<String>> health() {
        return ResponseEntity.ok(BaseResponse.success("Master data service is healthy", "OK"));
    }
}
