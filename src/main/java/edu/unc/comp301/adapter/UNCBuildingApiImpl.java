package edu.unc.comp301.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UNCBuildingApiImpl implements UNCBuildingApi {
  private final Map<String, Building> buildings = new HashMap<>();

  public void add(String name, String description) {
    buildings.put(name, new BuildingImpl(name, description));
  }

  @Override
  public Building getBuilding(String name) {
    return buildings.get(name);
  }

  @Override
  public List<String> getAllBuildingNames() {
    return new ArrayList<>(buildings.keySet());
  }

  @Override
  public void getDirections(String startBuilding, String endBuilding) {
    // Intentionally empty: the adapter delegates this to OpenRouteService.
  }
}
