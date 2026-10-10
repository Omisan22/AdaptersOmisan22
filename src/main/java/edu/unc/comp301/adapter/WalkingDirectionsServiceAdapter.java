package edu.unc.comp301.adapter;

import java.util.List;

public class WalkingDirectionsServiceAdapter implements UNCBuildingApi {
  private final WalkingDirectionsService wds; // the adaptee
  private final UNCBuildingApi api; // the base UNC api we delegate to

  public WalkingDirectionsServiceAdapter(WalkingDirectionsService wds, UNCBuildingApi api) {
    this.wds = wds;
    this.api = api;
  }

  @Override
  public Building getBuilding(String name) {
    return api.getBuilding(name);
  }

  @Override
  public List<String> getAllBuildingNames() {
    return api.getAllBuildingNames();
  }

  @Override
  public void getDirections(String startBuilding, String endBuilding) {
    Building start = api.getBuilding(startBuilding);
    Building end = api.getBuilding(endBuilding);
    if (start == null || end == null) {
      System.out.println("Both buildings must be added to the UNCBuildingApi first.");
      return;
    }
    // Parameter conversion: building names -> latitude/longitude
    wds.getWalkingDirections(
        start.getLatitude(), start.getLongitude(), end.getLatitude(), end.getLongitude());
  }
}
