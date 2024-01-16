package com.example.ProductMicroService.Controller;

import com.example.ProductMicroService.Model.Product;
import com.example.ProductMicroService.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable int id) {
        Optional<Product> product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        ArrayList<Product> products = (ArrayList<Product>) productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/products/add")
    public ResponseEntity<Product> AddProduct(@RequestBody Product product) {
        Product p = productService.AddNewProduct(product);
        return ResponseEntity.ok(p);
    }

    @PutMapping("/products/update/{id}")
    public ResponseEntity<Product> UpdateProduct(@PathVariable int id, @RequestBody Product product) {
        Product p=productService.UpdateProduct(id, product);
        return ResponseEntity.ok(p);
    }

    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<Product> DeleteProduct(@PathVariable int id)
    {
        return ResponseEntity.ok(productService.deleteProductById(id));
    }
}
