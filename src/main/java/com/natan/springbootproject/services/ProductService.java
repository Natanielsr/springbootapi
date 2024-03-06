package com.natan.springbootproject.services;

import com.natan.springbootproject.dtos.ProductDTO;
import com.natan.springbootproject.exceptions.InvalidParamException;
import com.natan.springbootproject.models.Product;
import com.natan.springbootproject.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Product saveProductDTO(ProductDTO productDTO){
        return saveProductDTO(productDTO, new Product());
    }

    public Product saveProductDTO(ProductDTO productDTO, Product product){
        BeanUtils.copyProperties(productDTO, product);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(UUID id) throws InvalidParamException {
        return verifyProductExist(id);
    }

    public Product updateProduct(UUID id, ProductDTO updatedProductDTO) throws InvalidParamException {
        Product product = verifyProductExist(id);
        return saveProductDTO(updatedProductDTO, product);
    }

    private Product verifyProductExist(UUID id) throws InvalidParamException {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isPresent()) {
            return existingProductOptional.get();
        }
        else {
            throw new InvalidParamException("Product not found with id: " + id);
        }
    }

    public void deleteProductById(UUID id)  throws InvalidParamException   {

        verifyProductExist(id);
        productRepository.deleteById(id);
    }


}
