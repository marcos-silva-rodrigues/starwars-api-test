package com.rodrigues.silva.marcos.starwarsapi.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends CrudRepository<Planet, Long > {
}
