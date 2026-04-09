package com.mcp.server;

import com.mcp.server.repository.ProductRepository;
import com.mcp.server.service.ProductService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ServerApplication.class);
		app.setLogStartupInfo(false);
		app.setBannerMode(Banner.Mode.OFF);

		ConfigurableApplicationContext context = app.run(args);

		// Add a shutdown hook to gracefully close the application context on JVM shutdown
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("Shutting down application...");
			context.close();
		}));

		// CRITICAL: Keep the application running
		try  {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	// Provide the tools that are available in the ProductService class
	@Bean
	public ToolCallbackProvider productTools(ProductService productService) {
		return MethodToolCallbackProvider.builder()
				.toolObjects(productService)
				.build();
	}
}
