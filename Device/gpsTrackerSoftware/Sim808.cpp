#include "Sim808.h"

String Sim808::sendAT(String command, int time)
{
  delay(time);
  while (serial->available() > 0) serial->read();
  if (command.length() > 0) {
    serial->println(command);
  };
  delay(time);
  String response = serial->readString();
  response.trim();
  return response;
}

void Sim808::resetGPRS() {
  sendAT("AT+SAPBR=0,1", 100);
}

bool Sim808::occurs(String command, String response) {
  if (response.indexOf(command) != -1)
    return true;
  else return false;
}

void Sim808::initHTTP() {
  Serial.println("Initializing HTTP");
  String response = sendAT("AT+HTTPINIT", 100);
  bool done = false;
  int resetCounter = 0;
  while (!done) {
    if (!occurs(DEFAULT_RESPONSE, response))
      done = false;
    else(done = true);
    response = sendAT("AT+HTTPPARA=\"CID\",1", 100);
    if (!occurs(DEFAULT_RESPONSE, response))
      done = false;
    else(done = true);
    resetCounter++;
    if (resetCounter == 3)
      resetGPRS();
  }
  Serial.println("HTTP READY");
}

void Sim808::terminateHTTP() {
  Serial.println("Terminating HTTP");
  String response = sendAT("AT+HTTPTERM", 200);
  if (!occurs(DEFAULT_RESPONSE, response))
    terminateHTTP();
  Serial.println("HTTP Terminated");
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

void Sim808::initGPRS(String APN) {
  bool finish = false;
  int resetCounter = 0;
  String response;
  while (!finish) {
    response = sendAT("AT+CREG?", 100);
    Serial.println("Registration Status..." + response);
    response = sendAT("AT+SAPBR=3,1,\"Contype\",\"GPRS\"", 100);
    Serial.println("Configure Bearer..." + response);
    response = sendAT("AT+SAPBR=3,1,\"APN\",\"" + APN + "\"", 100);
    Serial.println("Configure Bearer APN..." + response);
    response = sendAT("AT+SAPBR=1,1", 100);
    if (occurs(DEFAULT_RESPONSE, response))
      finish = true;
    resetCounter++;
    if (resetCounter == 3)
      resetGPRS();
  }
  response = sendAT("AT+SAPBR=2,1", 100);
  Serial.println("Configure finished..." + response);
}

void Sim808::initGPS() {
  String response = sendAT("AT+CGNSPWR=1", 100);
  while (!occurs(DEFAULT_RESPONSE, response)) {
    Serial.print(F("Initializing GPS..."));
    Serial.println("ERROR: " + response);
    response = sendAT("AT+CGNSPWR=1", 100);
  };
  Serial.println("Initializing GPS... OK");
}

String Sim808::getGPS() {
  String response = sendAT("AT+CGNSINF", 100);
  while (!occurs(",", response)) {
    Serial.print(F("Getting GPS..."));
    Serial.println("ERROR: " + response);
    response = sendAT("AT+CNPSINF", 100);
  };
  response = response.substring(10, response.length());
  return response;
}

void Sim808::sendJson(String url, String body) {
  initHTTP();
  String  response = sendAT("AT+HTTPPARA=\"URL\",\"http://ec39c92f.eu.ngrok.io/gps/measurements/h1rQ-BWZ9\"", 300);
  delay(400);
  Serial.println(response);
  response = sendAT("AT+HTTPPARA=\"CONTENT\",\"application/json\"", 100);
  Serial.println("Setting content type: " + response);
  const char *bodyChar = body.c_str();
  response = sendAT("AT+HTTPDATA=" + String(strlen(bodyChar)) + ",5000", 100);
  delay(400);
  Serial.println(response);
  delay(100);
  Serial.println("Setting JSON " + body);
  response = sendAT(body, 100);
  delay(2000);
  response = sendAT("AT+HTTPACTION=1", 400);
  Serial.println("Action: " + response);
  response = sendAT("AT+HTTPREAD", 200);
  Serial.println("Sending datta: " + response);
  terminateHTTP();
  //  resetGPRS();
}

String Sim808::getValue(String data, char separator, int index) {
  int found = 0;
  int strIndex[] = { 0, -1 };
  int maxIndex = data.length() - 1;

  for (int i = 0; i <= maxIndex && found <= index; i++) {
    if (data.charAt(i) == separator || i == maxIndex) {
      found++;
      strIndex[0] = strIndex[1] + 1;
      strIndex[1] = (i == maxIndex) ? i + 1 : i;
    }
  }
  return found > index ? data.substring(strIndex[0], strIndex[1]) : "";
}
