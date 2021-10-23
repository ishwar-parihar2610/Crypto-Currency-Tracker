package com.example.cryptocurrencytracker.model;

public class CurrencyModel {
    private String currencyName;
    private String symbol;
    private double price;

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public CurrencyModel(String currencyName, String symbol, double price) {
        this.currencyName = currencyName;
        this.symbol = symbol;
        this.price = price;
    }
}
