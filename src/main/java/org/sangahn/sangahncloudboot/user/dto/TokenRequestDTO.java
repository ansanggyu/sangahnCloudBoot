package org.sangahn.sangahncloudboot.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TokenRequestDTO {

    @NotNull(message = "UserId는 null일 수 없습니다.")
    private String userId;

    @NotNull(message = "Password는 null일 수 없습니다.")
    private String password;
}
