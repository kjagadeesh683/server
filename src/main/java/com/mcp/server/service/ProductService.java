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
                    product.getId(), product.getName(), product.getCategory(), product.getPrice(),
                    product.getStock()));
        }
        return result.toString();
    }

    @Tool(description = "Searches for products by Category Name. " +
            "Returns all products that match the specified category (case-sensitive). " +
            "Common categories include: Electronics, Books, Clothing, Appliances.")
    public String searchByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);

        if(products.isEmpty()) {
            return String.format("No products found in category: %s", category);
        }

        StringBuilder result = new StringBuilder();
        result.append(String.format("Found %d products in category '%s':\n",
                products.size(), category));

        for (Product product : products) {
            result.append(String.format("ID: %d, Name: %s, Price: %.2f, Stock: %d\n",
                    product.getId(), product.getName(), product.getPrice(), product.getStock()));
        }
        return result.toString();
    }

    @Tool(description = "Finds all products that are priced below a specified maximum price. " +
            "Useful for finding budget-friendly options or products within a price range" +
            "Price should be specified as a decimal number (e.g., 50.00).")
    public String finsProductsUnderPrice(double maxPrice) {
        List<Product> products = productRepository.findByPriceLessThan(maxPrice);

        if(products.isEmpty()) {
            return String.format("No products found under price: %.2f", maxPrice);
        }

        StringBuilder result = new StringBuilder();
        result.append(String.format("Found %d products under price %.2f:\n",
                products.size(), maxPrice));

        for (Product product : products) {
            result.append(String.format("ID: %d, Name: %s, Category: %s, Price: %.2f, Stock: %d\n",
                    product.getId(), product.getName(), product.getCategory(),
                    product.getPrice(), product.getStock()));
        }
        return result.toString();
    }

    @Tool(description = "Adds a new product to the inventory database. " +
            "Requires: name (product name), category (product category), price (decimal price), " +
            "and stock (integer quantity). Returns confirmation with the created product details.")
    public String addProduct(String name, String category, double price, int stock) {
        // Validation
        if (name == null || name.trim().isEmpty()) {
            return "Error: Product name is required and cannot be empty";
        }

        if (category == null || category.trim().isEmpty()) {
            return "Error: Product category is required and cannot be empty";
        }

        if (price < 0) {
            return "Error: Product price cannot be negative";
        }

        if (stock < 0) {
            return "Error: Product stock cannot be negative";
        }

        Product product = new Product(name, category, price, stock);
        Product savedProduct = productRepository.save(product);

        return String.format("Product added successfully. " +
                "ID: %d, Name: %s, Category: %s, Price: %.2f, Stock: %d units",
                savedProduct.getId(), savedProduct.getName(), savedProduct.getCategory(),
                savedProduct.getPrice(), savedProduct.getStock());
    }

    @Tool(description = "Updates an existing product's information in the inventory. " +
            "Require the productId and new values for name, category, price, and stock. " +
            "ALl fields are required even if only updating one field.")
    public String updateProductStock(long id, String name, String category, double price, int stock) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(name);
                    product.setCategory(category);
                    product.setPrice(price);
                    product.setStock(stock);
                    Product updatedProduct = productRepository.save(product);

                    return String.format("Product updated successfully. " +
                            "ID: %d, Name: %s, Category: %s, Price: %.2f, Stock: %d units",
                            updatedProduct.getId(), updatedProduct.getName(), updatedProduct.getCategory(),
                            updatedProduct.getPrice(), updatedProduct.getStock());
                }).orElse(String.format("Error: Product with ID %d not found", id));
    }

    @Tool(description = "Deletes a product from the inventory by it's ID. " +
            "Returns confirmation of deletion or error if the product is not found.")
    public String deleteProduct(long id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return String.format("Product '%s' with ID %d deleted successfully",
                            product.getName(), product.getId());
                }).orElse(String.format("Error: Product with ID %d not found", id));
    }
}
