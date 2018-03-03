// demo: CAN-BUS Shield, receive data with check mode
// send data coming to fast, such as less than 10ms, you can use this way
// loovee, 2014-6-13


#include <SPI.h>
#include "mcp_can.h"

#include "NineAxesMotion.h"
#include <Wire.h>

#define BUF_SIZE 500
#define FLOAT_BUF_SIZE 8

void printParam(float v, char* vName, char* buf);
void appendToBuf(char* str, char* buf);
void appendFloat(float f, char* buf);
void fmtChecksumMsg(char* buf, long targetChecksum);
void fmtChecksumMsg(char* buf, long targetChecksum);

NineAxesMotion imu;
char outBuf[BUF_SIZE];
long lastWrite = 0;


// the cs pin of the version after v1.1 is default to D9
// v0.9b and v1.0 is default D10
const int SPI_CS_PIN = 9;

MCP_CAN CAN(SPI_CS_PIN);                                    // Set CS pin

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
  Serial.println("init in progress");

  //set up IMU
  imu.initSensor();
  imu.setOperationMode(OPERATION_MODE_NDOF);
  imu.setUpdateMode(AUTO);
}


void loop()
{
  Serial.print("a\n");
  unsigned char len = 0;
  unsigned char canBuf[8];

  // Variables and shit
  float gaccX;
  float gaccY;
  float gaccZ;

  float laccX;
  float laccY;
  float laccZ;

  float gyroX;
  float gyroY;
  float gyroZ;

  float packCurrent;
  float packVolts;
  float packSoc;
  float relayState;
  float packDcl;
  float packTemp;

  float delayPot;

    //initialize the buffer to all 0's
  for (int i = 0; i < BUF_SIZE; i++) {
    outBuf[i] = 0;
  }

  if (CAN_MSGAVAIL == CAN.checkReceive())           // check if data coming
  {
    CAN.readMsgBuf(&len, canBuf);    // read data,  len: data length, buf: data buf
    unsigned int canId = CAN.getCanId();
    switch(canId) {
      case 0x6B0:
        packCurrent = (float)canBuf[0];
        packVolts = (float)canBuf[2];
        packSoc = (float)canBuf[4];
        relayState = (float)canBuf[5];
        break;
      case 0x6B1:
        packDcl = (float)canBuf[0];
        packTemp = (float)(canBuf[5] + 256*canBuf[4]);
        break;
    }
  }
  
  // Print it out
  if((millis() - lastWrite) > 250)
  {
    lastWrite = millis();

    // delayPot = ((float)analogRead(A0)) *1.953125f;

//    printParam(delayPot, "delayPot", outBuf);
    printParam(packCurrent, "packCurrent", outBuf);
    printParam(packVolts, "packVolts", outBuf);
    printParam(packSoc, "packSoc", outBuf);
    printParam(relayState, "relayState", outBuf);
    printParam(packDcl, "packDcl", outBuf);
    printParam(packTemp, "temp", outBuf);

    // Read the data in
    imu.readGravAccel(gaccX, gaccY, gaccZ);
    imu.readLinearAccel(laccX, laccY, laccZ);
    imu.readGyro(gyroX, gyroY, gyroZ);
    
    printParam(gaccX, "gaccX", outBuf);
    printParam(gaccY, "gaccY", outBuf);
    printParam(gaccZ, "gaccZ", outBuf);
  
    printParam(laccX, "laccX", outBuf);
    printParam(laccY, "laccY", outBuf);
    printParam(laccZ, "laccZ", outBuf);
  
    printParam(gyroX, "gyroX", outBuf);
    printParam(gyroY, "gyroY", outBuf);
    printParam(gyroZ, "gyroZ", outBuf);
  
    // append checksum and terminator
//    fmtChecksumMsg(outBuf, 0xDEADBEEFL - 10L);
  
    Serial.println(outBuf);
  }
}




void printParam(float v, char* vName, char* buf) {
  appendToBuf(vName, buf);
  appendToBuf("=", buf);
  appendFloat(v, buf);
  appendToBuf("&", buf);
}



void appendToBuf(char* str, char* buf) {
  int len = strlen(buf);
  for (int i = 0; i < strlen(str); i++) {
    buf[len + i] = str[i];
  }
}


void appendFloat(float f, char* buf) {
  char floatBuf[FLOAT_BUF_SIZE];
  for (int i = 0; i < FLOAT_BUF_SIZE; i++) {
    floatBuf[i] = 0;
  }
  dtostrf(f, FLOAT_BUF_SIZE - 1, 2, floatBuf);
  appendToBuf(floatBuf, buf);
}




void fmtChecksumMsg(char* buf, long targetChecksum) {
  int rawLen = strlen(buf);
  int len = rawLen;
  if (len % 4 != 0) { // pad to a multiple of 4 bytes
    len += 4;
    len -= (len % 4);
  }

  long sum = 0;
  for (int i = 0; i < len / 4; len += 4) { // do a preliminary sum of the existing data. Should overflow naturally
    sum += buf[i];
    sum += buf[i + 1] << 8;
    sum += buf[i + 1] << 16;
    sum += buf[i + 1] << 24;
  }

  long diff = targetChecksum - sum;
  buf[rawLen] = diff & 0xFFL;
  buf[rawLen + 1] = (diff & 0xFF00L) >> 8L;
  buf[rawLen + 2] = (diff & 0xFF0000L) >> 16L;
  buf[rawLen + 3] = (diff & 0xFF000000L) >> 24L;
}

/*********************************************************************************************************
  END FILE
*********************************************************************************************************/
