#include <ArduinoJson.h>

#define TINY_GSM_MODEM_SIM808
#include <TinyGsmClient.h>

#include <SoftwareSerial.h>
SoftwareSerial SerialAT(2, 3); // RX, TX

const char apn[]  = "darmowy";
const char user[] = "";
const char pass[] = "";

TinyGsm modem(SerialAT);
TinyGsmClient client(modem);

const char server[] = "";
const int  port     = 80;
const char resource[] = "/";

void setup() {
  Serial.begin(115200);
  delay(10);

  SerialAT.begin(115200);
  delay(3000);

  Serial.println("Initialize modem");
  modem.restart();
  Serial.print("Modem " + modem.getModemInfo());

  Serial.println("Unlock sim");
  modem.simUnlock("5655");
}

void loop() {
  Serial.print(F("Waiting for network..."));
  if (!modem.waitForNetwork()) {
    Serial.println(" fail");
    delay(10000);
    return;
  }
  Serial.println(" connected");

  String gps = getGPS();
  Serial.println("GPS raw data:" + gps);

}

String getGPS() {
  modem.enableGPS();
  String gps = modem.getGPSraw();
  modem.disableGPS();
  return gps;
}



