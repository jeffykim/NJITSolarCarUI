/* 
 * Serves as a communications wrapper for the LoRa module on the 
 * solar car. Will listen for UDP data on a certain port, and 
 * forward it to the lora module. The reciever will then fetch
 * that data and write it to any connected clients
 */
 
#include <bcm2835.h>

#include <RH_RF69.h>
#include <RH_RF95.h>

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include "udpForXmit.h"

#define BOARD_DRAGINO_PIHAT

// Now we include RasPi_Boards.h so this will expose defined 
// constants with CS/IRQ/RESET/on board LED pins definition
#include "/home/pi/Desktop/RadioHead/examples/raspi/RasPiBoards.h"

// Our RFM95 Configuration 
#define RF_FREQUENCY  915.00
#define RF_NODE_ID    4
#define RF_OTHER_NODE_ID    5
#define TX_POWER 23

// LoRa module reference
RH_RF95 rf95(RF_CS_PIN, RF_IRQ_PIN);
unsigned char buf[UDP_BUF_SIZE];
int n; //number bytes received


int initLoRa();
int initUdpServer();
void resetLoRa();
void shutdown(char *msg);
void setLoRaLED(int);



int main(int argc, char **argv) {
	// init stuff
	initLoRa();
	initUdpServer();
	
	// recieve 1 packet to get client address
	printf("Waiting for client to connect... Packet size=%d\n", UDP_BUF_SIZE);
	bzero(buf, UDP_BUF_SIZE);
	recvData(buf, UDP_BUF_SIZE);
	printf("Client connected!\n");
	
	while(1) {
#ifdef RF_IRQ_PIN
		if (bcm2835_gpio_eds(RF_IRQ_PIN)) {
			bcm2835_gpio_set_eds(RF_IRQ_PIN); // reset interrupt
#endif
			if(rf95.available()) {
				// Should be a message for us now
				uint8_t len  = sizeof(buf);
				uint8_t from = rf95.headerFrom();
				uint8_t to   = rf95.headerTo();
				uint8_t id   = rf95.headerId();
				uint8_t flags= rf95.headerFlags();;
				int8_t rssi  = rf95.lastRssi();
				
				bzero(buf, UDP_BUF_SIZE);
				
				if (rf95.recv(buf, &len)) {
					printf("Packet[%02d] #%d => #%d %ddB: ", len, from, to, rssi);
					printbuffer(buf, len);
					printf("\n");
					printUdpEcho(buf, len);
				} else {
					printf("receive failed");
				}
				printf("\n");
			}
#ifdef RF_IRQ_PIN
		}
#endif
	}
}



/// Starts up the LoRa module. This is essentially a copy
/// of the setup code, slightly tweaked to suit our 
/// purposes. Returns nonzero on failure.
int initLoRa() {
	printf("Initializing LoRa...\n");
	static unsigned long last_millis;
	static unsigned long led_blink = 0;
  
	// signal(SIGINT, sig_handler);

	if (!bcm2835_init()) {
		shutdown("bcm2835_init() Failed\n\n");
		return 1;
	}

	printf( "RF95 CS=GPIO%d", RF_CS_PIN);
	#ifdef RF_IRQ_PIN
		printf( ", IRQ=GPIO%d", RF_IRQ_PIN );
		// IRQ Pin input/pull down 
		pinMode(RF_IRQ_PIN, INPUT);
		bcm2835_gpio_set_pud(RF_IRQ_PIN, BCM2835_GPIO_PUD_DOWN);
		bcm2835_gpio_ren(RF_IRQ_PIN);
	#endif
	
	resetLoRa();
	
	#ifdef RF_LED_PIN
		printf( ", LED=GPIO%d", RF_LED_PIN );
		pinMode(RF_LED_PIN, OUTPUT);
		setLoRaLED(HIGH);
	#endif

	if (!rf95.init()) {
		shutdown("\nLoRA init failed!\n" );
	} else {
		printf( "\nLoRa module seen OK!\r\n");

		rf95.setTxPower(TX_POWER, false); 
		rf95.setFrequency( RF_FREQUENCY );
		rf95.setThisAddress(RF_NODE_ID);
		rf95.setHeaderFrom(RF_NODE_ID);
		rf95.setHeaderTo(RF_OTHER_NODE_ID);  
		rf95.setPromiscuous(true);
		rf95.setModeRx();

		printf("LoRa init OK. Node addr:%d; frequency:%3.2fMHz\n", RF_NODE_ID, RF_FREQUENCY );

		last_millis = millis();
		return 0;
	}
}





void resetLoRa() {
	#ifdef RF_RST_PIN
		printf( ", RST=GPIO%d", RF_RST_PIN );
		// Pulse a reset on module
		pinMode(RF_RST_PIN, OUTPUT);
		digitalWrite(RF_RST_PIN, LOW );
		bcm2835_delay(150);
		digitalWrite(RF_RST_PIN, HIGH );
		bcm2835_delay(100);
	#endif
}




void shutdown(char *msg) {
	perror(msg);
	bcm2835_close();
	exit(1);
}




void setLoRaLED(int on) {
	#ifdef RF_LED_PIN
		digitalWrite(RF_LED_PIN, on);
	#endif
}









