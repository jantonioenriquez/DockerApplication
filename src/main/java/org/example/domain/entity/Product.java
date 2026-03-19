package org.example.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.example.domain.valueObject.AggregateRoot;
import org.example.domain.valueObject.ProductId;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Product extends AggregateRoot<ProductId> {
    private String proposalId;
    private String upcEan;
    private Date createdAt;
    private String status;
    private User user;

    public Product (ProductId id, String proposalId, String upcEan, Date createdAt, String status, User user){
        super.setId(id);
        this.proposalId = proposalId;
        this.upcEan = upcEan;
        this.createdAt = createdAt;
        this.status = status;
        this.user = user;
    }
}
