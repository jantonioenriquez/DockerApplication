package org.example.domain.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.domain.entity.Product;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class QueryProductResponse {
    private final List<Product> products;
    private final int page;
    private final int size;
    private final long total;
}
