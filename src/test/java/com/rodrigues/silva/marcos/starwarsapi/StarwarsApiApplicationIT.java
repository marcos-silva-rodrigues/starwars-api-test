package com.rodrigues.silva.marcos.starwarsapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodrigues.silva.marcos.starwarsapi.domain.Planet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static com.rodrigues.silva.marcos.starwarsapi.common.PlanetConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/remove_planets.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/import_planets.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class StarwarsApiApplicationIT {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void createPlanet_ReturnsCreated() {
		ResponseEntity<Planet> sut = restTemplate.postForEntity("/planets", PLANET, Planet.class);

		assertEquals(HttpStatus.CREATED, sut.getStatusCode());
		assertNotNull(sut.getBody().getId());

		assertEquals(PLANET.getName(), sut.getBody().getName());
		assertEquals(PLANET.getClimate(), sut.getBody().getClimate());
		assertEquals(PLANET.getTerrain(), sut.getBody().getTerrain());
	}

	@Test
	public void getPlanets_ReturnsPlanet() {
		ResponseEntity<Planet> sut = restTemplate.getForEntity("/planets/1", Planet.class);

		assertEquals(HttpStatus.OK, sut.getStatusCode());

		assertEquals(TATOOINE.getId(), sut.getBody().getId());
		assertEquals(TATOOINE.getName(), sut.getBody().getName());
		assertEquals(TATOOINE.getTerrain(), sut.getBody().getTerrain());
		assertEquals(TATOOINE.getClimate(), sut.getBody().getClimate());
	}

	@Test
	public void getPlanetByName_ReturnsPlanet() {
		ResponseEntity<Planet> sut = restTemplate.getForEntity(
						"/planets/name/" + TATOOINE.getName(),
						Planet.class);

		assertEquals(HttpStatus.OK, sut.getStatusCode());

		assertEquals(TATOOINE, sut.getBody());
	}

	@Test
	public void listPlanets_ReturnsAllPlanets() {
		ResponseEntity<Planet[]> sut = restTemplate.getForEntity(
						"/planets",
						Planet[].class);

		assertEquals(HttpStatus.OK, sut.getStatusCode());
		assertEquals(3, sut.getBody().length);
		assertEquals(TATOOINE, sut.getBody()[0]);
	}

	@Test
	public void listPlanets_ByClimate_ReturnsPlanet() {
		ResponseEntity<Planet[]> sut = restTemplate.getForEntity(
						String.format("/planets?climate=%s", ALDERAAN.getClimate()),
						Planet[].class);

		assertEquals(HttpStatus.OK, sut.getStatusCode());
		assertEquals(1, sut.getBody().length);
		assertEquals(ALDERAAN.getName(), sut.getBody()[0].getName());
	}

	@Test
	public void listPlanets_ByTerrain_ReturnsPlanet() {
		ResponseEntity<Planet[]> sut = restTemplate.getForEntity(
						String.format("/planets?terrain=%s", YAVINIV.getTerrain()),
						Planet[].class);

		assertEquals(HttpStatus.OK, sut.getStatusCode());
		assertEquals(1, sut.getBody().length);
		assertEquals(YAVINIV.getName(), sut.getBody()[0].getName());
	}

	@Test
	public void removePlanet_ReturnsNoContent() {
		ResponseEntity<Void> sut = restTemplate.exchange(
						"/planets/1",
						HttpMethod.DELETE,
            null,
						Void.class);

		assertEquals(HttpStatus.NO_CONTENT, sut.getStatusCode());
	}

}
