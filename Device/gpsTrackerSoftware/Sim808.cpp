#include "Sim808.h"

String Sim808::sendAT(String command, int time)
{
  delay(time);
  while (serial->available() > 0) serial->read();
  if (command.length() > 0) {
    serial->println(command);
  };
  String response = serial->readString();
  response.trim();
  return response;
}

void Sim808::setAPN(String APN) {
  String response = sendAT("AT+CSTT=\"" + APN + "\",\"\",\"\"", 3000);
  int restartCounter = 0;
  while (!occurs(DEFAULT_RESPONSE, response)) {
    Serial.print(F("Setting APN..."));
    Serial.println("ERROR: " + response);
    response = sendAT("AT+CSTT=\"" + APN + "\",\"\",\"\"", 3000);
    if (restartCounter < 5)
      restartCounter += 1;
    else sendAT("AT+CFUN=1,1", 1000);
  };
  Serial.println("Setting APN... OK");
}
bool Sim808::occurs(String command, String response) {
  if (response.indexOf(command) != -1)
    return true;
  else return false;
}

Sim808::Sim808(SoftwareSerial * ss) {
  serial = ss;
}

void Sim808::begin(uint32_t baud) {
  serial->begin(baud);
  delay(10);
  String response = sendAT("AT", 200);
  if (occurs(DEFAULT_RESPONSE, response)) {
    Serial.println("Serial READY");
  }
  else begin(baud);
}

void Sim808::initModem() {
  String response = sendAT("AT+CFUN=1", 1000);
  while (!occurs(DEFAULT_RESPONSE, response)) {
    Serial.print(F("Initializing modem..."));
    Serial.println("ERROR: " + response);
    response = sendAT("AT+CFUN=1", 1000);
  };
  Serial.println("Initializing modem...OK");
  checkModemStatus();
}

void Sim808::checkModemStatus() {
  String response = sendAT("AT+CPIN?", 1000);
  while (!occurs("READY", response)) {
    Serial.print(F("Checking status..."));
    Serial.println("ERROR: " + response);
    response = sendAT("AT+CPIN?", 1000);
  };
  Serial.println("Checking status...READY");
}

void Sim808::initGPRS(String APN) {
  setAPN(APN);
  String response = sendAT("AT+CIICR", 1000);
//  while (!occurs(DEFAULT_RESPONSE, response)) {
//    Serial.print(F("Initializing GPRS..."));
//    Serial.println("ERROR: " + response);
//    response = sendAT("AT+CIICR", 1000);
//  };
  String ipAddress = getLocalIP();
  Serial.println("Initializing GPRS... .OK IP: " + ipAddress);
}

String Sim808::getLocalIP() {
  String response = sendAT("AT+CIFSR", 1000);
  while (!occurs(".", response)) {
    Serial.print(F("Getting IP..."));
    Serial.println("ERROR: " + response);
    response = sendAT("AT+CIFSR", 1000);
  };
  Serial.println("Getting IP...OK");
  return response;
}

