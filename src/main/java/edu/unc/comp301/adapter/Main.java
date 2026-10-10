package edu.unc.comp301.adapter;

import java.io.InputStream;
import java.util.Properties;

public class Main {
  public static void main(String[] args) {
    System.out.println("Assn05-Adapter");

    // OpenRoutesService API Key
    Properties cfg = new Properties();
    try (InputStream in = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
      cfg.load(in);
    } catch (Exception e) {
      System.out.println(e);
    }
    String apiKey = cfg.getProperty("ors.api.key");

    // 1-2. Create the UNC api and add some buildings
    UNCBuildingApiImpl unc = new UNCBuildingApiImpl();
    unc.add("sitterson hall", "computer science");
    unc.add("hamilton hall", "history and political science");
    unc.add("davis library", "main library");

    // 3-5. Wrap the walking directions service in the adapter and use it
    try {
      WalkingDirectionsService wds = new WalkingDirectionsService(apiKey);
      UNCBuildingApi adapter = new WalkingDirectionsServiceAdapter(wds, unc);
      adapter.getDirections("sitterson hall", "hamilton hall");
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
