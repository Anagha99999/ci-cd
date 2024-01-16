package com.example.ProductMicroService.Service;

import com.example.ProductMicroService.Exceptions.ProductNotFoundException;
import com.example.ProductMicroService.Model.Product;
import com.example.ProductMicroService.Repository.ProductRepository;
import com.example.ProductMicroService.Service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void SetUp()
    {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllProductsTest()
    {
        List<Product> mockProducts=Arrays.asList(
                new Product(1,"laptop","hardware",80000.00),
                new Product(2,"mouse","electronics",4000.00));
        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> products=productService.getAllProducts();
        assertEquals(mockProducts.size(),products.size());
    }

    @Test
    void getProductById()
    {
        when(productRepository.findById(1)).thenReturn(
                Optional.of(new Product(1, "laptop", "hardware", 80000.00)));

        Optional<Product> product=productRepository.findById(1);
        assertEquals("laptop",product.get().getProductName());
    }

    @Test
    void getProductById_IdNotFound()
    {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,()-> productService.getProductById(1));
    }

    @Test
    void AddNewProductTest()
    {
      Product newproduct=new Product();
      Product savedProduct=new Product();
      newproduct.setProductName("laptop");
      newproduct.setCategory("hardware");
      newproduct.setPrice(45.00);

      savedProduct.setProductName("laptop");
      savedProduct.setCategory("hardware");
      savedProduct.setPrice(45.00);
      when(productRepository.save(any())).thenReturn(savedProduct);

      Product result=productService.AddNewProduct(newproduct);

      assertEquals(result,savedProduct);

    }

    @Test
    void UpdateProductTests()
    {
        Product prod1=new Product(1,"laptop","hardware",45.00);
        Product updatedProd1=new Product(1,"UpdatedProduct1","UpdatedCategory1",55.00);

        when(productRepository.findById(1)).thenReturn(Optional.of(prod1));
        when(productRepository.save(updatedProd1)).thenReturn(updatedProd1);

        Product product=productService.UpdateProduct(1,updatedProd1);
        assertEquals(updatedProd1,product);

    }

    @Test
    void UpdateProduct_ProductNotFound()
    {
        when(productRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class,()->productService.UpdateProduct(1,new Product()));
    }

    @Test
    void deleteProduct()
    {
        Product prod1=new Product(1,"laptop","hardware",45.00);
        when(productRepository.findById(1)).thenReturn(Optional.of(prod1));

        Product deletedProduct=productService.deleteProductById(1);

        assertEquals(1,deletedProduct.getID());
        verify(productRepository,times(1)).deleteById(1);
    }

    @Test
    void DeleteById_IdNotFound()
    {
        when(productRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class,()->productService.deleteProductById(1));
    }


}
