package org.sangahn.sangahncloudboot.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.sangahn.sangahncloudboot.security.util.JWTUtil;
import org.sangahn.sangahncloudboot.user.dto.ErrorResponseDTO;
import org.sangahn.sangahncloudboot.user.dto.LoginDTO;
import org.sangahn.sangahncloudboot.user.dto.TokenRequestDTO;
import org.sangahn.sangahncloudboot.user.dto.TokenResponseDTO;
import org.sangahn.sangahncloudboot.user.exception.LoginException;
import org.sangahn.sangahncloudboot.user.exception.LoginTaskException;
import org.sangahn.sangahncloudboot.user.service.LoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
@Log4j2
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    private final JWTUtil jwtUtil;

    @Value("${org.sangahn.accessTime}")
    private int accessTime;

    @Value("${org.sangahn.refreshTime}")
    private int refreshTime;

    @Value("${org.sangahn.alwaysNew}")
    private boolean alwaysNew;

    @PostMapping("makeToken")
    public ResponseEntity<?> makeToken(@RequestBody @Validated TokenRequestDTO tokenRequestDTO){

        LoginDTO loginDTO = null;
        try{
            loginDTO = loginService.authenticate(
                    tokenRequestDTO.getUserId(),
                    tokenRequestDTO.getPassword()
            );

            //아이디 패스워드가 null이거나 비었을 때 예외처리
            if (tokenRequestDTO.getUserId() == null || tokenRequestDTO.getUserId().isEmpty() ||
            tokenRequestDTO.getPassword() == null || tokenRequestDTO.getPassword().isEmpty()){
                log.error("아이디나 비밀번호가 비어 있거나 null입니다.");
                LoginTaskException exception = LoginException.BAD_AUTH.getException();

                return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
            }
        } catch (Exception e){
            log.error("authenticate에서 문제가 발생했습니다.: {}", e.getMessage());
            LoginTaskException exception = LoginException.BAD_AUTH.getException();
            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
            errorResponseDTO.setStatus(exception.getStatus());
            errorResponseDTO.setMessage(exception.getMessage());

            return ResponseEntity.status(exception.getStatus()).body(errorResponseDTO);
        }

        Map<String, Object> claimMap = new HashMap<>();
        claimMap.put("userId", loginDTO.getUserId());

        String accessToken = jwtUtil.createToken(claimMap, accessTime);
        String refreshToken = jwtUtil.createToken(claimMap, refreshTime);

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        tokenResponseDTO.setAccessToken(accessToken);
        tokenResponseDTO.setRefreshToken(refreshToken);
        tokenResponseDTO.setUserId(loginDTO.getUserId());

        log.info("Token created successfully for userId: {}", loginDTO.getUserId());

        return ResponseEntity.ok(tokenResponseDTO);
    }

    // ErrorResponse 생성 함수
    private ResponseEntity<ErrorResponseDTO> createErrorResponse(LoginException exception) {
        return createErrorResponse(exception, exception.getMessage());
    }

    // ErrorResponse 생성 함수 (커스터마이즈된 메시지 포함)
    private ResponseEntity<ErrorResponseDTO> createErrorResponse(LoginException exception, String customMessage) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setStatus(exception.getStatus());
        errorResponse.setMessage(customMessage);
        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }

    @PostMapping(value = "refreshToken", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String accessToken, @RequestParam String refreshToken){

        //accessToken 또는 refreshToken이 없는 경우
        if(accessToken == null || refreshToken == null){
            log.info("Please provide accessToken and refreshToken Together.");
            return createErrorResponse(LoginException.TOKEN_NOT_ENOUGH);
        }

        // accessToken의 형식이 올바르지 않은 경우 처리
        if (!accessToken.startsWith("Bearer ")) {
            log.info("AccessToken has wrong format.");
            return createErrorResponse(LoginException.ACCESSTOKEN_TOO_SHORT);
        }

        String accessTokenStr = accessToken.substring("Bearer ".length());

        // accessToken 검증
        try {
            Map<String, Object> payload = jwtUtil.validateToken(accessTokenStr);
            String userId = payload.get("userId").toString();

            TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
            tokenResponseDTO.setAccessToken(accessTokenStr);
            tokenResponseDTO.setUserId(userId);
            tokenResponseDTO.setRefreshToken(refreshToken);

            // accessToken 검증 성공 후, refreshToken 검증
            try {
                Map<String, Object> payloadRefresh = jwtUtil.validateToken(refreshToken);
                String refreshUserId = payloadRefresh.get("userId").toString();

                String newAccessToken = null;
                String newRefreshToken = null;

                if (alwaysNew) {
                    Map<String, Object> claimMap = Map.of("userId", userId);
                    newAccessToken = jwtUtil.createToken(claimMap, accessTime);
                    newRefreshToken = jwtUtil.createToken(claimMap, refreshTime);
                }

                tokenResponseDTO.setAccessToken(newAccessToken);
                tokenResponseDTO.setRefreshToken(newRefreshToken);
                tokenResponseDTO.setUserId(userId);

                return ResponseEntity.ok(tokenResponseDTO);

            } catch (Exception refreshEx) {
                // refreshToken이 만료되었거나 문제가 있을 경우
                log.error("Refresh Token problem: {}", refreshEx.getMessage());
                return createErrorResponse(LoginException.REFRESHTOKEN_EXPIRED);
            }

        } catch (Exception accessEx) {
            // accessToken이 잘못되었으면 상태 메시지 반환
            log.error("Access Token problem: {}", accessEx.getMessage());
            try{
                Map<String, Object> payloadRefresh = jwtUtil.validateToken(refreshToken);
                String userId = payloadRefresh.get("userId").toString();

                // 새 액세스 토큰 발급
                String newAccessToken = jwtUtil.createToken(Map.of("userId", userId), accessTime);

                TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
                tokenResponseDTO.setAccessToken(newAccessToken);
                tokenResponseDTO.setRefreshToken(refreshToken);
                tokenResponseDTO.setUserId(userId);

                return ResponseEntity.ok(tokenResponseDTO);
            } catch (Exception refreshEx) {
                // refreshToken도 만료된 경우
                log.error("Both Access Token and Refresh Token have issues: {}", refreshEx.getMessage());
                return createErrorResponse(LoginException.REFRESHTOKEN_EXPIRED);
            }
        }
    }

}
