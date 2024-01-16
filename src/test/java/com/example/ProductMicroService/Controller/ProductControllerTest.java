package com.example.ProductMicroService.Controller;

import com.example.ProductMicroService.Model.Product;
import com.example.ProductMicroService.Service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMVC;
    @InjectMocks
    private ProductController productController;
    @MockBean
    private ProductService productService;

    @Test
    void getAllProductsSuccess() throws Exception {
        List<Product> products=new ArrayList<>();
        products.add(new Product(1,"prod1","category1",54.55));
        products.add(new Product(2,"prod2","category2",66.55));

        when(productService.getAllProducts()).thenReturn(products);

        List<Product> prods=productService.getAllProducts();
        assertEquals(products,prods);
        mockMVC.perform(MockMvcRequestBuilders.get("/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void getAllProductsFailure() throws Exception {
        List<Product> products=new ArrayList<>();
        when(productService.getAllProducts()).thenReturn(products);

        assertEquals(0, products.size());

        mockMVC.perform(MockMvcRequestBuilders.get("/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void AddProductSuccess() throws Exception {
        Product product=new Product();
        product.setProductName("product");
        product.setCategory("category");
        product.setPrice(566.44);

        when(productService.AddNewProduct(any())).thenReturn(product);

        ArrayList<Product> products=new ArrayList<>();
        products.add(product);

        assertEquals(1,products.size());
        mockMVC.perform(MockMvcRequestBuilders.post("/products/add").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void AddProductFailure() throws Exception {
        when(productService.AddNewProduct(any())).thenReturn(null);
        Product p=null;
        assertNull(p);
        mockMVC.perform(MockMvcRequestBuilders.post("/products/add").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
