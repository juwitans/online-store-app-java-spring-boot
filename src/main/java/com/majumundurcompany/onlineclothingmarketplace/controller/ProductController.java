package com.majumundurcompany.onlineclothingmarketplace.controller;

import com.majumundurcompany.onlineclothingmarketplace.dto.request.NewProductRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.SearchProductRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.UpdateProductRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.PagingResponse;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.WebResponse;
import com.majumundurcompany.onlineclothingmarketplace.entity.Product;
import com.majumundurcompany.onlineclothingmarketplace.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createNewProduct(@RequestBody NewProductRequest request) {
        Product newProduct = productService.create(request);
        WebResponse<Product> response = WebResponse.<Product>builder()
                .message("successfully create new product")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(newProduct)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) Long minPrice,
                                    @RequestParam(required = false) Long maxPrice) {
        SearchProductRequest request = SearchProductRequest.builder()
                .page(page)
                .size(size)
                .name(name)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();
        Page<Product> products = productService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(request.getPage())
                .size(products.getSize())
                .totalPages(products.getTotalPages())
                .totalElements(products.getTotalElements())
                .build();

        WebResponse<List<Product>> response = WebResponse.<List<Product>>builder()
                .message("sucessfully get all product")
                .status(HttpStatus.OK.getReasonPhrase())
                .paging(pagingResponse)
                .data(products.getContent())
                .build();

        return ResponseEntity.ok(response);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        Product product = productService.getById(id);
        WebResponse<Product> response = WebResponse.<Product>builder()
                .message("successfully get product")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(product)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        productService.deleteById(id);
        WebResponse<Product> response = WebResponse.<Product>builder()
                .message("successfully delete product")
                .status(HttpStatus.OK.getReasonPhrase())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody UpdateProductRequest product) {
        Product updated = productService.update(product);
        WebResponse<Product> response = WebResponse.<Product>builder()
                .message("successfully update product")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(updated)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/merchant/{id}")
    public ResponseEntity<?> getAllProductByMerchantId(@PathVariable String id) {
        List<Product> products = productService.getAllByMerchantId(id);
        WebResponse<List<Product>> response = WebResponse.<List<Product>>builder()
                .message("successfully get all products by merchant id")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(products)
                .build();
        return ResponseEntity.ok(response);
    }
}
