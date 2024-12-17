package siit.service;

import siit.dao.ProductDao;
import siit.model.Product;

import java.util.List;

public class ProductService {

    private ProductDao productDao;

    public ProductService() {
        this.productDao = new ProductDao();
    }

    public List<Product> getProductsBy(String term) {
        return productDao.getProductsBy(term);
    }
}
