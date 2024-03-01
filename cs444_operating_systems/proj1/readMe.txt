Giancarlos Marte

Before starting the project I had to relearn a lot of c because it had been a year since I last used it.
The way I approached this project was essentially through trial and error. I tried to break things down and simplify things as much as possible. 
I would often run pieces of code and test them separately from the main file to make sure things worked. Afterwards I would put them together.
I did this for things like reading the text, making/using the min priority queue, running the huffman algorithm, doing bitwise operations and writing to a file.
My initial concept of the project was that I would read the file, store the characters and frequencies in parallel arrays,
use an array to do the huffman algorithm, store the codes and then print them on the output file, while reading the characters from the file again.
This idea ended up being an oversimplification of what I actually had to do. I ended up changing things as I worked on the project and got stuck on specific parts.
I also went to office hours, which cleared up a lot of the confusion I had for writing the codes onto the output file.
The first thing I changed was using structs instead of parallel arrays. I also used them for the min priority queue.
Structs made things a lot easier for me as they resembled java objects, which I have experience with. It also grouped the data in a more organized and accessible way.
While learning to use structs I also ended up having to relearn pointers in order to actually work with them. 
This also took a lot of trial and error to really understand how pointers work and when to use them.
The next thing that I came across was the huffman algorithm and creating a tree. Using the node struct made things a lot easier than I expected. 
The only thing that was a bit confusing was how I would end up using the final root node that contained the entire tree.
The part that was really frustrating was adding the codes to the nodes. I kept trying to set the code array directly to the node code array.
Eventually, I realized that you can’t actually copy arrays in this way and that you actually need memcpy or strcpy.
The biggest challenge of the project by far was the bitwise operations I had to do in order to print out a byte. 
I had to write down a lot of examples and experiment with what operations to use. 
I also did a lot of research on how to use an integer as a way of storing and manipulating bits.
Eventually I came up with something that seemed to work and gave the output I was looking for.
Unfortunately, I was not able to compare my output with the professor’s output because it kept saying I was denied access when I tried to run the executable.
This was most likely because I was trying to do it after the deadline as I received an extension due to accommodations.
The final program I created did the things that were asked of. It is able to read the command line arguments with the options.
If there are too many arguments, too little or the wrong options, the program will stop and print out the issue. If there are no arguments,
the default input and output file names will be used. After that the input file is read and a min priority queue of nodes are made.
This is then used to do the huffman algorithm and get/store the codes. Finally, the input file is read again and the code for each character is placed in a buffer.
When the buffer reaches 8 or more bits, a byte gets written to the output file in the form of a character.
Lastly, if there are extra bits left at the end in the buffer, those get written as well.
All of this is done using bitwise operations (shift, and, or) and the fwrite function.
