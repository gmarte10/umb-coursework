#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <stdbool.h>


// Create a part name from command line file
char* getPartName(char* file_name, int part) {
	char* part_name = (char*)malloc(110 * sizeof(char*));
	char* ext;
	switch(part) {
		case 0:
			ext = ".part0";
			strcpy(part_name, file_name);
			strcat(part_name, ext);
			break;
		case 1:
			ext = ".part1";
			strcpy(part_name, file_name);
			strcat(part_name, ext);
			break;
		case 2:
			ext = ".part2";
			strcpy(part_name, file_name);
			strcat(part_name, ext);
			break;
		case 3:
			ext = ".part3";
			strcpy(part_name, file_name);
			strcat(part_name, ext);
			break;
		case 4:
			ext = ".part4";
			strcpy(part_name, file_name);
			strcat(part_name, ext);
			break;
		case 5:
			ext = ".part5";
			strcpy(part_name, file_name);
			strcat(part_name, ext);
			break;
		case 6:
			ext = ".part6";
			strcpy(part_name, file_name);
			strcat(part_name, ext);
			break;
		default:
			fprintf(stderr, "nonexistent part number\n");
			return NULL;
	}
	return part_name;
}

/* Takes any part file name and returns it's size. Since all part files are the same size, the output
	applies to all part files.
*/
int getPartSize(char* part_name) {
	// Open the file
	FILE *fp;
   fp = fopen(part_name, "rb");
   if (!fp) {
      fprintf(stderr, "failed to open file for size\n");
      return 1;
   }
   // Get file size
   fseek(fp, 0L, SEEK_END);
   int part_size = ftell(fp);
   rewind(fp);
   fclose(fp);
   return part_size;
}

// Used to open all the part files
void openFiles(FILE** fpointers, char** part_names) {
	FILE *temp;
	for (int i = 0; i < 7; i++) {
		temp = fopen(part_names[i], "rb");
   	if (!temp) {
      	fprintf(stderr, "failed to openfile in openFiles()\n");
      }
      fpointers[i] = temp;
	}
}

// Get the next byte of each part file
void nextByte(FILE** fpointers, unsigned char* part_bytes) {
	FILE* fp;
	unsigned char* byte = (unsigned char*)malloc(sizeof(unsigned char));
	for (int i = 0; i < 7; i++) {
		fp = fpointers[i];
		fread(byte, 1, 1, fp);
		unsigned char b = *byte;
		part_bytes[i] = b;
	} 
}

int main(int argc, char **argv )  {
	// Get and check command line arguments
	char* file_name = (char*)malloc(100 * sizeof(char*));
	int file_size;
	char opt;
	while ((opt = getopt(argc, argv, "f:s:")) != -1) {
		switch(opt) {
			case 'f':
				file_name = optarg;
				break;
			case 's':
				file_size = atoi(optarg);
				break;
			default:
				fprintf(stderr, "wrong flag\n");
				return 1;
		}
	}

	// Get the part file names
	char* part0_name = getPartName(file_name, 0);
	char* part1_name = getPartName(file_name, 1);
	char* part2_name = getPartName(file_name, 2);
	char* part3_name = getPartName(file_name, 3);
	char* part4_name = getPartName(file_name, 4);
	char* part5_name = getPartName(file_name, 5);
	char* part6_name = getPartName(file_name, 6);
	char** part_names = (char**)malloc(7 * sizeof(char*));
	part_names[0] = part0_name;
	part_names[1] = part1_name;
	part_names[2] = part2_name;
	part_names[3] = part3_name;
	part_names[4] = part4_name;
	part_names[5] = part5_name;
	part_names[6] = part6_name;

	// Create output file name
   char* ext = ".2";
   char* name = (char*)malloc(110 * sizeof(char*));
   strcpy(name, file_name);
   strcat(name, ext);

	// Get the size of all part files
   int part_size = getPartSize(part0_name);

   // Open all the part files
   FILE** fpointers = (FILE**)malloc(7 * sizeof(FILE*));
   openFiles(fpointers, part_names);
   unsigned char* part_bytes = (unsigned char*)malloc(7 * sizeof(unsigned char));

   // Buffers for part bytes and hamming bits
   unsigned char byte0, byte1, byte2, byte3, byte4, byte5, byte6;
   unsigned char p1, p2, d1, p4, d2, d3, d4;

   // Parity checking
   int error = 0;
   unsigned char x1, x2, x3;

   // Used for bitwise operations
   int buff_shift = 7;
   int shift = 7;

   // Buffer used to store decoded byte
   unsigned char buff = 0;

   // Number of bytes made from 1 part file byte
   int byte_count = 0;

   // Number of decoded bytes
   int index = 0;

   // Open the output file
   FILE *fpn;
   fpn = fopen(name, "wb");

   // Get the first byte from each part file
   nextByte(fpointers, part_bytes);

   while (index < file_size) {
   	// If four bytes have been decoded
   	if (byte_count == 4) {
   		shift = 7;
   		byte_count = 0;
   		nextByte(fpointers, part_bytes);
   	}

   	byte0 = part_bytes[0];
   	byte1 = part_bytes[1];
   	byte2 = part_bytes[2];
   	byte3 = part_bytes[3];
   	byte4 = part_bytes[4];
   	byte5 = part_bytes[5];
   	byte6 = part_bytes[6];

   	p1 = (byte0 >> shift) & 1;
		p2 = (byte1 >> shift) & 1;
		d1 = (byte2 >> shift) & 1;
		p4 = (byte3 >> shift) & 1;
		d2 = (byte4 >> shift) & 1;
		d3 = (byte5 >> shift) & 1;
		d4 = (byte6 >> shift) & 1;
		shift--;

		// Parity checking
   	x1 = p1 ^ d1 ^ d2 ^ d4;
		x2 = p2 ^ d1 ^ d3 ^ d4;
		x3 = p4 ^ d2 ^ d3 ^ d4;

		error = 0;
		if (x1 != 0) {
			error = error + 1;
		}
		if (x2 != 0) {
		   error = error + 2;
		}
		if (x3 != 0) {
		   error = error + 4;
		}

		// Error correction
		switch(error) {
			case 1:
				p1 = p1 ^ 1;
				break;
			case 2:
				p2 = p2 ^ 1;
				break;
			case 3:
				d1 = d1 ^ 1;
				break;
			case 4:
				p4 = p4 ^ 1;
				break;
			case 5:
				d2 = d2 ^ 1;
				break;
			case 6:
				d3 = d3 ^ 1;
				break;
			case 7:
				d4 = d4 ^ 1;
				break;
		}

		// Store data bits
		d1 = d1 << buff_shift;
		buff_shift--;
		d2 = d2 << buff_shift;
		buff_shift--;
		d3 = d3 << buff_shift;
		buff_shift--;
		d4 = d4 << buff_shift;
		buff_shift--;
		buff = buff | d1 | d2 | d3 | d4;

		// High and low nibbles decoded and stored in buff
		if (buff_shift < 0) {
			buff_shift = 7;
			byte_count++;
			unsigned char* x = &buff;
		   fwrite(x, 1, 1, fpn);
			buff = 0;
			index++;
		}
   }
	return 0;
}