package com.project.MCP.Server.config;

import com.project.MCP.Server.entity.Product;
import com.project.MCP.Server.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(ProductRepository productRepository) {
        return args -> {
            productRepository.save(new Product("Smartphone", "Electronics", 699.99, 50));
            productRepository.save(new Product("Laptop", "Electronics", 999.99, 30));
            productRepository.save(new Product("Headphones", "Electronics", 199.99, 100));
            productRepository.save(new Product("Fiction Book", "Books", 14.99, 200));
            productRepository.save(new Product("Non-Fiction Book", "Books", 24.99, 150));
            productRepository.save(new Product("T-Shirt", "Clothing", 19.99, 300));
            productRepository.save(new Product("Jeans", "Clothing", 49.99, 100));
            productRepository.save(new Product("Blender", "Appliances", 89.99, 80));
            productRepository.save(new Product("Microwave Oven", "Appliances", 149.99, 40));
        };
    }
}
