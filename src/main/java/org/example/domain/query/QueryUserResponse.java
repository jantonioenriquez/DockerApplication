package org.example.domain.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.domain.entity.User;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class QueryUserResponse {
    private final List<User> items;
    private final int page;
    private final int size;
    private final long total;
}
