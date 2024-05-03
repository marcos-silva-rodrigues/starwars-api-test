package com.rodrigues.silva.marcos.starwarsapi.domain;

import org.springframework.stereotype.Service;

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


}
