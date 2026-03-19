package org.example.dataaccess.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("products")
public class ProductEntity {
    @Id
    private String id;
    private String proposalId;
    private String upcEan;
    @CreatedDate
    private Date createdAt;
    private String status;
    private UserEntity user;
}
