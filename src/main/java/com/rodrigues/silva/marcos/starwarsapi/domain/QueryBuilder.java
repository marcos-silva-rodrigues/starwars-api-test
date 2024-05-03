package com.rodrigues.silva.marcos.starwarsapi.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

public class QueryBuilder {

  public static Example<Planet> makeExample(Planet planet) {
    ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();
    return Example.of(planet, exampleMatcher);
  }
}
