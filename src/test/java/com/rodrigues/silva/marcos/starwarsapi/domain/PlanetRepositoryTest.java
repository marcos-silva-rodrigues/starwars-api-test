package com.rodrigues.silva.marcos.starwarsapi.domain;

import static com.rodrigues.silva.marcos.starwarsapi.common.PlanetConstants.INVALID_PLANET;
import static com.rodrigues.silva.marcos.starwarsapi.common.PlanetConstants.PLANET;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class PlanetRepositoryTest {

  @Autowired
  private PlanetRepository planetRepository;

  @Autowired
  private TestEntityManager testEntityManager;

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
}
