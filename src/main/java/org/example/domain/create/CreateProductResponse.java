package org.example.domain.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateProductResponse {

    private final String productId;
    private final String message;
}
