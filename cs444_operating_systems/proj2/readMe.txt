Giancarlos Marte

	My raid encoding starts with getting the command line arguments and setting the file name variable.
After that my code opens the file given from the command line and gets the size of it. It also stores the
entire file content into a large array using the file size. Afterwards the part names are created and
placed in an array for easier access. After that the variables for storing the hamming code bits, the
byte part buffer size, the encoded part files and the part file size are declared and initialized.
Next is the loop that goes through the file given from the command line argument. Inside the loop,
the first byte is gotten and from that the high nibble is retrieved. The hamming code bits for the high
nibble is then calculated and placed into the corresponding byte part buffers. The same thing is done
for the low nibble that is retrieved next. If the byte buffer part size is 0, then the bits are placed
into the corresponding large buffers for the part files. The part file size is increased and the
byte part buffers are reset. The loop goes on until all the bytes from the file are encoded.
Lastly, all the part files are opened and the large part file buffers are written onto the files.
	My diar decoding starts similar to the raid, but also sets the byte size of the file from the
command line. After that the part file names are created and stored in an array. Next the output
file is made and the part size is gotten. After that all of the part files are opened to prepare for
reading them. The variables used for decoding are made, which include things like hamming bits,
part file byte buffers, parity checking, shift counters for bitwise operations, etc. Next the
output file is opened to prepare to be written to. The first byte from each of the part files is
gotten before entering the loop. Inside the loop if four bytes have been decoded, variables get
reset and the next byte from each of the part files is gotten. Next the part byte buffers get the
current byte being looked at, the hamming code bits are gotten using masking and bitwise operations.
Then the decoding is done using parity checking and error correction. Afterwards the data bits are
stored into the byte buffer. If the buffer is full (decided by the shift used for placing bits in buff)
then variables are reset, the byte count is increased and the decoded byte in the buff is written
to the output file. The loop continues until the amount of bytes decoded is the same as
the total bytes in the file.
	Being able to store the entire file content into an array in raid.c was a feature because it
made getting the bytes easier. I did not intend for it to be a feature, but after finishing the code
I realized it made everything more understandable for me than trying to read the file byte by byte
and doing the hamming calculations at the same time. I tried to do this in diar.c by putting all
the part file contents into individual arrays, but this kept giving me a segmentation fault when
decoding large files. It took me a while to realize that I was using too much memory and that the
source of the segmentation fault was when trying to create 7 really large arrays. Another feature of
my code is the way I did my bitwise operations. I made sure to shift the bits in a way that would make
them easier to store into byte buffers using the or operation.
	The way I got the endianness right in my code was mostly by splitting the bits up in their own
byte buffers. I also used shift operations to keep the bits in the correct places. By doing this I was
able to do parity computations like parity checking and parity correction in diar.c. Overall, splitting
the bits made things much easier conceptually as well.
	The biggest issue I faced was the segmentation fault I kept getting for large files in diar. As
mentioned earlier the cause was the large arrays I kept trying to make for the part file content. However,
it took me a long time and a lot of reimplementations of my code to figure this out. I also went to office
hours to discuss this issue with the professor. Using the extra extensions, I was able to put in the time to
redo my diar and have it just read the part files byte by byte. I was also able to reorganize both raid and
diar by using methods instead of doing everything in main. Overall, the project was a lot more tedious than
I expected it to be, but I learned a lot about hamming code/decoding and also the way C works.
	The first line of the Makefile is setting a variable for the program that compiles C programs (cc).
The second line is another variable that represents the extra flags of the C compiler that give warnings to
things like unused variables, uninitialized variables, etc. The 4th line is a rule with the prerequisites being
raid and diar, which represent the file names. The 6th line is another rule that uses the % wildcard to match
a ".c" file in the prerequisites. The 7th line is the command that compiles the C program. It puts together
the different variables and adds extra flags/symbols that compiles raid and diar.c. The 9th line is the
clean target name. The 10th line is the command to remove .out, .part? (where ? could be anything) and .2 files.
	I used getopt.h in a very simple way to read input from the command line. I had it find the f
flag in raid and the f and s flag in diar. After that my code uses a switch statement to check for the 
flags in case statements. Inside that my code would then set the appropriate variables to the arguments
that come after the flag (file name and byte size). After setting the variable in the case statement it
would break out of the switch statement and then my code would continue. The default case would catch
any flags that aren't "f" or/and "f" (depending on diar or raid) and then return an error message and
the number 1. Using getopt.h seemed very intimidating at first, but after looking at the piazza post
about it by one of my classmates, it made things much more understandable.
