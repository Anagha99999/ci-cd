package com.example.ProductMicroService.Service;

import com.example.ProductMicroService.Exceptions.ProductNotFoundException;
import com.example.ProductMicroService.Model.Product;
import com.example.ProductMicroService.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> getProductById(int id) {
        Optional<Product> product=productRepository.findById(id);
        if(!product.isPresent())
        {
            throw new ProductNotFoundException("Product is not present");
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products= productRepository.findAll();
        return products;
    }

    @Override
    public Product AddNewProduct(Product product) {
        Product prod=new Product();
        prod.setProductName(product.getProductName());
        prod.setCategory(product.getCategory());
        prod.setPrice(product.getPrice());
        productRepository.save(prod);
        return prod;
    }

    @Override
    public Product UpdateProduct(int id, Product product) {
        Optional<Product> product1 = productRepository.findById(id);
        if (!product1.isPresent())
        {
            throw new ProductNotFoundException("Enter valid ID");
        }
        if(product1.isPresent())
        {
            Product p=product1.get();
            p.setProductName(product.getProductName());
            p.setCategory(product.getCategory());
            p.setPrice(product.getPrice());
            productRepository.save(p);
        }
        return product;
    }

    @Override
    public Product deleteProductById(int id) {
        Optional<Product> product1=productRepository.findById(id);
        if(!product1.isPresent())
        {
           throw new ProductNotFoundException("product is not found");
        }
        Product p=product1.get();
        productRepository.deleteById(id);
        return p;
    }

}
