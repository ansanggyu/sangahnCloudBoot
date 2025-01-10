package org.sangahn.sangahncloudboot.user.controller.advice;

import lombok.extern.log4j.Log4j2;
import org.sangahn.sangahncloudboot.user.dto.ErrorResponseDTO;
import org.sangahn.sangahncloudboot.user.exception.LoginTaskException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class LoginControllerAdvice {

    @ExceptionHandler(LoginTaskException.class)
    public ResponseEntity<ErrorResponseDTO> ex(LoginTaskException ex) {
        StackTraceElement[] stackTrace = ex.getStackTrace();
        log.error("====================LOGIN ERROR RESPONSE====================");
        for (StackTraceElement stackTraceElement : stackTrace) {
            log.error(stackTraceElement.toString());
        }
        log.error("====================LOGIN ERROR RESPONSE====================");

        // ErrorResponseDTO에 상태 코드와 메시지를 담아서 반환
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setStatus(ex.getStatus());
        errorResponseDTO.setMessage(ex.getMessage());

        return ResponseEntity.status(ex.getStatus()).body(errorResponseDTO);
    }
}
