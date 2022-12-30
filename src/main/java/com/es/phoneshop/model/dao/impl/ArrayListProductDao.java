package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.exception.NoSuchProductException;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final List<Product> products;
    private Long currentId = 0L;

    public ArrayListProductDao() {
        this.products = new ArrayList<>();
        this.saveSampleProducts();
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

    private void saveSampleProducts(){
        Currency usd = Currency.getInstance("USD");
        this.save(new Product( "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        this.save(new Product( "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        this.save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        this.save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        this.save(new Product( "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        this.save(new Product( "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        this.save(new Product( "sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        this.save(new Product( "xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        this.save(new Product( "nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        this.save(new Product( "palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        this.save(new Product( "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        this.save(new Product( "simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        this.save(new Product( "simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));

    }
}
