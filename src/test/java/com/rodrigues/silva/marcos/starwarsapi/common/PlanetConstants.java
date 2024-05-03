package com.rodrigues.silva.marcos.starwarsapi.common;

import com.rodrigues.silva.marcos.starwarsapi.domain.Planet;

public class PlanetConstants {

  public static final Planet PLANET = new Planet("name", "clima", "terreno");
  public static final Planet INVALID_PLANET = new Planet("", "", "");
}
