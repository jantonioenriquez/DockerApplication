package org.example.domain.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QueryUsersCommand {
    private final String order;
    private final String sort;
    private final int size;
    private final int page;
    private final String email;
}
