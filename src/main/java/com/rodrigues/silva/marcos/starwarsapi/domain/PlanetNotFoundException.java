package com.rodrigues.silva.marcos.starwarsapi.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PlanetNotFoundException extends RuntimeException {

  public PlanetNotFoundException(String paramMessage) {
    super("Planet not found with " + paramMessage);
  }
}
