#include "Sim808.h"

#include <SoftwareSerial.h>

SoftwareSerial ss(2, 3);
Sim808 sim(&ss);
String APN = "darmowy";

void setup() {
  Serial.begin(115200);
  delay(100);
  sim.begin(115200);
  delay(100);
  sim.initModem();
  delay(100);
  sim.initGPRS(APN);
}

void loop() {

}

