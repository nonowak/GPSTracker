#include "Sim808.h"

#include <SoftwareSerial.h>

SoftwareSerial ss(2, 3);
Sim808 sim(&ss);
String APN = "darmowy";
String DEVICE_SERIAL = "h1rQ-BWZ9";
String SERVER_ADDRESS = "http://7822f73b.eu.ngrok.io";

void setup() {
  Serial.begin(9600);
  delay(100);
  sim.begin(9600);
  delay(100);
  sim.initGPS();
  delay(100);
  sim.initGPRS(APN);
}

void loop() {
  delay(500);
  gpsToJSON();
}

void gpsToJSON() {
  String gps = sim.getGPS();
  Serial.println(gps);
  String rawTime = sim.getValue(gps, ',', 2);
  String measurementTime = "\"measurementTime\":\"" +  rawTime.substring(0, rawTime.length() - 4) + "\"";
  String latitude = "\"latitude\":" + sim.getValue(gps, ',', 3);
  String longitude = "\"longitude\":" + sim.getValue(gps, ',', 4);
  String json = "{" + measurementTime +  "," + latitude + "," + longitude + "}";
  Serial.println("5");
  String url = SERVER_ADDRESS + "/gps/measurements/" + DEVICE_SERIAL;
  Serial.println(json);
  sim.sendJson(url, json);
}

