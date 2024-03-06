package com.natan.springbootproject.controllers;

import com.natan.springbootproject.dtos.ProductDTO;
import com.natan.springbootproject.exceptions.InvalidParamException;
import com.natan.springbootproject.models.Product;
import com.natan.springbootproject.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
   ProductService productService;

    @PostMapping
    public ResponseEntity<Object> saveProduct(@RequestBody @Valid ProductDTO productDTO){
        Product createdProduct = productService.saveProductDTO(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable(value = "id")UUID id){
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (InvalidParamException e) {
            return makeResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid ProductDTO productDTO){

        try {
            Product product = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(product);
        } catch (InvalidParamException e) {
            return makeResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    private ResponseEntity<Object> makeResponse(HttpStatus status, Object response){
        return  ResponseEntity.status(status).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id){
        try {
            productService.deleteProductById(id);
            return makeResponse(HttpStatus.OK, "Product Deleted Successfully");
        } catch (InvalidParamException e) {
            return makeResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
