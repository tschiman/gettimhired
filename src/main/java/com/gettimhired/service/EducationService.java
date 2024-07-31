package com.gettimhired.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gettimhired.model.dto.EducationDTO;
import com.gettimhired.model.dto.update.EducationUpdateDTO;
import com.gettimhired.repository.EducationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class EducationService {

    private final String username;
    private final String password;
    Logger log = LoggerFactory.getLogger(EducationService.class);
    private final EducationRepository educationRepository;
    private final String educationServiceHost;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public EducationService(
            EducationRepository educationRepository,
            @Value("${resumesite.educationservice.host}") String educationServiceHost,
            RestTemplate restTemplate,
            @Value("${resumesite.userservice.username}") String username,
            @Value("${resumesite.userservice.password}") String password,
            ObjectMapper objectMapper) {
        this.educationRepository = educationRepository;
        this.educationServiceHost = educationServiceHost;
        this.restTemplate = restTemplate;
        this.username = username;
        this.password = password;
        this.objectMapper = objectMapper;
    }

    public List<EducationDTO> findAllEducationsForUserAndCandidateId(String userId, String candidateId) {
        Map<String, Object> variables = Map.of("userId", userId, "candidateId", candidateId);
        String query = """
                query ($candidateId: String!, $userId: String!) {
                  getEducations(candidateId: $candidateId, userId: $userId) {
                    id
                    userId
                    candidateId
                    name
                    startDate
                    endDate
                    graduated
                    areaOfStudy
                    educationLevel
                  }
                }
                """;
        Map<String, Object> body = Map.of("query", query, "variables", variables);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username, password);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(
                    educationServiceHost + "/graphql",
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );
        } catch (Exception e) {
            log.error("Failed Get All Educations candidateId={} userId={}", candidateId, userId, e);
            return Collections.emptyList();
        }

        if (responseEntity.getBody() != null && responseEntity.getBody().containsKey("errors")) {
            log.error("Error obtaining educations from GQL endpoint candidateId={} userId={}", candidateId, userId);
            return Collections.emptyList();
        }

        List<EducationDTO> educations = ((ArrayList<LinkedHashMap>) ((LinkedHashMap) responseEntity.getBody().get("data")).get("getEducations")).stream()
                .map(map -> {
                    try {
                        return objectMapper.readValue(objectMapper.writeValueAsString(map), EducationDTO.class);
                    } catch (JsonProcessingException e) {
                        log.error("Error", e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        return educations;
    }

    public Optional<EducationDTO> findEducationByIdAndUserId(String id, String userId) {
        Map<String, Object> variables = Map.of("userId", userId, "id", id);
        String query = """
                query ($id: ID!, $userId: String!) {
                  getEducationById(id: $id, userId: $userId) {
                    id
                    userId
                    candidateId
                    name
                    startDate
                    endDate
                    graduated
                    areaOfStudy
                    educationLevel
                  }
                }
                """;
        Map<String, Object> body = Map.of("query", query, "variables", variables);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username, password);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(
                    educationServiceHost + "/graphql",
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );
        } catch (Exception e) {
            log.error("Failed Get education by id id={} userId={}", id, userId, e);
            return Optional.empty();
        }

        if (responseEntity.getBody() != null && responseEntity.getBody().containsKey("errors")) {
            log.error("Error obtaining educations from GQL endpoint id={} userId={}", id, userId);
            return Optional.empty();
        }

        var education = (LinkedHashMap) ((LinkedHashMap) ((LinkedHashMap) responseEntity.getBody()).get("data")).get("getEducationById");
        EducationDTO educationDto = null;
        try {
            educationDto = objectMapper.readValue(objectMapper.writeValueAsString(education), EducationDTO.class);
        } catch (JsonProcessingException e) {
            log.error("Error", e);
        }
        return Optional.ofNullable(educationDto);
    }

    public Optional<EducationDTO> createEducation(String userId, String candidateId, EducationDTO educationDTO) {
        Map<String, Object> variables = null;
        try {
            variables = Map.of("education", objectMapper.readValue(objectMapper.writeValueAsString(educationDTO), Map.class) , "userId", userId);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
        String query = """
                mutation ($education: EducationInput, $userId: String!) {
                  createEducation(education: $education, userId: $userId) {
                    id
                    userId
                    candidateId
                    name
                    startDate
                    endDate
                    graduated
                    areaOfStudy
                    educationLevel
                  }
                }
                """;
        Map<String, Object> body = Map.of("query", query, "variables", variables);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username, password);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(
                    educationServiceHost + "/graphql",
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );
        } catch (Exception e) {
            log.error("Failed create education by id userId={}", userId, e);
            return Optional.empty();
        }

        if (responseEntity.getBody() != null && responseEntity.getBody().containsKey("errors")) {
            log.error("Error create educations from GQL endpoint userId={}", userId);
            return Optional.empty();
        }

        var education = (LinkedHashMap) ((LinkedHashMap) ((LinkedHashMap) responseEntity.getBody()).get("data")).get("createEducation");
        EducationDTO educationDto = null;
        try {
            educationDto = objectMapper.readValue(objectMapper.writeValueAsString(education), EducationDTO.class);
        } catch (JsonProcessingException e) {
            log.error("Error", e);
        }
        return Optional.ofNullable(educationDto);
    }

    public Optional<EducationDTO> updateEducation(String id, String userId, String candidateId, EducationUpdateDTO educationUpdateDTO) {
        Map<String, Object> variables = null;
        try {
            variables = Map.of(
                    "education", objectMapper.readValue(objectMapper.writeValueAsString(educationUpdateDTO), Map.class),
                    "userId", userId,
                    "id", id,
                    "candidateId", candidateId);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
        String query = """
                mutation ($education: EducationInput, $userId: String!, $id: String!, $candidateId: String!) {
                  updateEducation(education: $education, userId: $userId, id: $id, candidateId: $candidateId) {
                    id
                    userId
                    candidateId
                    name
                    startDate
                    endDate
                    graduated
                    areaOfStudy
                    educationLevel
                  }
                }
                """;
        Map<String, Object> body = Map.of("query", query, "variables", variables);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username, password);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(
                    educationServiceHost + "/graphql",
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );
        } catch (Exception e) {
            log.error("Failed create education by id userId={}", userId, e);
            return Optional.empty();
        }

        if (responseEntity.getBody() != null && responseEntity.getBody().containsKey("errors")) {
            log.error("Error create educations from GQL endpoint userId={}", userId);
            return Optional.empty();
        }

        var education = (LinkedHashMap) ((LinkedHashMap) ((LinkedHashMap) responseEntity.getBody()).get("data")).get("updateEducation");
        EducationDTO educationDto = null;
        try {
            educationDto = objectMapper.readValue(objectMapper.writeValueAsString(education), EducationDTO.class);
        } catch (JsonProcessingException e) {
            log.error("Error", e);
        }
        return Optional.ofNullable(educationDto);
    }

    public boolean deleteEducation(String id, String userId) {
        try {
            educationRepository.deleteByIdAndUserId(id, userId);
            return true;
        } catch (Exception e) {
            log.error("deleteEducation userId={} id={}", userId, id, e);
            return false;
        }
    }

    public List<EducationDTO> findAllEducationsByCandidateId(String candidateId) {
        return educationRepository.findAllByCandidateId(candidateId)
                .stream().sorted((e1, e2) -> {
                    if (e1.endDate() == null && e2.endDate() == null) {
                        return 0;
                    }
                    if (e1.endDate() == null) {
                        return -1;
                    }
                    if (e2.endDate() == null) {
                        return 1;
                    }
                    return e2.endDate().compareTo(e1.endDate());
                }).toList();
    }

    public List<EducationDTO> migrateEducations() {
        return educationRepository.findAll().stream()
                .map(EducationDTO::new)
                .toList();
    }
}
