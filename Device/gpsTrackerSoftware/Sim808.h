#include "Arduino.h"
#include <SoftwareSerial.h>

class Sim808 {
  private:
    SoftwareSerial * serial;
    String DEFAULT_RESPONSE = "OK";
    String sendAT(String command, int time);
    bool occurs(String command, String Response);
    void setGPSMode();

  public:
    Sim808(SoftwareSerial * ss);
    void begin(uint32_t baud);
    void initModem();
    void checkModemStatus();
    void initGPRS(String APN);
    String getLocalIP();
    void initGPS();
    String getGPS();
};
