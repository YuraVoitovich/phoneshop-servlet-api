package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.exception.NoSuchProductException;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private int countMatches(Matcher matcher) {
        int counter = 0;
        while (matcher.find()) {
            counter++;
        }
        return counter;
    }
    @Override
    public List<Product> findProducts(String query) {
        readLock.lock();
        List<Product> foundProducts;
        Stream<Product> stream = products.stream()
                .filter(currentProduct -> currentProduct.getPrice() != null)
                .filter(currentProduct -> currentProduct.getStock() > 0);
        if (query == null || query.isEmpty()) {
            foundProducts = stream.collect(Collectors.toList());
        } else {
            Pattern pattern = Pattern.compile(Arrays.stream(query.trim().split("\\s"))
                    .map(o -> "(" + o + ")").collect(Collectors.joining("|")));
            Comparator<Object> queryComparator = Comparator.comparingInt(object -> {
                Product product = (Product) object;
                Matcher matcher = pattern.matcher(product.getDescription());
                return countMatches(matcher);
            }).thenComparing(Collections
                    .reverseOrder(Comparator.comparingInt(o -> { Product p = (Product)o; return p.getDescription().length(); })));

            foundProducts = stream.filter(currentProduct -> {
                        Matcher matcher = pattern.matcher(currentProduct.getDescription());
                        return countMatches(matcher) > 0;
                    })
                    .sorted(queryComparator)
                    .collect(Collectors.toList());
            Collections.reverse(foundProducts);
        }

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
