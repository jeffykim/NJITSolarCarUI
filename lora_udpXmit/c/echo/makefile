CC=gcc
CFLAGS=-c -O0 -Wall

all: udpEcho

udpEcho: udpEcho.o
	$(CC) -o udpEcho udpEcho.o
	
clean:
	-del *.o
	-del *.exe