package com.majumundurcompany.onlineclothingmarketplace.service;

import com.majumundurcompany.onlineclothingmarketplace.dto.request.NewProductRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.SearchProductRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.UpdateProductRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.ProductBuyerResponse;
import com.majumundurcompany.onlineclothingmarketplace.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product create(NewProductRequest request);
    Page<Product> getAll(SearchProductRequest request);
    List<Product> getAllByMerchantId(String id);
    Product getById(String id);
    Product update(UpdateProductRequest request);
    Product update(Product product);
    void deleteById(String id);
}
