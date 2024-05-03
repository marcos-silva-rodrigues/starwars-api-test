package com.rodrigues.silva.marcos.starwarsapi.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.rodrigues.silva.marcos.starwarsapi.common.PlanetConstants.INVALID_PLANET;
import static com.rodrigues.silva.marcos.starwarsapi.common.PlanetConstants.PLANET;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest(classes = PlanetService.class)
public class PlanetServiceTest {

  @InjectMocks
  private PlanetService planetService;

  @Mock
  private PlanetRepository planetRepository;

  @Test
  public void createPlanet_WithValidData_ReturnsPlanet() {
    when(planetRepository.save(PLANET))
            .thenReturn(PLANET);

    // system under test
    Planet sut = planetService.create(PLANET);

    assertEquals(PLANET, sut);
  }

  @Test
  public void createPlanet_WithInvalidData_ThrowsException() {
    when(planetRepository.save(PLANET))
            .thenThrow(RuntimeException.class);

    assertThrows(RuntimeException.class, () -> planetService.create(INVALID_PLANET));
  }
}
