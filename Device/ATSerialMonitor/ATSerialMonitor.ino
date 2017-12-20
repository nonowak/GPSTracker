#include <SoftwareSerial.h>

SoftwareSerial mySerial(2, 3);

void setup()
{
  mySerial.begin(9600);                
  Serial.begin(9600);       
  Serial.println("halo");             
}

void loop()
{
  if (mySerial.available())
    Serial.write(mySerial.read());
  if (Serial.available())
    mySerial.write(Serial.read());  
}
