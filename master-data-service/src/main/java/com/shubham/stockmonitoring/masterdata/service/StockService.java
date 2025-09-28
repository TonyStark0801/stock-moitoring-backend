package com.shubham.stockmonitoring.masterdata.service;

import com.shubham.stockmonitoring.masterdata.dto.StockRequest;
import com.shubham.stockmonitoring.masterdata.dto.StockResponse;
import com.shubham.stockmonitoring.masterdata.entity.Stock;
import com.shubham.stockmonitoring.masterdata.repository.StockRepository;
import com.shubham.stockmonitoring.commons.exception.CustomException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {
    
    private final StockRepository stockRepository;
    
    public Page<StockResponse> getAllStocks(Pageable pageable) {
        Page<Stock> stocks = stockRepository.findAll(pageable);
        return stocks.map(this::mapToResponse);
    }
    
    public StockResponse getStockById(Long id) {
        Stock stock = stockRepository.findById(id)
            .orElseThrow(() -> new CustomException("STOCK_NOT_FOUND", "Stock not found with id: " + id, HttpStatus.BAD_REQUEST));
        return mapToResponse(stock);
    }
    
    public StockResponse getStockBySymbol(String symbol) {
        Stock stock = stockRepository.findBySymbol(symbol)
            .orElseThrow(() -> new CustomException("STOCK_NOT_FOUND", "Stock not found with symbol: " + symbol, HttpStatus.BAD_REQUEST));
        return mapToResponse(stock);
    }
    
    public List<StockResponse> searchStocks(String query) {
        List<Stock> stocks = stockRepository.findByNameOrSymbolContaining(query);
        return stocks.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }
    
    public List<StockResponse> getStocksBySector(String sector) {
        List<Stock> stocks = stockRepository.findBySector(sector);
        return stocks.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }
    
    public StockResponse createStock(StockRequest request) {
        if (stockRepository.findBySymbol(request.getSymbol()).isPresent()) {
            throw new CustomException("STOCK_EXISTS", "Stock already exists with symbol: " + request.getSymbol(), HttpStatus.BAD_REQUEST);
        }
        Stock stock = new Stock();
        mapRequestToEntity(request, stock);
        Stock savedStock = stockRepository.save(stock);
        return mapToResponse(savedStock);
    }
    
    public StockResponse updateStock(Long id, StockRequest request) {
        Stock stock = stockRepository.findById(id)
            .orElseThrow(() -> new CustomException("STOCK_NOT_FOUND", "Stock not found with id: " + id, HttpStatus.BAD_REQUEST));
        mapRequestToEntity(request, stock);
        Stock updatedStock = stockRepository.save(stock);
        return mapToResponse(updatedStock);
    }
    
    public void deleteStock(Long id) {
        Stock stock = stockRepository.findById(id)
            .orElseThrow(() -> new CustomException("STOCK_NOT_FOUND", "Stock not found with id: " + id, HttpStatus.BAD_REQUEST));
        stockRepository.delete(stock);
    }
    
    private void mapRequestToEntity(StockRequest request, Stock stock) {
        stock.setSymbol(request.getSymbol());
        stock.setName(request.getName());
        stock.setDescription(request.getDescription());
        stock.setSector(request.getSector());
        stock.setExchange(request.getExchange());
        stock.setCurrentPrice(request.getCurrentPrice());
        stock.setMarketCap(request.getMarketCap());
        stock.setVolume(request.getVolume());
    }
    
    private StockResponse mapToResponse(Stock stock) {
        return new StockResponse(
            stock.getId(),
            stock.getSymbol(),
            stock.getName(),
            stock.getDescription(),
            stock.getSector(),
            stock.getExchange(),
            stock.getCurrentPrice(),
            stock.getMarketCap(),
            stock.getVolume(),
            stock.isActive(),
            stock.getCreatedAt(),
            stock.getUpdatedAt()
        );
    }
}
