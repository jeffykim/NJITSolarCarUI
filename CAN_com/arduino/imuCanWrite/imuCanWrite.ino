// demo: CAN-BUS Shield, send data
// loovee@seeed.cc

#include <mcp_can.h>
#include <SPI.h>
#include <Servo.h>

#include "NineAxesMotion.h"
#include <Wire.h>

#define CAN_BUF_SIZE 8

NineAxesMotion imu;
const int SPI_CS_PIN = 9;
MCP_CAN CAN(SPI_CS_PIN);                                    // Set CS pin

unsigned char canBuf[CAN_BUF_SIZE];

void fmtSendCanFrame(float x, float y, float z, unsigned char* buf, unsigned int id);

float laccX;
float laccY;
float laccZ;

float gaccX;
float gaccY;
float gaccZ;

float gyroX;
float gyroY;
float gyroZ;

float potVal;

long lastMillis = 0;
Servo mot;

void setup()
{
    Serial.begin(115200);

    while (CAN_OK != CAN.begin(CAN_500KBPS))              // init can bus : baudrate = 500k
    {
        Serial.println("CAN BUS Shield init fail");
        Serial.println(" Init CAN BUS Shield again");
        delay(100);
    }
    Serial.println("CAN BUS Shield init ok!");

    
    I2C.begin();
    imu.initSensor();
    imu.setOperationMode(OPERATION_MODE_NDOF);
    imu.setUpdateMode(AUTO);

    // Motor driver
    mot.attach(45);
    pinMode(31, INPUT);
}


void loop()
{
  if(millis() > lastMillis + 250) {
    lastMillis = millis();
    
    // Read the 9 axis data in
    imu.readGravAccel(gaccX, gaccY, gaccZ);
    imu.readLinearAccel(laccX, laccY, laccZ);
    imu.readGyro(gyroX, gyroY, gyroZ);
  
    potVal = (float)analogRead(A0)/8;
    Serial.println(potVal);
    
    fmtSendCanFrame(gaccX, gaccY, gaccZ, canBuf, 0x100);
    fmtSendCanFrame(laccX, laccY, laccZ, canBuf, 0x101);
    fmtSendCanFrame(gyroX, gyroY, gyroZ, canBuf, 0x102);
    fmtSendCanFrame(potVal, 0, 0, canBuf, 0x103);
  }

  int potRaw = analogRead(A0);
  byte isFwd = digitalRead(31);
  int motVal = 0;
  if(isFwd) {
    motVal = map(potRaw, 0, 1023, 0, 90);
  } else {
    motVal = map(potRaw, 0, 1023, 90, 0);
  }

  mot.write(motVal);
  delay(25);
}




void fmtSendCanFrame(float x, float y, float z, unsigned char* buf, unsigned int id) {
  for(int i=0; i<CAN_BUF_SIZE; i++) {
    canBuf[i] = 0;
  }
  
  int ix = (int)(x*256.0);
  int iy = (int)(y*256.0);
  int iz = (int)(z*256.0);

  buf[0] = ix >> 8;
  buf[1] = ix & 0xFF;
  buf[2] = iy >> 8;
  buf[3] = iy & 0xFF;
  buf[4] = iz >> 8;
  buf[5] = iz & 0xFF;
  
  Serial.print("Sent Frame: ");
  Serial.print(id);
  Serial.print(", ");
  for(int i=0; i<CAN_BUF_SIZE; i++) {
    Serial.print(buf[i], HEX);
    Serial.print(" ");
  }
  Serial.println();
  
  CAN.sendMsgBuf(id, 0, 8, buf);
}















// END FILE
