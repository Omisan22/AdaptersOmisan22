package edu.unc.comp301.adapter;

public class BuildingImpl implements Building {
  private final String name;
  private final String description;
  // TODO: replace with your UNC email
  private final NominatimGeocoder geocoder =
      new NominatimGeocoder("UNCGeocoder (omisan22@unc.edu)");
  private Location loc;

  public BuildingImpl(String name, String description) {
    this.name = name;
    this.description = description;

    String address = name + ", Chapel Hill, North Carolina";
    try {
      this.loc = geocoder.geocode(address);
    } catch (Exception e) {
      System.out.println("Could not geocode " + name + ": " + e.getMessage());
    }
    if (this.loc == null) {
      System.out.println("No coordinates found for " + name);
    }
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public double getLatitude() {
    return loc == null ? 0.0 : loc.getLatitude();
  }

  @Override
  public double getLongitude() {
    return loc == null ? 0.0 : loc.getLongitude();
  }
}
