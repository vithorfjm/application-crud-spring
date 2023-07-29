package com.example.crud.services;

import com.example.crud.domain.category.Category;
import com.example.crud.domain.category.CategoryRepository;
import com.example.crud.domain.product.Product;
import com.example.crud.domain.product.ProductRepository;
import com.example.crud.domain.product.RequestProduct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return repository.findAllByActiveTrue();
    }

    public void registerProduct(RequestProduct data) {
        Optional<Category> optionalCategory = categoryRepository.findById(data.category_id());
        Product newProduct = new Product(data, optionalCategory.get());
        repository.save(newProduct);
    }

    @Transactional
    public void updateProduct(RequestProduct data) {
        Optional<Product> optionalProduct = repository.findById(data.id());
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(data.name());
            product.setPrice_in_cents(data.price_in_cents());
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    public void deleteProduct(int id) {
        Optional<Product> optionalProduct = repository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setActive(false);
        } else {
            throw new EntityNotFoundException();
        }
    }
}