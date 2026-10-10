package edu.unc.comp301.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class WalkingDirectionsService {
  private final String baseURL = "https://api.openrouteservice.org/v2/directions/foot-walking";
  private final String apiKey;
  private final OkHttpClient client = new OkHttpClient();

  public WalkingDirectionsService(String apiKey) {
    this.apiKey = apiKey;
  }

  // ORS expects each coordinate as "longitude,latitude" (longitude first!)
  public String createURL(double startLat, double startLon, double endLat, double endLon) {
    return HttpUrl.parse(baseURL)
        .newBuilder()
        .addQueryParameter("api_key", apiKey)
        .addQueryParameter("start", startLon + "," + startLat)
        .addQueryParameter("end", endLon + "," + endLat)
        .addQueryParameter("steps", "true")
        .build()
        .toString();
  }

  public Request buildRequest(String url) {
    return new Request.Builder().url(url).get().build();
  }

  public String sendRequest(Request request) throws IOException {
    try (Response resp = client.newCall(request).execute()) {
      if (!resp.isSuccessful() || resp.body() == null) {
        throw new IOException("Request failed: " + resp);
      }
      return resp.body().string();
    }
  }

  public DirectionsResult parseDirections(String json) {
    JSONObject obj = new JSONObject(json);
    JSONArray segments =
        obj.getJSONArray("features")
            .getJSONObject(0)
            .getJSONObject("properties")
            .getJSONArray("segments");

    List<Step> steps = new ArrayList<>();
    double totalDistance = 0;
    double totalTime = 0;

    for (int i = 0; i < segments.length(); i++) {
      JSONArray segmentSteps = segments.getJSONObject(i).getJSONArray("steps");
      for (int j = 0; j < segmentSteps.length(); j++) {
        JSONObject step = segmentSteps.getJSONObject(j);
        String instruction = step.getString("instruction");
        double distance = step.getDouble("distance"); // meters
        double duration = step.getDouble("duration"); // seconds

        steps.add(new Step(instruction, distance, duration));
        totalDistance += distance;
        totalTime += duration;
      }
    }

    return new DirectionsResult(steps, totalDistance, totalTime);
  }

  public void getWalkingDirections(double startLat, double startLon, double endLat, double endLon) {
    try {
      String url = createURL(startLat, startLon, endLat, endLon);
      Request request = buildRequest(url);
      String json = sendRequest(request);
      DirectionsResult result = parseDirections(json);

      List<Step> steps = result.getSteps();
      for (int i = 0; i < steps.size(); i++) {
        Step step = steps.get(i);
        System.out.println(
            (i + 1)
                + ". "
                + step.getInstruction()
                + " - ("
                + String.format("%.1f", step.getDistance())
                + " meters, "
                + String.format("%.1f", step.getDuration())
                + " seconds)");
      }
      System.out.println(
          "Total distance: " + String.format("%.1f", result.getTotalDistance()) + " m");
      System.out.println("Total time: " + String.format("%.1f", result.getTotalDuration()) + " s");
    } catch (Exception e) {
      System.out.println("Could not get walking directions: " + e.getMessage());
    }
  }
}
