package com.rodrigues.silva.marcos.starwarsapi.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static com.rodrigues.silva.marcos.starwarsapi.common.PlanetConstants.INVALID_PLANET;
import static com.rodrigues.silva.marcos.starwarsapi.common.PlanetConstants.PLANET;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PlanetRepositoryTest {

  @Autowired
  private PlanetRepository planetRepository;

  @Autowired
  private TestEntityManager testEntityManager;

  @AfterEach
  public void afterEach() {
    PLANET.setId(null);
  }

  @Test
  public void createPlanet_WithValidData_ReturnsPlanet() {
    Planet planet = planetRepository.save(PLANET);
    Planet sut = testEntityManager.find(Planet.class, planet.getId());

    assertNotNull(sut);
    assertEquals(sut.getClimate(), planet.getClimate());
    assertEquals(sut.getTerrain(), planet.getTerrain());
    assertEquals(sut.getName(), planet.getName());
  }

  @Test
  public void createPlanet_WithInvalidData_ThrowsException() {
    Planet emptyPlanet = new Planet();

    assertThrows(RuntimeException.class, () -> {
      planetRepository.save(emptyPlanet);
    });

    assertThrows(RuntimeException.class, () -> {
      planetRepository.save(INVALID_PLANET);
    });

  }

  @Test
  public void createPlanet_WithExistingName_ThrowsException() {
    Planet planet = testEntityManager.persistFlushFind(PLANET);
    testEntityManager.detach(planet);

    planet.setId(null);

    assertThrows(RuntimeException.class, () -> {
      planetRepository.save(planet);
    });
  }

  @Test
  public void getPlanet_ByExistingId_ReturnsPlanet() {
    Planet planet = testEntityManager.persistFlushFind(PLANET);

    Optional<Planet> sut = planetRepository.findById(planet.getId());

    assertTrue(sut.isPresent());
    assertEquals(sut.get(), planet);
  }

  @Test
  public void getPlanet_ByUnexistingId_ReturnsPlanet() {
    Optional<Planet> sut = planetRepository.findById(1L);

    assertTrue(sut.isEmpty());
  }
}
