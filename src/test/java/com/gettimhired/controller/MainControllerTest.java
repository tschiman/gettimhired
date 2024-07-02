package com.gettimhired.controller;

import com.gettimhired.model.mongo.User;
import com.gettimhired.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainControllerTest {

    private MainController mainController;
    private UserService userService;
    private Model model;

    @BeforeEach
    public void init() {
        userService = mock(UserService.class);
        model = mock(Model.class);
        mainController = new MainController(userService);
    }

    @Test
    public void testThatRootRouteReturnsTheIndexPage() {
        assertEquals("index", mainController.index());
    }

    @Test
    public void testThatRootRouteWithCandidateReturnsTheIndex() {
        when(model.addAttribute(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(model);

        assertEquals("index", mainController.index("BARK", model));

        Mockito.verify(model, times(1)).addAttribute(Mockito.anyString(), Mockito.anyBoolean());
    }

    @Test
    public void testThatAPIRouteReturnsTheAPIPage() {
        assertEquals("api", mainController.api());
    }

    @Test
    public void testThatAPIRouteReturnsAPI() {
        User user = new User("BARK_USER", "BARK_PASSWORD");

        when(userService.createUser()).thenReturn(user);

        when(model.addAttribute("user", "BARK_USER")).thenReturn(model);
        when(model.addAttribute("password", "BARK_PASSWORD")).thenReturn(model);

        assertEquals("api", mainController.createCredentials(model));

        assertEquals("index", mainController.index("BARK", model));
        Mockito.verify(model, times(2)).addAttribute(Mockito.anyString(), Mockito.anyString());
    }

}