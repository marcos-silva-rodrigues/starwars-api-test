package com.rodrigues.silva.marcos.starwarsapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodrigues.silva.marcos.starwarsapi.domain.Planet;
import com.rodrigues.silva.marcos.starwarsapi.domain.PlanetService;
import org.junit.jupiter.api.Test;

import static com.rodrigues.silva.marcos.starwarsapi.common.PlanetConstants.INVALID_PLANET;
import static com.rodrigues.silva.marcos.starwarsapi.common.PlanetConstants.PLANET;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.internal.hamcrest.HamcrestArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PlanetService planetService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void createPlanet_WithValidData_ReturnsCreated() throws Exception {
    when(planetService.create(any(Planet.class)))
            .thenReturn(PLANET);

    mockMvc.perform(post("/planets")
          .content(objectMapper.writeValueAsString(PLANET))
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$").value(PLANET));
  }

  @Test
  public void createPlanet_WithInvalidData_ReturnsBadRequest() throws Exception {
    Planet planet = new Planet();

    mockMvc.perform(post("/planets")
                    .content(objectMapper.writeValueAsString(planet))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity());

    mockMvc.perform(post("/planets")
                    .content(objectMapper.writeValueAsString(INVALID_PLANET))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity());
  }
}
