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

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final List<Product> products;
    private Long currentId = 1L;

    public ArrayListProductDao() {
        this.products = new ArrayList<>();
    }

    @Override
    public Product getProduct(@NonNull Long id) {
        Lock readLock = lock.readLock();
        readLock.lock();
        Product product = products.stream()
                .filter(o -> id.equals(o.getId()))
                .findAny().
                orElseThrow(() -> new NoSuchProductException(String.format("Product with id=%s not found", id)));
        readLock.unlock();
        return product;
    }

    @Override
    public List<Product> findProducts() {
        Lock readLock = lock.readLock();
        readLock.lock();
        List<Product> foundProducts =  products.stream()
                .filter(o -> o.getPrice() != null)
                .filter(o -> o.getStock() > 0)
                .collect(Collectors.toList());
        readLock.unlock();
        return foundProducts;
    }

    @Override
    public void save(@NonNull Product product) {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        if (product.getId() != null) {
            Optional<Product> productOptional = products.stream()
                    .filter(o -> o.getId().equals(product.getId()))
                    .findAny();
            if (productOptional.isPresent()) {
                products.set(products.indexOf(productOptional.get()), product);
            } else {
                product.setId(currentId++);
                products.add(product);
            }
        } else {
            product.setId(currentId++);
            this.products.add(product);
        }
        writeLock.unlock();
    }

    @Override
    public void delete(@NonNull Long id) {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        if (!this.products.removeIf(o -> id.equals(o.getId())))
            throw new NoSuchProductException(String.format("Product with id=%s not found for deleting", id));
        writeLock.unlock();
    }


}
