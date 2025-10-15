package com.complefit.complefit.auth.controller;

import com.complefit.complefit.auth.dto.AuthRequestDTO;
import com.complefit.complefit.auth.dto.AuthResponseDTO;
import com.complefit.complefit.auth.dto.RefreshTokenRequestDTO;
import com.complefit.complefit.auth.exception.AuthException;
import com.complefit.complefit.auth.service.AuthService;
import com.complefit.complefit.infra.exceptions.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    private String toJson(Object o) throws Exception {
        return objectMapper.writeValueAsString(o);
    }

    @Nested
    @DisplayName("POST /api/auth/login")
    class LoginTests {
        @Test
        @DisplayName("should return 200 and tokens when credentials are valid")
        void login_ok() throws Exception {
            AuthResponseDTO tokens = new AuthResponseDTO("access", "refresh");
            given(authService.login(eq("user@email.com"), eq("pass123"))).willReturn(tokens);

            AuthRequestDTO body = new AuthRequestDTO("user@email.com", "pass123");

            mockMvc.perform(post("/api/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(body)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.accessToken", is("access")))
                    .andExpect(jsonPath("$.refreshToken", is("refresh")));
        }

        @Test
        @DisplayName("should return 400 when email is invalid format")
        void login_invalidEmail() throws Exception {
            AuthRequestDTO body = new AuthRequestDTO("not-an-email", "pass123");

            mockMvc.perform(post("/api/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(body)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status", is(400)))
                    .andExpect(jsonPath("$.message", containsString("email")));
        }

        @Test
        @DisplayName("should return 400 when password is blank")
        void login_blankPassword() throws Exception {
            AuthRequestDTO body = new AuthRequestDTO("user@email.com", "");

            mockMvc.perform(post("/api/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(body)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("should return 401 when credentials invalid")
        void login_unauthorized() throws Exception {
            given(authService.login(eq("user@email.com"), eq("wrong")))
                    .willThrow(AuthException.invalidCredentials());

            AuthRequestDTO body = new AuthRequestDTO("user@email.com", "wrong");

            mockMvc.perform(post("/api/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(body)))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.error", is("Authentication Error")))
                    .andExpect(jsonPath("$.message", containsString("Invalid email or password")));
        }

        @Test
        @DisplayName("should map unexpected errors to 500")
        void login_unexpectedError() throws Exception {
            given(authService.login(any(), any())).willThrow(new RuntimeException("boom"));

            AuthRequestDTO body = new AuthRequestDTO("user@email.com", "pass123");

            mockMvc.perform(post("/api/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(body)))
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.message", containsString("boom")));
        }
    }

    @Nested
    @DisplayName("POST /api/auth/refresh")
    class RefreshTests {
        @Test
        @DisplayName("should return 200 and new tokens when refresh token valid")
        void refresh_ok() throws Exception {
            AuthResponseDTO tokens = new AuthResponseDTO("new-access", "new-refresh");
            given(authService.refresh(eq("r1"))).willReturn(tokens);

            RefreshTokenRequestDTO body = new RefreshTokenRequestDTO("r1");

            mockMvc.perform(post("/api/auth/refresh")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(body)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.accessToken", is("new-access")))
                    .andExpect(jsonPath("$.refreshToken", is("new-refresh")));
        }

        @Test
        @DisplayName("should return 400 when refresh token is blank")
        void refresh_blankToken() throws Exception {
            RefreshTokenRequestDTO body = new RefreshTokenRequestDTO("");

            mockMvc.perform(post("/api/auth/refresh")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(body)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("should return 401 when refresh token expired")
        void refresh_expired() throws Exception {
            given(authService.refresh(eq("expired"))).willThrow(AuthException.refreshTokenExpired());

            RefreshTokenRequestDTO body = new RefreshTokenRequestDTO("expired");

            mockMvc.perform(post("/api/auth/refresh")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(body)))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.message", containsString("expired")));
        }

        @Test
        @DisplayName("should return 400 when refresh token invalid format")
        void refresh_invalid() throws Exception {
            given(authService.refresh(eq("bad"))).willThrow(AuthException.invalidRefreshToken());

            RefreshTokenRequestDTO body = new RefreshTokenRequestDTO("bad");

            mockMvc.perform(post("/api/auth/refresh")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(body)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status", is(400)));
        }

        @Test
        @DisplayName("should map unexpected errors to 500 on refresh")
        void refresh_unexpectedError() throws Exception {
            given(authService.refresh(any())).willThrow(new RuntimeException("crash"));

            RefreshTokenRequestDTO body = new RefreshTokenRequestDTO("r1");

            mockMvc.perform(post("/api/auth/refresh")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(body)))
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.message", containsString("crash")));
        }
    }

    @Nested
    @DisplayName("POST /api/auth/logout")
    class LogoutTests {
        @Test
        @DisplayName("should return 204 when logout succeeds")
        void logout_ok() throws Exception {
            doNothing().when(authService).logout(eq("r1"));

            RefreshTokenRequestDTO body = new RefreshTokenRequestDTO("r1");

            mockMvc.perform(post("/api/auth/logout")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(body)))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("should return 400 when refresh token blank on logout")
        void logout_blank() throws Exception {
            RefreshTokenRequestDTO body = new RefreshTokenRequestDTO("");

            mockMvc.perform(post("/api/auth/logout")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(body)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("should return 401 when refresh token expired on logout")
        void logout_expired() throws Exception {
            org.mockito.Mockito.doThrow(AuthException.refreshTokenExpired())
                    .when(authService).logout(eq("expired"));

            RefreshTokenRequestDTO body = new RefreshTokenRequestDTO("expired");

            mockMvc.perform(post("/api/auth/logout")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(body)))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("should return 403 when access denied during logout")
        void logout_accessDenied() throws Exception {
            org.mockito.Mockito.doThrow(AuthException.accessDenied())
                    .when(authService).logout(eq("denied"));

            RefreshTokenRequestDTO body = new RefreshTokenRequestDTO("denied");

            mockMvc.perform(post("/api/auth/logout")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(body)))
                    .andExpect(status().isForbidden())
                    .andExpect(jsonPath("$.status", is(403)));
        }

        @Test
        @DisplayName("should map unexpected errors to 500 on logout")
        void logout_unexpected() throws Exception {
            org.mockito.Mockito.doThrow(new RuntimeException("oops"))
                    .when(authService).logout(eq("r1"));

            RefreshTokenRequestDTO body = new RefreshTokenRequestDTO("r1");

            mockMvc.perform(post("/api/auth/logout")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(body)))
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.message", containsString("oops")));
        }
    }
}


