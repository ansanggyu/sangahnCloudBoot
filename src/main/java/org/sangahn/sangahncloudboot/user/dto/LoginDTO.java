package org.sangahn.sangahncloudboot.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginDTO {
    private String userId;
    private String password;
    private String userName;
    private String userEmail;

    public LoginDTO(String userId, String password, String userName, String userEmail) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.userEmail = userEmail;
    }
}
