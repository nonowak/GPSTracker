#include "Arduino.h"
#include <SoftwareSerial.h>

class Sim808 {
  private:
    SoftwareSerial * serial;
    String DEFAULT_RESPONSE = "OK";
    String sendAT(String command, int time);
    bool occurs(String command, String Response);
    void setAPN(String APN);
    void resetGPRS();
    void terminateHTTP();

  public:
    Sim808(SoftwareSerial * ss);
    void begin(uint32_t baud);
    void initGPRS(String APN);
    void initGPS();
    String getGPS();
    void sendJson(String url, String body);
    String getValue(String data, char separator, int index);
    void initHTTP();
};
