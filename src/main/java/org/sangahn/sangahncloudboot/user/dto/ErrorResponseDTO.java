package org.sangahn.sangahncloudboot.user.dto;

import lombok.Data;

@Data
public class ErrorResponseDTO {

    private int status;
    private String message;
}
