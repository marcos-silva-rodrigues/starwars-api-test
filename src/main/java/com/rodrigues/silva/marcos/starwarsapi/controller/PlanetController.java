package com.rodrigues.silva.marcos.starwarsapi.controller;

import com.rodrigues.silva.marcos.starwarsapi.domain.Planet;
import com.rodrigues.silva.marcos.starwarsapi.domain.PlanetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planets")
public class PlanetController {

  private PlanetService planetService;

  public PlanetController(PlanetService planetService) {
    this.planetService = planetService;
  }

  @PostMapping
  public ResponseEntity<Planet> create(@RequestBody Planet planet) {
    var planetCreated = planetService.create(planet);
    return ResponseEntity.status(HttpStatus.CREATED).body(planetCreated);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Planet> getById(@PathVariable Long id) {
    var planet = planetService.getById(id);
    return ResponseEntity.status(HttpStatus.OK).body(planet);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<Planet> getByName(@PathVariable String name) {
    Planet planet = planetService.getByName(name);
    return ResponseEntity.status(HttpStatus.OK).body(planet);
  }

  @GetMapping
  public ResponseEntity<List<Planet>> list(
          @RequestParam(required = false) String terrain,
          @RequestParam(required = false) String climate) {
    List<Planet> planets = planetService.list(climate, terrain);

    return ResponseEntity.ok(planets);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable Long id) {
    planetService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
