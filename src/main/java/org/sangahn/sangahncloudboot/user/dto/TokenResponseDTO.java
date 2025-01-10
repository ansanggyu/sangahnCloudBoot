package org.sangahn.sangahncloudboot.user.dto;

import lombok.Data;

@Data
public class TokenResponseDTO {

    private String userId;
    private String accessToken;
    private String refreshToken;
    private int status;
    private String message;
}
