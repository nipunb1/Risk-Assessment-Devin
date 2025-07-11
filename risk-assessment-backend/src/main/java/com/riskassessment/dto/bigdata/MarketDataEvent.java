package com.riskassessment.dto.bigdata;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MarketDataEvent {
    
    private String symbol;
    private BigDecimal price;
    private BigDecimal previousPrice;
    private BigDecimal volatility;
    private BigDecimal volume;
    private String marketType;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    
    private String source;
    private String eventType;

    public MarketDataEvent() {}

    public MarketDataEvent(String symbol, BigDecimal price, BigDecimal previousPrice, 
                          BigDecimal volatility, BigDecimal volume, String marketType, 
                          LocalDateTime timestamp, String source, String eventType) {
        this.symbol = symbol;
        this.price = price;
        this.previousPrice = previousPrice;
        this.volatility = volatility;
        this.volume = volume;
        this.marketType = marketType;
        this.timestamp = timestamp;
        this.source = source;
        this.eventType = eventType;
    }

    public BigDecimal getPriceChangePercentage() {
        if (previousPrice != null && previousPrice.compareTo(BigDecimal.ZERO) > 0) {
            return price.subtract(previousPrice)
                      .divide(previousPrice, 4, BigDecimal.ROUND_HALF_UP)
                      .multiply(BigDecimal.valueOf(100));
        }
        return BigDecimal.ZERO;
    }

    public boolean isHighVolatility(BigDecimal threshold) {
        return volatility != null && volatility.compareTo(threshold) > 0;
    }

    public boolean isSignificantPriceChange(BigDecimal threshold) {
        BigDecimal changePercentage = getPriceChangePercentage().abs();
        return changePercentage.compareTo(threshold) > 0;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPreviousPrice() {
        return previousPrice;
    }

    public void setPreviousPrice(BigDecimal previousPrice) {
        this.previousPrice = previousPrice;
    }

    public BigDecimal getVolatility() {
        return volatility;
    }

    public void setVolatility(BigDecimal volatility) {
        this.volatility = volatility;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public String getMarketType() {
        return marketType;
    }

    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
