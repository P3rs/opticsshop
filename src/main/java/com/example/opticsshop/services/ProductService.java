package com.example.opticsshop.services;

import com.example.opticsshop.DAO.models.Image;
import com.example.opticsshop.DAO.models.Product;
import com.example.opticsshop.DAO.models.User;
import com.example.opticsshop.DAO.repositories.ProductRepository;
import com.example.opticsshop.DAO.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.security.Principal;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public  List <Product> listProducts(String title, String brand){
        if (title==null && brand==null)
            return productRepository.findAll();
        else if (title==null || title.equals(""))
            return productRepository.findByBrand(brand);
        else if (brand==null || brand.equals(""))
            return productRepository.findByTitle(title);
        return productRepository.findByBrandAndTitle(brand, title);
    }

    public void saveProduct(Principal principal, Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        product.setUser(getUserByPrincipal(principal));
        Image image1;
        Image image2;
        Image image3;
        if (file1.getSize() != 0){
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
        }
        if (file2.getSize() != 0){
            image2 = toImageEntity(file2);
            product.addImageToProduct(image2);
        }
        if (file3.getSize() != 0){
            image3 = toImageEntity(file3);
            product.addImageToProduct(image3);
        }
        log.info("Saving new Product. Title: {}; Brand: {}", product.getTitle(), product.getBrand());
        Product productFromDB = productRepository.save(product);
        productFromDB.setPreviewImageId(productFromDB.getImages().get(0).getId());
        productRepository.save(product);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null)
            return new User();
        return userRepository.findByEmail(principal.getName());
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElse(null);
        if (product != null) {
            productRepository.delete(product);
            log.info("Product with id = {} was deleted", id);
        }
    }



    public Product getProductById(Long id) {
       return productRepository.findById(id).orElse(null);
    }
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
}

