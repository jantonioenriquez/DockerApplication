package org.example.domain.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class QueryProductCommand {
    private final int size;
    private final int page;
    private String order;
    private String sort;
    private String proposalId;
    private String upcEan;
    private String userId;
}
