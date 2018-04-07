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
#define RF_GATEWAY_ID 4 
#define RF_NODE_ID    5
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
	
	// loop infinitely recieving messages
	while(1) {
		// recieve a paket from the client
		bzero(buf, UDP_BUF_SIZE);
		n = recvData(buf, UDP_BUF_SIZE);
		if (n < 0) // abort if < 0 bytes recieved, means an error occured
			shutdown("ERROR in recvfrom");
		
		// write to the LoRa
		setLoRaLED(LOW);
		
		printf("Sending %02d bytes to node #%d => ", n, RF_GATEWAY_ID );
		printbuffer(buf, n);
		printf("\n" );
		rf95.send(buf, n);
		rf95.waitPacketSent();       

		// Turn LED back on
		setLoRaLED(HIGH);
		
		// echo to client
		printUdpEcho(buf, n);
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
		shutdown("%s bcm2835_init() Failed\n\n");
		return 1;
	}

	printf( "RF95 CS=GPIO%d", RF_CS_PIN);
	#ifdef RF_IRQ_PIN
		printf( ", IRQ=GPIO%d", RF_IRQ_PIN );
		// IRQ Pin input/pull down 
		pinMode(RF_IRQ_PIN, INPUT);
		bcm2835_gpio_set_pud(RF_IRQ_PIN, BCM2835_GPIO_PUD_DOWN);
	#endif
	
	#ifdef RF_LED_PIN
		printf( ", LED=GPIO%d", RF_LED_PIN );
		pinMode(RF_LED_PIN, OUTPUT);
		setLoRaLED(HIGH);
	#endif

	if (!rf95.init()) {
		shutdown("\nLoRA init failed!\n" );
	} else {
		printf( "\nLoRa module seen OK!\r\n");

		#ifdef RF_IRQ_PIN
		// Since we may check IRQ line with bcm_2835 Rising edge detection
		// In case radio already have a packet, IRQ is high and will never
		// go to low so never fire again 
		// Except if we clear IRQ flags and discard one if any by checking
		rf95.available();

		// Now we can enable Rising edge detection
		bcm2835_gpio_ren(RF_IRQ_PIN);
		#endif
		rf95.setTxPower(TX_POWER, false); 
		rf95.setFrequency( RF_FREQUENCY );
		rf95.setThisAddress(RF_NODE_ID);
		rf95.setHeaderFrom(RF_NODE_ID);
		rf95.setHeaderTo(RF_GATEWAY_ID);  

		printf("LoRa init OK. Node addr:%d Gateway; addr:%d; frequency:%3.2fMHz\n", RF_NODE_ID, RF_GATEWAY_ID, RF_FREQUENCY );

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









