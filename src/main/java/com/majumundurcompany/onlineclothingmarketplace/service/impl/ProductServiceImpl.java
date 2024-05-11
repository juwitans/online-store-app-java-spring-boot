package com.majumundurcompany.onlineclothingmarketplace.service.impl;

import com.majumundurcompany.onlineclothingmarketplace.dto.request.NewProductRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.SearchProductRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.request.UpdateProductRequest;
import com.majumundurcompany.onlineclothingmarketplace.dto.response.ProductBuyerResponse;
import com.majumundurcompany.onlineclothingmarketplace.entity.Merchant;
import com.majumundurcompany.onlineclothingmarketplace.entity.Product;
import com.majumundurcompany.onlineclothingmarketplace.repository.ProductRepository;
import com.majumundurcompany.onlineclothingmarketplace.service.MerchantService;
import com.majumundurcompany.onlineclothingmarketplace.service.OrderService;
import com.majumundurcompany.onlineclothingmarketplace.service.ProductService;
import com.majumundurcompany.onlineclothingmarketplace.util.ValidationUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final MerchantService merchantService;
    private final ValidationUtil validationUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product create(NewProductRequest request) {
        Merchant merchant = merchantService.getById(request.getMerchantId());
        Product product = Product.builder()
                .merchant(merchant)
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .description(request.getDescription())
                .status(Boolean.TRUE)
                .build();
        return productRepository.save(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Page<Product> getAll(SearchProductRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Pageable pageable = PageRequest.of(
                (request.getPage() - 1), request.getSize()
        );
        Specification<Product> specification = getProductSpecification(request);
        return productRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Product> getAllByMerchantId(String id) {
        return productRepository.findAllByMerchantId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product getById(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) return optionalProduct.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"product not found");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product update(UpdateProductRequest request) {
        validationUtil.validate(request);
        Optional<Product> optionalProduct = productRepository.findById(request.getId());
        if (optionalProduct.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found");
        Product product = optionalProduct.get();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setDescription(request.getDescription());
        return productRepository.saveAndFlush(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product update(Product product) {
        Optional<Product> optionalProduct =
                productRepository.findById(product.getId());
        if(optionalProduct.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found");
        return productRepository.save(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"product not found");
        Product product = optionalProduct.get();
        productRepository.delete(product);
    }

    private Specification<Product> getProductSpecification(SearchProductRequest request) {
        Specification<Product> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getName() != null) {
                Predicate namePredicate = criteriaBuilder.like(
                        root.get("name"),
                        "%" + request.getName() + "%"
                );
                predicates.add(namePredicate);
            }

            if (request.getMinPrice() != null) {
                Predicate minPricePredicate = criteriaBuilder.greaterThanOrEqualTo(
                        root.get("price"),
                        request.getMinPrice()
                );
                predicates.add(minPricePredicate);
            }

            if (request.getMaxPrice() != null) {
                Predicate maxPricePredicate = criteriaBuilder.lessThanOrEqualTo(
                        root.get("price"),
                        request.getMaxPrice()
                );
                predicates.add(maxPricePredicate);
            }

            return query.where(predicates.toArray(new Predicate[] {})).getRestriction();
        };
        return specification;
    }
}
