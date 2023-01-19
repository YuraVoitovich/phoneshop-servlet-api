package com.es.phoneshop.model.exception;

public class OutOfStockException extends RuntimeException {

    private final int requestedStock;
    private final int availableStock;

    public int getRequestedStock() {
        return requestedStock;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public OutOfStockException(int requestedStock, int availableStock) {
        this.requestedStock = requestedStock;
        this.availableStock = availableStock;
    }
}
