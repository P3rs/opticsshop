package com.example.opticsshop;

import com.example.opticsshop.DAO.models.Product;
import com.example.opticsshop.DAO.models.User;
import com.example.opticsshop.DAO.repositories.ProductRepository;
import com.example.opticsshop.DAO.repositories.UserRepository;
import com.example.opticsshop.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductService(productRepository, userRepository);
    }

    @Test
    void testListProducts() {
        String title = "Test Product";
        String brand = "Test Brand";
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.setTitle(title);
        product.setBrand(brand);
        productList.add(product);
        when(productRepository.findByBrandAndTitle(brand,title)).thenReturn(productList);
        List<Product> result = productService.listProducts(title, brand);
        assertEquals(1,result.size());
        assertEquals(title, result.get(0).getTitle());
        assertEquals(brand, result.get(0).getBrand());
    }


    @Test
    public void testSaveProduct() throws IOException {
        // создаем тестовые данные
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "testuser";
            }
        };
        Product product = new Product();
        product.setTitle("Test Product");
        product.setBrand("Test Brand");
        MultipartFile file1 = new MockMultipartFile("image1.jpg", new byte[10]);
        MultipartFile file2 = new MockMultipartFile("image2.jpg", new byte[20]);
        MultipartFile file3 = new MockMultipartFile("image3.jpg", new byte[30]);

        // вызываем тестируемый метод
        productService.saveProduct(principal, product, file1, file2, file3);

        // проверяем результаты сохранения
        List<Product> products = productService.getAllProducts();
        assertEquals(1, products.size());
        Product savedProduct = products.get(0);
        assertEquals("Test Product", savedProduct.getTitle());
        assertEquals("Test Brand", savedProduct.getBrand());
        assertNotNull(savedProduct.getPreviewImageId());
        assertEquals(3, savedProduct.getImages().size());
    }

    @Test
    void testGetUserByPrincipal() {
        String email = "test@test.com";
        User user = new User();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(user);
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return email;
            }
        };
        User result = productService.getUserByPrincipal(principal);
        assertEquals(email, result.getEmail());
    }

    @Test
    void testDeleteProduct() {
        Long id = 1L;
        Product product = new Product();
        product.setId(id);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        productService.deleteProduct(id);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testGetProductById() {
        Long id = 1L;
        Product product = new Product();
        product.setId(id);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Product result = productService.getProductById(id);
        assertEquals(id, result.getId());
    }
}