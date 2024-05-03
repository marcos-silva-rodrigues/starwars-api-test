package com.rodrigues.silva.marcos.starwarsapi.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanetRepository extends CrudRepository<Planet, Long > {

  Optional<Planet> findByName(String name);



}
