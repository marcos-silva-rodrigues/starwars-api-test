package com.rodrigues.silva.marcos.starwarsapi.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.rodrigues.silva.marcos.starwarsapi.common.PlanetConstants.INVALID_PLANET;
import static com.rodrigues.silva.marcos.starwarsapi.common.PlanetConstants.PLANET;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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

  @Test
  public void findPlanet_ByExistingId_ReturnsPlanet() {
    when(planetRepository.findById(anyLong()))
            .thenReturn(Optional.of(PLANET));

    Planet sut = planetService.getById(1L);

    assertEquals(PLANET, sut);
  }

  @Test
  public void findPlanet_ByUnexistingId_ThrowsCustomException() {
    when(planetRepository.findById(anyLong()))
            .thenReturn(Optional.empty());

    assertThrows(PlanetNotFoundException.class,
            () -> planetService.getById(1L));
  }

  @Test
  public void findPlanet_ByExistingName_ReturnsPlanet() {
    when(planetRepository.findByName(anyString()))
            .thenReturn(Optional.of(PLANET));

    var sut =  planetService.getByName("Tatooine");

    assertEquals(PLANET, sut);
  }

  @Test
  public void findPlanet_ByUnexistingName_ReturnsPlanet() {
    when(planetRepository.findByName(anyString()))
            .thenReturn(Optional.empty());

    assertThrows(PlanetNotFoundException.class, () -> planetService.getByName("Tatooine"));
  }
}
