package com.mcp.server.service;

import com.mcp.server.entity.Product;
import com.mcp.server.repository.ProductRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Tool(description = "Retrieves all products from the inventory database. " +
            "Returns a formatted list of all products with their details " +
            "including ID, name, category, price, and stock quantity.")
    public String getAllProducts() {
        List<Product> products = productRepository.findAll();

        StringBuilder result = new StringBuilder();
        result.append(String.format("Found %d products:\n", products.size()));
        for (Product product : products) {
            result.append(String.format("ID: %d, Name: %s, Category: %s, Price: %.2f, Stock: %d\n",
                    product.getId(), product.getName(), product.getCategory(), product.getPrice(), product.getStock()));
        }
        return result.toString();

    }
}
