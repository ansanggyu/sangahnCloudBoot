package org.sangahn.sangahncloudboot.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.sangahn.sangahncloudboot.common.BasicEntity;

@Getter
@Entity
@Table(name = "user")
public class UserEntity extends BasicEntity {

    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "user_password", nullable = false)
    private String userPassword;
}
