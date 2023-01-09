package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.dao.enums.SortField;
import com.es.phoneshop.model.dao.enums.SortOrder;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.exception.NoSuchProductException;
import com.es.phoneshop.utils.Pair;

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

    private final EnumMap<SortField, Comparator<Object>> sortFieldComparators;

    public ArrayListProductDao() {
        this.products = new ArrayList<>();
        ReadWriteLock lock = new ReentrantReadWriteLock();
        this.writeLock = lock.writeLock();
        this.readLock = lock.readLock();
        sortFieldComparators = new EnumMap<>(SortField.class);
        sortFieldComparators.put(SortField.DESCRIPTION,
                Comparator.comparing(o -> ((Product) o).getDescription()));
        sortFieldComparators.put(SortField.PRICE,
                Comparator.comparing(o -> ((Product) o).getPrice()));

    }

    @Override
    public Product getProduct(Long id) {
        if (id == null) throw new IllegalArgumentException("Id can not be null");
        readLock.lock();
        Product product;
        try {
            product = products.stream()
                    .filter(currentProduct -> id.equals(currentProduct.getId()))
                    .findAny()
                    .orElseThrow(() -> new NoSuchProductException(String.format("Product with id=%s not found", id)));
        } finally {
            readLock.unlock();
        }
        return product;
    }

    private int countMatches(Matcher matcher) {
        int counter = 0;
        while (matcher.find()) {
            counter++;
        }
        return counter;
    }

    private Comparator<Object> createQueryComparator() {

        return Collections.reverseOrder(Comparator.comparingInt(o -> ((Pair<Product, Integer>)o).snd)
                        .reversed()
                .thenComparing(object -> ((Pair<Product, Integer>)object).fst.getDescription().length())
                .reversed());
    }

    private int countMatches(Pattern pattern, String description) {
        Matcher matcher = pattern.matcher(description);
        return countMatches(matcher);
    }
    private List<Product> processQuery(Stream<Product> filteredStream, String query, Comparator<Object> sortComparator) {
        List<Product> foundProducts;

        Pattern pattern = Pattern.compile(Arrays.stream(query.trim().split("\\s"))
                .map(o -> "(" + o + ")").collect(Collectors.joining("|")));
        Comparator<Object> resultComparator = sortComparator == null ? createQueryComparator()
                : createQueryComparator()
                .thenComparing((o1,o2) -> sortComparator
                        .compare(((Pair<Product, Integer>)o1).fst, ((Pair<Product, Integer>)o2).fst));
        foundProducts = filteredStream
                .map(o -> new Pair<Product, Integer>(o, countMatches(pattern, o.getDescription())))
                .filter(o -> o.snd > 0)
                .sorted(resultComparator)
                .map(o -> o.fst)
                .collect(Collectors.toList());
        return foundProducts;
    }

    private List<Product> processEmptyQuery(Stream<Product> filteredStream, Comparator<Object> sortComparator) {
        return sortComparator == null ? filteredStream.collect(Collectors.toList())
                : filteredStream.sorted(sortComparator).collect(Collectors.toList());
    }

    private Comparator<Object> createSortComparator(SortField sortField, SortOrder sortOrder) {
        Comparator<Object> sortComparator;
        if (sortField == null) {
            sortComparator = null;
        } else {
            if (sortOrder == null) {
                sortOrder = SortOrder.ASC;
            }
            sortComparator = sortFieldComparators.get(sortField);
            if (sortOrder == SortOrder.DESC) {
                sortComparator = sortComparator.reversed();
            }
        }
        return sortComparator;
    }
    @Override
    public List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
        List<Product> foundProducts;
        readLock.lock();
        try {
            Stream<Product> filteredStream = products.stream()
                    .filter(currentProduct -> currentProduct.getPrice() != null)
                    .filter(currentProduct -> currentProduct.getStock() > 0);
            Comparator<Object> sortComparator = createSortComparator(sortField, sortOrder);
            if (query == null || query.isEmpty()) {
                foundProducts = processEmptyQuery(filteredStream, sortComparator);
            } else {
                foundProducts = processQuery(filteredStream, query, sortComparator);
            }
        } finally {
            readLock.unlock();
        }
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
        try {
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
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void delete(Long id) {
        if (id == null) throw new IllegalArgumentException("Id can not be null");
        writeLock.lock();
        try {
            if (!this.products.removeIf(currentProduct -> id.equals(currentProduct.getId())))
                throw new NoSuchProductException(String.format("Product with id=%s not found for deleting", id));
        } finally {
            writeLock.unlock();
        }
    }


}
