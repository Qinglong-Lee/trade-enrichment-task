package com.verygoodbank.tes.holder;

import com.verygoodbank.tes.constant.Constants;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * holder of the product.csv cache
 * autoload the data when started
 */
@Component
public class ProductCacheHolder {
    private static final Logger LOG = LoggerFactory.getLogger(ProductCacheHolder.class);
//    Concurrency safe map for product.csv
    private final ConcurrentHashMap<String, String> productMap = new ConcurrentHashMap<>();


    @Value("${enrich.product.file}")
    private String PRODUCT_FILE;

    @PostConstruct
    public void loadProductData() {
        try {
//            NIO for better IO performance
            Path path = Paths.get(PRODUCT_FILE);
            List<String> lines = Files.readAllLines(path);

            lines.forEach(line -> {
                String[] parts = line.split(",");
                productMap.put(parts[0], parts[1]);
            });

        } catch (Exception e) {
            throw new RuntimeException("Failed to load product data", e);
        }
    }

    public String getProductName(String productId) {
        if (productMap.isEmpty()) {
            throw new IllegalStateException("Product data not loaded");
        }

        if (productMap.containsKey(productId)) {
            return productMap.get(productId);
        } else {
            LOG.error("Product name not found for product id: {}", productId);
            return Constants.DEFAULT_PRODUCT_NAME;
        }
    }
}
