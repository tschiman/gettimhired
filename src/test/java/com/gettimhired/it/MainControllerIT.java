package com.gettimhired.it;

import com.gettimhired.controller.MainController;
import com.gettimhired.model.dto.CandidateDTO;
import com.gettimhired.model.mongo.User;
import com.gettimhired.service.CandidateService;
import com.gettimhired.service.EducationService;
import com.gettimhired.service.JobService;
import com.gettimhired.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MainController.class)
class MainControllerIT {
    @MockBean
    private UserService userService;

    @MockBean
    private CandidateService candidateService;

    @MockBean
    private EducationService educationService;

    @MockBean
    private JobService jobService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new MainController(userService, candidateService, educationService, jobService)).build();
    }

    @Test
    void testIndex() throws Exception {
        when(candidateService.findAllCandidates()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("candidates"));

        verify(candidateService, times(1)).findAllCandidates();
    }

    @Test
    void testIndexWithCandidateId() throws Exception {
        String candidateId = "1";
        CandidateDTO candidate = new CandidateDTO("candidateId",null,null,null,null, "LinkedIn", "Github");
        when(candidateService.findCandidateById(candidateId)).thenReturn(Optional.of(candidate));
        when(educationService.findAllEducationsByCandidateId(candidateId)).thenReturn(Collections.emptyList());
        when(jobService.findAllJobsByCandidateId(candidateId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/").param("candidateId", candidateId))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("hasCandidate"))
                .andExpect(model().attributeExists("candidate"))
                .andExpect(model().attributeExists("educations"))
                .andExpect(model().attributeExists("jobs"));

        verify(candidateService, times(1)).findCandidateById(candidateId);
        verify(educationService, times(1)).findAllEducationsByCandidateId(candidateId);
        verify(jobService, times(1)).findAllJobsByCandidateId(candidateId);
    }

    @Test
    void testCredentialsGet() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("credentials"));

        // No service call expected for this endpoint
    }

    @Test
    void testCreateCredentials() throws Exception {
        User user = new User("id", "password");
        when(userService.createUser()).thenReturn(user);

        mockMvc.perform(post("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("credentials"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("password"));

        verify(userService, times(1)).createUser();
    }
}