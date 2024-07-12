package com.gettimhired.controller;

import com.gettimhired.model.mongo.User;
import com.gettimhired.service.CandidateService;
import com.gettimhired.service.EducationService;
import com.gettimhired.service.JobService;
import com.gettimhired.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MainControllerTest {

    private MainController mainController;
    private UserService userService;
    private Model model;
    private CandidateService candidateService;
    private EducationService educationService;
    private JobService jobService;

    @BeforeEach
    public void init() {
        userService = mock(UserService.class);
        model = mock(Model.class);
        candidateService = mock(CandidateService.class);
        educationService = mock(EducationService.class);
        jobService = mock(JobService.class);
        mainController = new MainController(userService, candidateService, educationService, jobService);
    }

    @Test
    public void testThatRootRouteReturnsTheIndexPage() {
        when(model.addAttribute(Mockito.anyString(), Mockito.anyList())).thenReturn(model);

        assertEquals("index", mainController.index(model));

        verify(model, times(1)).addAttribute(Mockito.anyString(), Mockito.anyList());
    }

    @Test
    public void testThatRootRouteWithCandidateReturnsTheIndex() {
        when(model.addAttribute(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(model);
        when(model.addAttribute(Mockito.anyString(), Mockito.any())).thenReturn(model);
        when(model.addAttribute(Mockito.anyString(), Mockito.anyList())).thenReturn(model);
        when(model.addAttribute(Mockito.anyString(), Mockito.anyList())).thenReturn(model);

        assertEquals("index", mainController.index("BARK", model));

        Mockito.verify(model, times(4)).addAttribute(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testThatAPIRouteReturnsTheAPIPage() {
        assertEquals("signups", mainController.signup(model));
    }

    @Test
    public void testThatAPIRouteReturnsAPI() {
        User user = new User("BARK_USER", "BARK_PASSWORD", "email", "password");

        when(userService.createUser()).thenReturn(user);

        when(model.addAttribute(anyString(), any())).thenReturn(model);

//        assertEquals("credentials", mainController.createCredentials(model));

        Mockito.verify(model, times(2)).addAttribute(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testThatPostmanRouteWorks() {
        assertEquals("postmans", mainController.postman());
    }

}