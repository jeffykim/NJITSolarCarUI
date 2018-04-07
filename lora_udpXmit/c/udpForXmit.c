#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <netdb.h>
#include <sys/types.h> 
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

#include "udpForXmit.h"


// UDP listener components
int sockfd; /* socket */
struct sockaddr_in serveraddr; /* server's addr */
struct sockaddr_in clientaddr; /* client addr */
struct hostent *hostp; /* client host info */
char *hostaddrp; /* dotted decimal host addr string */
int optval; /* flag value for setsockopt */
socklen_t clientlen; /* byte size of client's address */




int initUdpServer() {
	// create socket
	sockfd = socket(AF_INET, SOCK_DGRAM, 0);
	if (sockfd < 0) {
		printf("ERROR opening socket");
		return 1;
	}
	
	// copied from example; should let us exit must faster after force stop
	optval = 1;
	setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, (const void *)&optval , sizeof(int));
	
	// Build server address
	bzero((char *) &serveraddr, sizeof(serveraddr));
	serveraddr.sin_family = AF_INET;
	serveraddr.sin_addr.s_addr = htonl(INADDR_ANY);
	serveraddr.sin_port = htons((unsigned short)UDP_PORT);
	
	// Bind server to port
	if (bind(sockfd, (struct sockaddr *) &serveraddr, sizeof(serveraddr)) < 0) {
		printf("ERROR on binding");
		return 1;
	}
	
	// set len
	clientlen = sizeof(clientaddr);
	
	return 0;
}





void printUdpEcho(unsigned char *buf, int n) {
	// get host address
//	printf("client address: %d\n", clientaddr.sin_addr.s_addr);
	hostp = gethostbyaddr((const char *)&clientaddr.sin_addr.s_addr, sizeof(clientaddr.sin_addr.s_addr), AF_INET);
	if (hostp == NULL)
		printf("ERROR on gethostbyaddr\n");
	hostaddrp = inet_ntoa(clientaddr.sin_addr);
	if (hostaddrp == NULL)
		printf("ERROR on inet_ntoa\n");
//	printf("server received datagram from %s (%s)\n", hostp->h_name, hostaddrp);
//	printf("server received %d/%d bytes: %s\n", strlen(buf), n, buf);

	// echo data back to client
	n = sendto(sockfd, buf, n, 0, (struct sockaddr *) &clientaddr, clientlen);
	if (n < 0) 
		printf("ERROR in sendto\n");
}



int recvData(unsigned char *buf, int bufSize) {
	int numRecv = recvfrom(sockfd, buf, bufSize, 0, (struct sockaddr *) &clientaddr, &clientlen);
//	printf("client addr=%d len=%d\n", clientaddr.sin_addr.s_addr, clientlen);
	return numRecv;
}
	
	
	
	