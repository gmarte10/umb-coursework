Giancarlos Marte
	
This project was a bit confusing and difficult at first because 
of the starter code and the much long pdf instructions. The starter code
threw me off a bit because the code was very different from my style. I
had to really go through it to understand what it was doing, especially
when it came to the parsecmd function and the template for using fork()
and execv(). Eventually, I was able to figure it out by experimenting with
the code and adding my own stuff. The envDemo.c was very helpful when trying
to do 3.1.
	This first problem was confusing as I didn't know where I was
supposed to get the environmental variables from. Fortunately, the demo made it
very clear. Then next problem, 3.2, took a bit of trial and error. I was stuck
for a while on why my code did not recognize "cd " (with the space). Eventually,
I realized that the space was removed when parsing for the command line arguments.
To recognize it, I just had to check if the next argument size after cd was 0.
3.3 was relatively straightforward. I just made a the array of pointers that
was asked for and stored all the command line arguments in it. The trickiest
part was removing the oldest command and shifting the rest down in the array after
500 commands. The last problem that I did was 4.1. This one had the most new 
things with the system calls, generating a child process and running an executable.
My code will only work sometimes for this part. Usually I would have to run
./bsh two times at least to get the absolute paths to run correctly. I don't know
why this is happening exactly, but I suspect I am not using execv() correctly.
I knew that the first argument had to be the absolute path name, but the second
argument for the method confused me. I didn't know what to put so I just
used cmdArg like it showed in the template. The only thing I did for 4.2 was to
recognize ">" and "<" symbols. I had my code print out what should happen if
those symbols were recognized. I did not implement the rest of the code as I
could not figure out what to do. I did not do anything for 4.3 and 4.4 because
I was mainly focused on making sure the required tasks worked properly and I got
stuck on 4.1 and 4.2.
	The special feature of my code is mostly how I made separate functions for
code that would take up a lot of space in main and cause confusion. I did this
after first doing most of the stuff in main. I wanted to make things more
modular and easier to edit/understand so I split them up. The one thing that
could be improved from doing this was that I had some repetitive functions
that could have been generalized. I could have made more functions for the
initializations of pointers as that took up a lot of redundant space. A feature
that I wanted to be consistent with was making copies of each of the pointers
and returning the modified versions of them in the functions. I was able to do
this at the beginning when setting up the variables and values, but eventually,
I just went back to modifying a lot of the pointers directly. Fully implementing
this feature would have made my code less prone to errors.
	A brief rundown of my code was that I first started off with getting the
environment variables and their values/paths. I used the envDemo as a guide.
I first made a copy of the envp array from the main parameter and then I used
that copy to split the variables and values into separate concurrent arrays
(Concurrent as in same indexes). Next I initialized the pointer I would used for
collecting the commands for the history command. After that I went through the
loop given in the started code. The main thing I changed was checking if the 500
commands were reached for history, refreshing the pointer if so. Then I added
the command line into the history array and increased the pointer
index. Next the command line arguments were parsed. I added a small block of code
for recognizing the I/O redirection. After that I implemented the env, setenv,
unsetenv, cd, history and unsupported commands. Lastly, the given clean up code
was left.