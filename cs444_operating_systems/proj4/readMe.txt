This project was a lot more difficult than the other ones.
It contained a lot of C syntax and functions that I had never
used before. An example was the functions using pointer variables
like: void *threadXYZ(void *p) {}. The given code snippets from
the pdf instructions and the example code files were also a bit
new and difficult to understand at first. Overall, I underestimated
how complex this project was and the amount of time I needed to
understand it before being able to implement anything. The code
I did manage to create were things I already knew. First, the
getopt.h was fairly simple as I had used it in project 2. I
started by first creating the lambda, mu, number of customers and 
number of servers variables with their default values. From there
I used getopt.h by making a loop getting each command line argument
based off of the correct flags. I used a switch case statement
to set the variables to the correct values. After that I checked to
see if the number of servers was greater than 1 as I was not
supporting multiple of them in this implementation. Afterwards,
I checked if the arrival rate was greater than the service rate times
the number of servers as the arrival rate had to be less than it.
Next I created the three threads for customers, server and the queue
length. I used the threadXYZ function to create them, but I was unsure
if this was right. There was warning when doing the pthread_create
when compiling, which I was able to resolve by doing &i instead of just
i, based off of internet research. After this, I added the functions from
the code snippets. This included a function for sleeping, drawing from an
exponential distribution, the onlineAvgStd and the basic code for the 
queue. I also added a printStats function. The main issue I had after
doing this was figuring out how to implement the queue. The mutex was
a big thing throwing me off as I have never used one in my code
before. I ended up leaving the code as was given from the example
because I did not have enough understanding to correctly implement
the loops and the mutex locks. I also did not have enough time to
do it. Overall, the main issue I had with this project was simply
understanding what I had to do and the details of the
implementation.

