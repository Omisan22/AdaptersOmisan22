package edu.unc.comp301.adapter;

import java.util.List;

public interface UNCBuildingApi {

  Building getBuilding(String name);

  List<String> getAllBuildingNames();

  void getDirections(String startBuilding, String endBuilding);
}
