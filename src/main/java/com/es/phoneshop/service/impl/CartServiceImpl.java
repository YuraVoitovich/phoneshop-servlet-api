package com.es.phoneshop.service.impl;

import com.es.phoneshop.model.dao.DAOProvider;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.entity.Cart;
import com.es.phoneshop.model.entity.CartItem;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.OutOfStockException;
import com.es.phoneshop.service.CartService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CartServiceImpl implements CartService {

    public final static String CART_ATTRIBUTE = CartServiceImpl.class.getSimpleName() + "-cart";

    private static volatile CartServiceImpl instance;

    public static CartServiceImpl getInstance(ProductDao productDao) {
        CartServiceImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (CartServiceImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CartServiceImpl(productDao);
                }
            }
        }
        return localInstance;
    }

    public static CartServiceImpl getInstance() {
        return getInstance(DAOProvider.getInstance().getProductDao());
    }
    private final Map<HttpSession, Cart> carts;
    private final ProductDao productDao;

    private final Lock readLock;

    private final Lock writeLock;

    private CartServiceImpl(ProductDao productDao) {
        this.carts = new HashMap<>();
        this.productDao = productDao;
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }


    private void checkIfCanBeAdded(int quantity, int stock) {
        if (stock < quantity) {
            throw new OutOfStockException(quantity, stock);
        }
    }

    @Override
    public Cart getCartBySession(HttpSession session) {
        readLock.lock();
        Cart cart = null;
        try {
            if (carts.containsKey(session)) {
                cart = carts.get(session);
                return cart;
            }

        } finally {
            readLock.unlock();
        }
        cart = new Cart();
        writeLock.lock();
        try {
            carts.put(session, cart);
        } finally {
            writeLock.unlock();
        }
        session.setAttribute(CART_ATTRIBUTE, cart);
        return cart;
    }

    private void increaseQuantity(CartItem cartItem, int increaseValue) {
        cartItem.setQuantity(cartItem.getQuantity() + increaseValue);
    }
    @Override
    public void add(HttpSession session, Long productId, int quantity) {
        Cart cart = getCartBySession(session);
        synchronized (session) {
            Product product = productDao.getProduct(productId);

            Optional<CartItem> cartItemOptional = cart.getItems().stream()
                    .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                    .findAny();

            if (cartItemOptional.isPresent()) {
                int realQuantity = quantity + cartItemOptional.get().getQuantity();
                checkIfCanBeAdded(realQuantity, product.getStock());
                increaseQuantity(cartItemOptional.get(), quantity);
            } else {
                checkIfCanBeAdded(quantity, product.getStock());
                cart.add(new CartItem(product, quantity));
            }
        }

    }
}
