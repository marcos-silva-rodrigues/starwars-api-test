package com.rodrigues.silva.marcos.starwarsapi.domain;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanetService {

  private PlanetRepository planetRepository;

  public PlanetService(PlanetRepository planetRepository) {
    this.planetRepository = planetRepository;
  }

  public Planet create(Planet planet) {
    return planetRepository.save(planet);
  }

  public Planet getById(Long id) {
    return planetRepository.findById(id)
            .orElseThrow(() -> new PlanetNotFoundException("id: " + id));
  }

  public Planet getByName(String name) {
    return planetRepository.findByName(name)
            .orElseThrow(() -> new PlanetNotFoundException("name: " + name));
  }

  public List<Planet> list(String terrain, String climate) {
    Example<Planet> query = QueryBuilder.makeExample(new Planet(climate, terrain));
    return planetRepository.findAll(query);
  }
}
