package com.example.ProductMicroService.Service;

import com.example.ProductMicroService.Model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getProductById(int id);
    List<Product> getAllProducts();
    Product AddNewProduct(Product product);
    Product UpdateProduct(int id, Product product);
    Product deleteProductById(int id);


}
