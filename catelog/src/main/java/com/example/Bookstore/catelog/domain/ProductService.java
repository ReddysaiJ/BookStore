package com.example.Bookstore.catelog.domain;

import com.example.Bookstore.catelog.ApplicationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ProductService {
    private final ProductRepository repository;
    private final ApplicationProperties properties;

    public ProductService(ProductRepository repository, ApplicationProperties properties) {
        this.repository = repository;
        this.properties = properties;
    }

    public PagedResult<Product> getproducts(int pageNo){
        Sort sort = Sort.by("name").ascending();
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(pageNo, properties.pageSize(), sort);
        var productEntityPage = repository.findAll(pageable)
                .map(ProductMapper::toProduct);
        return new PagedResult<>(
                productEntityPage.getContent(),
                productEntityPage.getTotalElements(),
                productEntityPage.getNumber() + 1,
                productEntityPage.getTotalPages(),
                productEntityPage.isFirst(),
                productEntityPage.isLast(),
                productEntityPage.hasNext(),
                productEntityPage.hasPrevious()
        );
    }

    public Optional<Product> getProductByCode(String code){
        return repository.findByCode(code).map(ProductMapper::toProduct);
    }
}
