/******************************************************************
*
*   file:     tutor.c
*   author:   eb
*   date:     September, 1990
*
*   Driver for a "tutor" program to mimic and extend the MECB ROM TUTOR.
* 
*   built from betty o'neil's previous version
*
*   revisions:
*   Jan '96: changed prompt to PC-tutor, now mimics floppy-based Tutor
*/

#include <stdio.h> // get system libraries stdio & add to text to current src file
#include "slex.h" // get slex.h from local directory (same folder) & add content to current src file

/* The preprocessor symbol SAPC is #defined in $pcinc/stdio.h.  This
 * stdio.h is found by i386-gcc because of the -I flag in its compile command
 * in the Makefile.  In the UNIX case, there is no such redirection,
 * so the ordinary stdio.h is used, and it has no SAPC definition.
 */
#ifdef SAPC // -I in makefile tells gcc to find elsewhere, SAPC is a stripped down version of C environment, checks if it is found
#define PROMPT "PC-tutor> " // if SAPC found, then make PROMPT = PC-tutor, u are in a SAPC device
#else // if it is not found
#define PROMPT "UNIX-tutor> " // make PROMPT = UNIX-tutor, u are in a linux device
#endif // end the prepocessor condition statement
int main(void); // define an empty main function for preprocessor
void getcmd(char *, int *, int *); // define the getcmd command for preprocessor

int main() // main function, program starts here
{
  int done = 0; // checks if the entire program is done, on/off button
  int cnum,linepos; // declares the command # (address) & the current line poistion
  char linebuf[LINELEN]; // this is for the input line from the user len of 80
  Cmd *cp; // pointer to all existing commands available

  printf("     cmd    help message\n"); // prints some shit
  for(cp = cmds; cp->cmdtoken; cp++) // loops through the existing commands and lists the help var of each of them
      printf("%8s    %s\n", cp->cmdtoken, cp->help);

  while (!done) { // while the program is not done (not 1)
    /* get cmd token from user by parsing first part of line in linebuf-- */
    getcmd(linebuf,&cnum,&linepos); // get the line, command number and the line position after the command
    /* call implementation routine for matched cmd-- */
    /* (remaining part of line to parse starts at linebuf+linepos) */
    done = cmds[cnum].cmdfn(&cmds[cnum], linebuf+linepos); // commnad # is where the command is at in the cmds array, then call it's funtion w/
    // cont... -> the address on where the command is at, the remaining part of the line to parse is given with linebuf + linepose, which goes to the linpos position
    // cont... -> the stop fucntion is the only one that returns 1, rest return 0 & display info
  }
  // official end
  return 0;
}

/***********************************************************************
*
*  getcmd 
*
* Better than scanf--read whole line in, parse resulting string.
* Uses slex package to match cmd token in first part of linebuf 
* and return command # and where to continue parsing.
* Getcmd loops until user provides proper command, so no error return.
*/
void getcmd(char *linebuf,    // the line of user input
             int *cnum_ptr,   /* returned command number */ // the index of the command in the commands array
             int *pos_ptr     /* position in linebuf (after cmd token) */ // the postion where the pointer ends up (after the first string/char set)
           )
{
  int done = 0; // on/off button, tells when the program is done

  while (!done) { // while done != 0, the program keeps looping
     printf( PROMPT ); // prints out the PROMPT shit
     gets(linebuf);		/* read line from user, null terminate */ // reads a line from user (stops w/ enter or /0 (end of file))
     /* match cmd token to get cnum--call slex package to do lookup-- */
     if (slex( linebuf, cmds, cnum_ptr, pos_ptr)==0)  // checks to see if the first string the user has input is a command that exists
         done = 1;	/* success in matching cmd */ // stops the program if it does exist
     else
         printf(" No such command\n"); // prints out that it don't exist & keeps the loop going until user writes an existing command
  }
}
