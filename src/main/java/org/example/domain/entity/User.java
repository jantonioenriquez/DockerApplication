package org.example.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.example.common.application.valueObject.AggregateRoot;
import org.example.common.application.valueObject.UserId;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User extends AggregateRoot<UserId> {
    private String email;
    private String name;
    private String lastNameP;
    private String lastNameM;
    private Date createdAt;
    private Date updatedAt;

    public User (UserId userId, String email, String name, String lastNameP, String lastNameM, Date createdAt, Date updatedAt){
        super.setId(userId);
        this.email = email;
        this.name = name;
        this.lastNameP = lastNameP;
        this.lastNameM = lastNameM;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
