package com.es.phoneshop;

import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.dao.enums.SortField;
import com.es.phoneshop.model.dao.enums.SortOrder;
import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.entity.Product;
import com.es.phoneshop.model.exception.NoSuchProductException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;
    private final Currency usd = Currency.getInstance("USD");

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsHasResults() {
        productDao.save(new Product( "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product( "sgs2", "Samsung Galaxy S III", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));

        assertTrue(productDao.findProducts(null, null, null).size() > 0);
    }
    @Test
    public void testFindProductsQuery() {
        productDao.save(new Product( "sgs", "Samsung Galaxy S III", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product( "sgs2", "Samsung Galaxy S", new BigDecimal(200), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));

        List<Product> productList = productDao.findProducts("Samsung Galaxy S", null, null);
        assertEquals("Samsung Galaxy S",productList.get(0).getDescription());
    }

    @Test
    public void testFindProductsDescriptionAscSort() {
        productDao.save(new Product( "sgs", "Samsung Galaxy S III", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product( "sgs2", "Samsung Galaxy S", new BigDecimal(200), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));

        List<Product> productList = productDao.findProducts(null, SortField.DESCRIPTION, SortOrder.ASC);
        assertEquals("Samsung Galaxy S",productList.get(0).getDescription());
    }

    @Test
    public void testFindProductsDescriptionDescSort() {
        productDao.save(new Product( "sgs", "Samsung Galaxy S III", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product( "sgs2", "Samsung Galaxy S", new BigDecimal(200), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));

        List<Product> productList = productDao.findProducts(null, SortField.DESCRIPTION, SortOrder.DESC);
        assertEquals("Samsung Galaxy S III",productList.get(0).getDescription());
    }

    @Test
    public void testFindProductsPriceAscSort() {
        productDao.save(new Product( "sgs", "Samsung Galaxy S III", new BigDecimal(200), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product( "sgs2", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));

        List<Product> productList = productDao.findProducts(null, SortField.PRICE, SortOrder.ASC);
        assertEquals("Samsung Galaxy S",productList.get(0).getDescription());
    }

    @Test
    public void testFindProductsNullSortOrderSort() {
        productDao.save(new Product( "sgs", "Samsung Galaxy S III", new BigDecimal(200), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product( "sgs2", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));

        List<Product> productList = productDao.findProducts(null, SortField.PRICE, null);
        assertEquals("Samsung Galaxy S",productList.get(0).getDescription());
    }

    @Test
    public void testFindProductsPriceDescSort() {
        productDao.save(new Product( "sgs", "Samsung Galaxy S III", new BigDecimal(200), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product( "sgs2", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));

        List<Product> productList = productDao.findProducts(null, SortField.PRICE, SortOrder.DESC);
        assertEquals("Samsung Galaxy S III",productList.get(0).getDescription());
    }
    @Test
    public void testFindProductsZeroStockResult() {
        productDao.save(new Product( "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product( "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));

        assertEquals(1, productDao.findProducts(null, null, null).size());
    }

    @Test
    public void testFindProductsNullPriceResult() {
        productDao.save(new Product( "sgs", "Samsung Galaxy S", null, usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product( "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));

        assertEquals(1, productDao.findProducts(null, null, null).size());
    }
    @Test
    public void testGetProduct() {
        Product product = new Product( "sgs", "Samsung Galaxy S", null, usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        productDao.save(new Product( "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));

        assertTrue(product.getId() > 0L);
        assertEquals(product.getCode(),productDao.getProduct(product.getId()).getCode());
    }

    @Test
    public void testSaveProduct() {
        Product product = new Product( "sgs", "Samsung Galaxy S", new BigDecimal(200), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        Product foundProduct = productDao.getProduct(product.getId());

        assertEquals(product, foundProduct);
    }

    @Test
    public void testSaveNotNullIdProduct() {
        Long expectedId = 9999L;
        Product product = new Product( 9999L,"sgs", "Samsung Galaxy S", new BigDecimal(200), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        Product foundProduct = productDao.getProduct(product.getId());

        assertNotEquals(expectedId, foundProduct.getId());
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product( "sgs", "Samsung Galaxy S", new BigDecimal(200), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        Product newProduct = new Product(product.getId(),"sgs2", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(newProduct);
        Product foundProduct = productDao.getProduct(product.getId());

        assertEquals(newProduct.getCode(), foundProduct.getCode());
    }

    @Test(expected = NoSuchProductException.class)
    public void testDeleteProductNoSuchProductException() {
        productDao.delete(0L);
    }

    @Test(expected = NoSuchProductException.class)
    public void testGetProductNoSuchProductException() {
        productDao.getProduct(0L);
    }
    @Test
    public void testDeleteProduct() {
        Product product = new Product( "sgs", "Samsung Galaxy S", new BigDecimal(200), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        productDao.delete(product.getId());

        assertEquals(0, productDao.findProducts(null, null, null).size());
    }






}
