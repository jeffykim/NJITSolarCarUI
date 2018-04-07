#include <RH_RF95.h>
#define UDP_BUF_SIZE RH_RF95_MAX_MESSAGE_LEN
#define UDP_PORT 7888

int initUdpServer();
void printUdpEcho(unsigned char *buf, int n);
int recvData(unsigned char *buf, int bufSize);