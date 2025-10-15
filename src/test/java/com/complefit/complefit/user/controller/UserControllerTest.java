package com.complefit.complefit.user.controller;

import com.complefit.complefit.infra.exceptions.GlobalExceptionHandler;
import com.complefit.complefit.user.domain.UserRole;
import com.complefit.complefit.user.dto.UserRequestDTO;
import com.complefit.complefit.user.dto.UserResponseDTO;
import com.complefit.complefit.user.dto.UserUpdateDTO;
import com.complefit.complefit.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    private String toJson(Object o) throws Exception { return objectMapper.writeValueAsString(o); }

    private UserResponseDTO sampleUser(UUID id) {
        return new UserResponseDTO(
                id,
                "John",
                "Doe",
                "john@example.com",
                "+5511999999999",
                LocalDate.of(1990, 1, 1),
                "12345678901",
                "MALE",
                180.0,
                80.0,
                UserRole.STUDENT,
                Instant.now(),
                Instant.now()
        );
    }

    @Test
    @DisplayName("POST /api/users/register - created and returns payload")
    void register_created() throws Exception {
        UUID id = UUID.randomUUID();
        given(userService.createUser(any())).willReturn(sampleUser(id));

        UserRequestDTO req = new UserRequestDTO(
                "John", "Doe", "john@example.com", "pass123", "+5511999999999",
                LocalDate.of(1990,1,1), "12345678901", null, 180.0, 80.0
        );

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(id.toString())))
                .andExpect(jsonPath("$.email", is("john@example.com")));
    }

    @Test
    @DisplayName("POST /api/users/register - validation error for bad email")
    void register_validation() throws Exception {
        UserRequestDTO req = new UserRequestDTO(
                "John", "Doe", "bad-email", "pass123", "+5511999999999",
                LocalDate.of(1990,1,1), "12345678901", null, 180.0, 80.0
        );

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", containsString("Email")));
    }

    @Test
    @DisplayName("GET /api/users/{id} - returns user")
    void getById_ok() throws Exception {
        UUID id = UUID.randomUUID();
        given(userService.getUserById(eq(id))).willReturn(sampleUser(id));

        mockMvc.perform(get("/api/users/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.toString())))
                .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
    @DisplayName("GET /api/users - returns list")
    void getAll_ok() throws Exception {
        UserResponseDTO u1 = sampleUser(UUID.randomUUID());
        UserResponseDTO u2 = sampleUser(UUID.randomUUID());
        given(userService.getAllUsers()).willReturn(List.of(u1, u2));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("PUT /api/users/{id} - updates and returns user")
    void update_ok() throws Exception {
        UUID id = UUID.randomUUID();
        UserResponseDTO updated = sampleUser(id);
        given(userService.updateUser(eq(id), any())).willReturn(updated);

        UserUpdateDTO body = new UserUpdateDTO(
                "Johnny", "D", "+5511888888888", 181.0, 79.0, "MALE", LocalDate.of(1990,1,1), "12345678901"
        );

        mockMvc.perform(put("/api/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.toString())));
    }

    @Test
    @DisplayName("PUT /api/users/{id} - validation error")
    void update_validationError() throws Exception {
        UUID id = UUID.randomUUID();
        UserUpdateDTO body = new UserUpdateDTO(
                "J", "D", "invalid-phone", -1.0, -2.0, "MALE", LocalDate.now().plusDays(1), "abc"
        );

        mockMvc.perform(put("/api/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /api/users/{id} - returns deactivated user")
    void delete_ok() throws Exception {
        UUID id = UUID.randomUUID();
        given(userService.inactiveUSer(eq(id))).willReturn(sampleUser(id));

        mockMvc.perform(delete("/api/users/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.toString())));
    }

    @Test
    @DisplayName("DELETE /api/users/{id} - returns 404 when not found")
    void delete_notFound() throws Exception {
        UUID id = UUID.randomUUID();
        given(userService.inactiveUSer(eq(id))).willThrow(new RuntimeException("not found"));

        mockMvc.perform(delete("/api/users/" + id))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", containsString("not found")));
    }
}


