#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <stdbool.h>

FILE* openFile(char* file_name) {
   FILE* fp;
   fp = fopen(file_name, "r");
   if (!fp) {
      fprintf(stderr, "failed to open file\n");
   }
   return fp;
}

// Returns the file size of a given file pointer with an open file
int getFileSize(FILE* fp) {
   fseek(fp, 0L, SEEK_END);
   int file_size = ftell(fp);
   rewind(fp);
   return file_size;
}

// Returns the file as an array
unsigned char* getFileAsBuffer(FILE* fp, int file_size) {
   unsigned char* file_buffer = (unsigned char*)malloc(file_size * sizeof(unsigned char));
   fread(file_buffer, 1, file_size, fp);
   fclose(fp);
   return file_buffer;
}

// Create the part file names
char** getPartNames(char* file_name) {
   char** part_names = (char**)malloc(7 * sizeof(char*));
   for (int i = 0; i < 7; i++) {
      char* extension = (char*)malloc(20 * sizeof(char));
      char* p = ".part";
      strcpy(extension, p);
      char* num = (char*)malloc(2 * sizeof(char));
      sprintf(num, "%d", i);
      strcat(extension, num);
      char* part = (char*)malloc(110 * sizeof(char));
      strcpy(part, file_name);
      strcat(part, extension);
      part_names[i] = part;
   }
   return part_names;
}

// Open all the part files for writing
FILE** openForWriting(char** part_names) {
   FILE* temp;
   FILE** fpointers = (FILE**)malloc(7 * sizeof(FILE*));
   for (int i = 0; i < 7; i++) {
      temp = fopen(part_names[i], "wb");
      fpointers[i] = temp;
   }
   return fpointers;
}

// Returns an array that sets up all the part file buffers
unsigned char** getFileBuffers(int file_size) {
   unsigned char** temp = (unsigned char**)malloc(7 * sizeof(unsigned char*));
   for (int i = 0; i < 7; i++) {
      temp[i] = (unsigned char*)malloc((file_size * 2) * sizeof(unsigned char));
   }
   return temp;
}

void writePartFiles(unsigned char** file_buffers, FILE** fpointers, int index) {
   for (int i = 0; i < 7; i++) {
      fwrite(file_buffers[i], 1, index, fpointers[i]);
      fclose(fpointers[i]);
   }
}

int main(int argc, char *argv[] )  {
   // Get and check command line arguments
   char* file_name = (char*)malloc(100 * sizeof(char*));
   char opt;
   while ((opt = getopt(argc, argv, "f:")) != -1) {
      switch(opt) {
         case 'f':
            file_name = optarg;
            break;
         default:
            fprintf(stderr, "wrong flag\n");
            return 1;
      }
   }

   // Open file, get it's size and create the part names using it
   FILE* fp;
   fp = openFile(file_name);
   int file_size = getFileSize(fp);
   unsigned char* file_buffer = getFileAsBuffer(fp, file_size);
   char** part_names = getPartNames(file_name);

   // Buffers for storing p1, p2, d1, p4, d2, d3 and d4 bytes
   unsigned char part0, part1, part2, part3, part4, part5, part6;
   part0 = 0;
   part1 = 0;
   part2 = 0;
   part3 = 0;
   part4 = 0;
   part5 = 0;
   part6 = 0;

   // Part buffer size
   int count = 8;

   unsigned char** file_buffers = getFileBuffers(file_size);

   // Used for keeping track of part files size
   int index = 0;

   for (int i = 0; i < file_size; i++) {

      unsigned char byte = file_buffer[i];
      unsigned char nibble_hi = (byte & 0xF0) >> 4;

      // Hamming code for high nibble
      unsigned char p1, p2, d1, p4, d2, d3, d4;
      d4 = (nibble_hi & 1);
      d3 = (nibble_hi >> 1) & 1;
      d2 = (nibble_hi >> 2) & 1;
      d1 = (nibble_hi >> 3) & 1;
      p1 = d1 ^ d2 ^ d4;
      p2 = d1 ^ d3 ^ d4;
      p4 = d2 ^ d3 ^ d4;

      // Place into part buffers
      int shift_hi = count - 1;
      part0 = part0 | (p1 << shift_hi);
      part1 = part1 | (p2 << shift_hi);
      part2 = part2 | (d1 << shift_hi);
      part3 = part3 | (p4 << shift_hi);
      part4 = part4 | (d2 << shift_hi);
      part5 = part5 | (d3 << shift_hi);
      part6 = part6 | (d4 << shift_hi);

      unsigned char nibble_lo = byte & 0x0F;

      // Hamming code for low nibble
      d4 = (nibble_lo & 1);
      d3 = (nibble_lo >> 1) & 1;
      d2 = (nibble_lo >> 2) & 1;
      d1 = (nibble_lo >> 3) & 1;
      p1 = d1 ^ d2 ^ d4;
      p2 = d1 ^ d3 ^ d4;
      p4 = d2 ^ d3 ^ d4;

      // Place into part buffers
      int shift_lo = count - 2;
      part0 = part0 | (p1 << shift_lo);
      part1 = part1 | (p2 << shift_lo);
      part2 = part2 | (d1 << shift_lo);
      part3 = part3 | (p4 << shift_lo);
      part4 = part4 | (d2 << shift_lo);
      part5 = part5 | (d3 << shift_lo);
      part6 = part6 | (d4 << shift_lo);
      count = count - 2;

      // Store into large part buffers if byte part buffer is full
      if (count == 0) {
         file_buffers[0][index] = part0;
         file_buffers[1][index] = part1;
         file_buffers[2][index] = part2;
         file_buffers[3][index] = part3;
         file_buffers[4][index] = part4;
         file_buffers[5][index] = part5;
         file_buffers[6][index] = part6;
         index++;
         count = 8;
         part0 = 0;
         part1 = 0;
         part2 = 0;
         part3 = 0;
         part4 = 0;
         part5 = 0;
         part6 = 0;
      }
   }
   FILE** fpointers;
   fpointers = openForWriting(part_names);
   writePartFiles(file_buffers, fpointers, index);
   return 0;
}