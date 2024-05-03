package com.rodrigues.silva.marcos.starwarsapi.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanetRepository extends CrudRepository<Planet, Long>, QueryByExampleExecutor<Planet> {

  Optional<Planet> findByName(String name);

  <S extends Planet> List<S> findAll(Example<S> example);
}
