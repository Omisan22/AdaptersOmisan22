package edu.unc.comp301.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UNCBuildingApiImpl implements UNCBuildingApi {
  private final Map<String, Building> buildings = new HashMap<>();

  // Buildings are stored under a lowercase key so lookups ignore capitalization.
  private String key(String name) {
    return name.trim().toLowerCase();
  }

  public void add(String name, String description) {
    if (name == null) {
      return;
    }
    buildings.put(key(name), new BuildingImpl(name, description));
  }

  @Override
  public Building getBuilding(String name) {
    if (name == null) {
      return null;
    }
    return buildings.get(key(name));
  }

  @Override
  public List<String> getAllBuildingNames() {
    List<String> names = new ArrayList<>();
    for (Building building : buildings.values()) {
      names.add(building.getName());
    }
    return names;
  }

  @Override
  public void getDirections(String startBuilding, String endBuilding) {
    // Intentionally empty: the adapter delegates this to OpenRouteService.
  }
}
