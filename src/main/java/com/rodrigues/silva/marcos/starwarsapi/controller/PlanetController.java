package com.rodrigues.silva.marcos.starwarsapi.controller;

import com.rodrigues.silva.marcos.starwarsapi.domain.Planet;
import com.rodrigues.silva.marcos.starwarsapi.domain.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
