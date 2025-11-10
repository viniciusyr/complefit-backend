package com.complefit.complefit.exercise.service;

import com.complefit.complefit.exercise.dto.ExerciseDTO;
import com.complefit.complefit.exercise.exception.ExerciseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ExerciseService {

    private final WebClient webClient;

    public ExerciseService(@Qualifier("exerciseDbWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Search exercises by name
     */
    public List<ExerciseDTO> searchByName(String name, int limit, int offset) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/exercises/name/{name}")
                            .queryParam("limit", limit)
                            .queryParam("offset", offset)
                            .build(name))
                    .retrieve()
                    .bodyToFlux(ExerciseDTO.class)
                    .collectList()
                    .onErrorResume(WebClientResponseException.class, this::handleApiError)
                    .block();
        } catch (Exception e) {
            throw ExerciseException.apiError(e.getMessage());
        }
    }

    /**
     * Get exercise by ID
     */
    public ExerciseDTO getById(String id) {
        try {
            return webClient.get()
                    .uri("/exercises/exercise/{id}", id)
                    .retrieve()
                    .bodyToMono(ExerciseDTO.class)
                    .onErrorResume(WebClientResponseException.class, this::handleApiError)
                    .block();
        } catch (Exception e) {
            if (e.getMessage().contains("404")) {
                throw ExerciseException.notFound(id);
            }
            throw ExerciseException.apiError(e.getMessage());
        }
    }

    /**
     * Get all exercises with pagination
     */
    public List<ExerciseDTO> getAll(int limit, int offset) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/exercises")
                            .queryParam("limit", limit)
                            .queryParam("offset", offset)
                            .build())
                    .retrieve()
                    .bodyToFlux(ExerciseDTO.class)
                    .collectList()
                    .onErrorResume(WebClientResponseException.class, this::handleApiError)
                    .block();
        } catch (Exception e) {
            throw ExerciseException.apiError(e.getMessage());
        }
    }

    /**
     * Search by target muscle
     */
    public List<ExerciseDTO> searchByTarget(String target, int limit, int offset) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/exercises/target/{target}")
                            .queryParam("limit", limit)
                            .queryParam("offset", offset)
                            .build(target))
                    .retrieve()
                    .bodyToFlux(ExerciseDTO.class)
                    .collectList()
                    .onErrorResume(WebClientResponseException.class, this::handleApiError)
                    .block();
        } catch (Exception e) {
            throw ExerciseException.apiError(e.getMessage());
        }
    }

    /**
     * Search by body part
     */
    public List<ExerciseDTO> searchByBodyPart(String bodyPart, int limit, int offset) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/exercises/bodyPart/{bodyPart}")
                            .queryParam("limit", limit)
                            .queryParam("offset", offset)
                            .build(bodyPart))
                    .retrieve()
                    .bodyToFlux(ExerciseDTO.class)
                    .collectList()
                    .onErrorResume(WebClientResponseException.class, this::handleApiError)
                    .block();
        } catch (Exception e) {
            throw ExerciseException.apiError(e.getMessage());
        }
    }

    /**
     * Search by equipment
     */
    public List<ExerciseDTO> searchByEquipment(String equipment, int limit, int offset) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/exercises/equipment/{equipment}")
                            .queryParam("limit", limit)
                            .queryParam("offset", offset)
                            .build(equipment))
                    .retrieve()
                    .bodyToFlux(ExerciseDTO.class)
                    .collectList()
                    .onErrorResume(WebClientResponseException.class, this::handleApiError)
                    .block();
        } catch (Exception e) {
            throw ExerciseException.apiError(e.getMessage());
        }
    }

    private <T> Mono<T> handleApiError(WebClientResponseException ex) {
        if (ex.getStatusCode().value() == 401 || ex.getStatusCode().value() == 403) {
            throw ExerciseException.invalidApiKey();
        }
        throw ExerciseException.apiError(ex.getMessage());
    }
}
