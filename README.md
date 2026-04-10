**Spring AI MCP Server**

A demo project showcasing how to build an MCP (Model Context Protocol) Server using Spring AI. This project demonstrates integrating an AI assistant with a product inventory system through the MCP protocol.

**Table of Contents**

* Overview
* Architecture Flow
* Tools & Technologies
* How It Works
* Available Tools
* Setup & Installation
* Configuration
* How Components Connect

**Overview**

This project implements an MCP Server that exposes product inventory operations as AI-callable tools. The MCP (Model Context Protocol) enables AI assistants like Claude to interact with external systems through a standardized protocol.

What is MCP?

* MCP (Model Context Protocol) is an open protocol that allows AI models to:
* Discover and call external tools/functions
* Access contextual data from various sources
* Interact with real-world systems securely


**Key Components**

| Component       | Description                                                      |
| --------------- | ---------------------------------------------------------------- |
| **MCP Host**    | Claude Desktop - The AI assistant interface                      |
| **MCP Client**  | Built into Claude Desktop - Handles tool discovery and execution |
| **MCP Server**  | This Spring Boot application - Exposes inventory tools           |
| **Data Source** | H2 In-Memory Database - Stores product inventory                 |

**Architecture Flow**

<img width="917" height="483" alt="Product Inventory architecture diagram" src="https://github.com/user-attachments/assets/1cc3fd66-a71e-4d6c-a771-329eabbf5036" />



**Tools & Technologies**
* Spring Boot - Framework for building the MCP Server
* Spring Web - For creating RESTful endpoints
* Spring Data JPA - For database interactions
* H2 Database - In-memory database for storing product data
* MCP Protocol - For AI-tool communication
* Java - Programming language used for the server implementation
* MCP Client Library - For handling MCP interactions on the client side
* Claude Desktop - AI assistant interface for testing the MCP Server
* Git - Version control system for managing the project codebase
* Gradle - Build tool for managing dependencies and building the project
* Lombok - For reducing boilerplate code in Java classes


**How It Works**
1. Tool Registration
The @Tool annotation from Spring AI marks methods as callable tools:

<img width="686" height="312" alt="tool registration" src="https://github.com/user-attachments/assets/62d5e9a7-8f76-44d6-ac60-68e58ed1e9a1" />


2. Tool Callback Provider
The McpServerApplication registers the service as a tool provider:

<img width="685" height="152" alt="toll callback provider" src="https://github.com/user-attachments/assets/9bd3857c-656f-4833-9db3-90737af123f4" />


3. MCP Protocol Communication
The MCP Client in Claude Desktop discovers the registered tools and can call them with appropriate parameters. 
The server processes the requests and returns results back to the client.

The MCP Server communicates with the MCP Client (Claude Desktop) via:
STDIO Transport: Standard input/output for local communication
JSON-RPC: Message format for tool discovery and invocation

**Available Tools**

1. getAllProducts()

Description: Retrieves all products from the inventory database.

Returns: Formatted list of all products with: Product ID, Name, Category, Price, Stock quantity

Example-
User: "Show me all products"
→ Claude calls getAllProducts()
→ Returns formatted inventory list

2. searchByCategory(String category)
                       
Description: Searches for products by category name.

Returns: List of products matching the category or "No products found" message.

Example-
User: "What electronics do we have?"
→ Claude calls searchByCategory("Electronics")
→ Returns matching products
      
4. addProduct(String name, String category, double price, int stock)

Description: Adds a new product to the inventory.

Returns: Confirmation message with new product ID.

Example-
User: "Add a new product: Name=Smartphone, Category=Electronics, Price=699.99, Stock=50"
→ Claude calls addProduct("Smartphone", "Electronics", 699.99, 50)
→ Returns "Product added with ID: 101"

5. updateStock(int productId, int newStock)

Description: Updates the stock quantity of an existing product.

Returns: Confirmation message with updated stock quantity.

Example-
User: "Update stock for product ID 101 to 30"
→ Claude calls updateStock(101, 30)
→ Returns "Stock updated for product ID 101: New stock = 30"

6. deleteProduct(int productId)

Description: Deletes a product from the inventory.

Returns: Confirmation message of deletion.

Example-
User: "Delete product with ID 101"
→ Claude calls deleteProduct(101)
→ Returns "Product with ID 101 has been deleted"

**Setup & Installation**

Prerequisites
1. Java 17 or higher
2. Gradle 8.0 or higher
3. Claude Desktop App (for MCP Host)

**Clone & Build**
# Clone the repository
git clone https://github.com/leetjourney/spring-ai-mcp-server.git
cd spring-ai-mcp-server

# Build the project
./gradlew clean build

# Run the application
./gradlew bootRun

**Configure Claude Desktop**

**Add the following to your Claude Desktop configuration (claude_desktop_config.json):**
                 
<img width="684" height="276" alt="configure claude desktop" src="https://github.com/user-attachments/assets/fa8ff81d-8146-49c3-ab64-a6ad24ab9309" />


**Config file locations:**

macOS: ~/Library/Application Support/Claude/claude_desktop_config.json

Windows: %APPDATA%/Claude/claude_desktop_config.json

**How Components Connect**
 
1. Spring AI MCP Server Starter
The spring-ai-starter-mcp-server dependency provides:
* MCP Server Auto-configuration: Automatically sets up the MCP server
* Tool Discovery: Exposes @Tool annotated methods to clients
* STDIO Transport: Handles communication via standard input/output

implementation 'org.springframework.ai:spring-ai-starter-mcp-server'

2. Tool Annotation Processing

Spring AI scans for @Tool annotations and:
* Extracts method metadata (name, parameters, description)
* Generates tool schemas for LLM understanding
* Creates callable endpoints for the MCP protocol

**Learn More**

Spring AI MCP Documentation: https://docs.spring.io/spring-ai/reference/api/mcp.html

MCP Protocol Specification: https://modelcontextprotocol.io/

Anthropic MCP Guide: https://docs.anthropic.com/en/docs/build-with-claude/mcp
