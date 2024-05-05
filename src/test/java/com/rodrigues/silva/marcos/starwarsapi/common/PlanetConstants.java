package com.rodrigues.silva.marcos.starwarsapi.common;

import com.rodrigues.silva.marcos.starwarsapi.domain.Planet;

import java.util.ArrayList;
import java.util.List;

public class PlanetConstants {

  public static final Planet PLANET = new Planet("name", "clima", "terreno");
  public static final Planet INVALID_PLANET = new Planet("", "", "");

  public static final Planet TATOOINE = new Planet(1L, "Tatooine", "arid", "desert");
  public static final Planet ALDERAAN = new Planet(1L, "Alderaan", "temperate", "grasslands, mountains");
  public static final Planet YAVINIV = new Planet(1L, "Yavin IV", "temperate, tropical", "jungle, rainforest");

  public static List<Planet> PLANETS = new ArrayList<>() {
    {
      add(TATOOINE);
      add(ALDERAAN);
      add(YAVINIV);
    }
  };
}
