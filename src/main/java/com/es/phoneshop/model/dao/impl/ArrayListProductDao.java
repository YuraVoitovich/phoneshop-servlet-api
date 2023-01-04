package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.exception.NoSuchProductException;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private final List<Product> products;
    private Long currentId = 1L;
    private final Lock writeLock;
    private final Lock readLock;

    public ArrayListProductDao() {
        this.products = new ArrayList<>();
        ReadWriteLock lock = new ReentrantReadWriteLock();
        this.writeLock = lock.writeLock();
        this.readLock = lock.readLock();
    }

    @Override
    public Product getProduct(Long id) {
        if (id == null) throw new IllegalArgumentException("Id can not be null");
        readLock.lock();
        Product product = products.stream()
                .filter(currentProduct -> id.equals(currentProduct.getId()))
                .findAny()
                .orElseThrow(() -> new NoSuchProductException(String.format("Product with id=%s not found", id)));
        readLock.unlock();
        return product;
    }

    @Override
    public List<Product> findProducts() {
        readLock.lock();
        List<Product> foundProducts =  products.stream()
                .filter(currentProduct -> currentProduct.getPrice() != null)
                .filter(currentProduct -> currentProduct.getStock() > 0)
                .collect(Collectors.toList());
        readLock.unlock();
        return foundProducts;
    }

    private void addProduct(Product product) {
        product.setId(currentId++);
        products.add(product);
    }
    @Override
    public void save(Product productToSave) {
        if (productToSave == null) throw new IllegalArgumentException("Product to save can not be null");
        writeLock.lock();
        if (productToSave.getId() != null) {
            Optional<Product> foundProductOptional = products.stream()
                    .filter(currentProduct -> currentProduct.getId().equals(productToSave.getId()))
                    .findAny();
            if (foundProductOptional.isPresent()) {
                products.set(products.indexOf(foundProductOptional.get()), productToSave);
            } else {
                addProduct(productToSave);
            }
        } else {
            addProduct(productToSave);
        }
        writeLock.unlock();
    }

    @Override
    public void delete(Long id) {
        if (id == null) throw new IllegalArgumentException("Id can not be null");
        writeLock.lock();
        if (!this.products.removeIf(currentProduct -> id.equals(currentProduct.getId())))
            throw new NoSuchProductException(String.format("Product with id=%s not found for deleting", id));
        writeLock.unlock();
    }


}
