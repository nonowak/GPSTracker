#include <ArduinoJson.h>

#define TINY_GSM_MODEM_SIM808
#include <TinyGsmClient.h>

#include <SoftwareSerial.h>
SoftwareSerial SerialAT(2, 3); // RX, TX

const char apn[]  = "darmowy";
const char user[] = "";
const char pass[] = "";

#ifdef DUMP_AT_COMMANDS
#include <StreamDebugger.h>
StreamDebugger debugger(SerialAT, Serial);
TinyGsm modem(debugger);
#else
TinyGsm modem(SerialAT);
#endif
TinyGsmClient client(modem);

RegStatus statusGPRS = REG_UNREGISTERED;

const char server[] = "";
const int  port     = 80;
const char resource[] = "/";

void setup() {
  Serial.begin(115200);
  delay(10);

  SerialAT.begin(115200);
  delay(3000);

  initModem();
  delay(2000);

  Serial.println("Enable GPS");
  modem.enableGPS();
  delay(2000);
  initNetwork();
  delay(2000);
  initGPRS();
  delay(2000);
}

void loop() {
  checkConnection();
  Serial.println("GPS raw data:" + getGPS());
  delay(2000);
}

void initModem() {
  SimStatus status = SIM_ERROR;
  String modemInfo = "";
  do {
    Serial.print(F("Initialize modem..."));
    modem.init();
    status = modem.getSimStatus();
    switch (status)
    {
      case SIM_ERROR:
        Serial.println(" ERROR Trying Again");
        modem.restart();
        break;
      case SIM_LOCKED:
        Serial.println(" Trying to unlock sim");
        modem.simUnlock("5655");
        modem.restart();
        break;
    }
    modemInfo = modem.getModemInfo();
    if (modemInfo == "") {
      status = SIM_ERROR;
    }
  } while (status != SIM_READY);
  delay(2000);
  Serial.println(" OK Modem: " + modemInfo);
}

void initNetwork() {
  Serial.println(F("Enable Network..."));
  modem.sendAT(GF("+CREG=1"));
  checkConnection();
}

void initGPRS() {
  bool status = false;
  do {
    checkConnection();
    Serial.println(F("Initialize GPRS..."));
    status = modem.gprsConnect(apn, user, pass);
    Serial.println(" fail");
    delay(10000);
  } while (!status);
  Serial.println("OK ");
}

void checkConnection() {
  statusGPRS = modem.getRegistrationStatus();
  do {
    delay(2000);
    Serial.println(F("Checking Network..."));
    statusGPRS = modem.getRegistrationStatus();
    switch (statusGPRS)
    {
      case REG_UNREGISTERED:
        Serial.println(" REG_UNREGISTERED");
        break;
      case REG_SEARCHING:
        Serial.println(" REG_SEARCHING");
        break;
      case REG_DENIED:
        Serial.println(" REG_DENIED");
        break;
      case REG_UNKNOWN:
        Serial.println(" REG_UNKNOWN");
        break;
    }
  } while ((statusGPRS != REG_OK_HOME && statusGPRS == REG_OK_ROAMING) ||
           (statusGPRS == REG_OK_HOME && statusGPRS != REG_OK_ROAMING));
  Serial.println(" OK");
}

String getGPS() {
  //  modem.enableGPS();
  String gps = modem.getGPSraw();
  //  modem.disableGPS();
  return gps;
}
