package com.rodrigues.silva.marcos.starwarsapi.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static com.rodrigues.silva.marcos.starwarsapi.common.PlanetConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

  @Test
  public void getPlanet_ByExistingName_ReturnsPlanet() throws Exception {
    Planet planet = testEntityManager.persistFlushFind(PLANET);

    Optional<Planet> sut = planetRepository.findByName(planet.getName());

    assertTrue(sut.isPresent());
    assertEquals(sut.get(), planet);
  }

  @Test
  public void getPlanet_ByUnexistingName_ReturnsPlanet() throws Exception {
    Optional<Planet> sut = planetRepository.findByName("tatooine");

    assertTrue(sut.isEmpty());

  }

  @Test
  @Sql(scripts = "/import_planets.sql")
  public void listPlanets_ReturnsFilteredPlanets() throws Exception {
    Example<Planet> exampleWithFilter = QueryBuilder.makeExample(new Planet(TATOOINE.getClimate(), TATOOINE.getTerrain()));
    Example<Planet> exampleWithoutFilter = QueryBuilder.makeExample(new Planet());

    List<Planet> sutWithFilters = planetRepository.findAll(exampleWithFilter);
    List<Planet> sutWithoutFilters = planetRepository.findAll(exampleWithoutFilter);

    assertEquals(TATOOINE, sutWithFilters.get(0));
    assertEquals(1, sutWithFilters.size());

    assertEquals(3, sutWithoutFilters.size());
  }

  @Test
  public void listPlanets_ReturnsNoPlanets() throws Exception {
    Example<Planet> exampleWithoutFilter = QueryBuilder.makeExample(new Planet());
    var sut = planetRepository.findAll(exampleWithoutFilter);

    assertTrue(sut.isEmpty());
  }

  @Test
  public void removePlanet_WithExitingId_ReturnsNoContent() throws Exception {
    Planet planet = testEntityManager.persistFlushFind(PLANET);

    planetRepository.deleteById(planet.getId());
    Planet sut = testEntityManager.find(Planet.class, planet.getId());

    assertNull(sut);

  }

}
