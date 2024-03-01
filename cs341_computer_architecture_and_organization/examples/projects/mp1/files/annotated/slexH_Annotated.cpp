/******************************************************************
*
*   file:     slex.h
*   author:   eb
*   date:     September, 1990
*
*   header file for lexical analyzer slex.h
*   built from betty o'neil's previous version
*   revisions:
*
*/
#ifndef PROTOTYPE // check if PROTOTYPE var not defined
#define PROTOTYPE    /* label ANSI C function prototypes with empty string */  // defines it as empty string
#endif // ends conditional statement

#define LINELEN 80 // All LINELEN in code replaced by 80, basically a var, the LINELEN of the terminal
#define MAXTOKENLEN 80 // All MAXTOKENLEN in code replaced by 80, a var, the max command length

/****************************************************************************
 *  What is a command? This typedef takes an object oriented view
 *  of the answer to that question.
 */
typedef struct cmdtag { // basically a command object, a token(md, s, ms or h), function for that token(memDisplay, Stop, setMemDisplay, Help), help string for it
  char *cmdtoken;		/* md or whatever--char string to invoke cmd */ // (md, s, ms or h)
  int (*cmdfn)(struct cmdtag *, char *);  /* function to call for this cmd */ // (mem_display, stop, set_mem_display, help)
  char *help;			/* helpstring for cmd */ // this is to get mem display, to stop, ...
} Cmd; // name of the struct

/****************************************************************************
 *  Tell the world that someone will provide space for and contents of
 *  the command table. 
 */

extern Cmd cmds[]; // all the commands available (md, ms, s, h)


/****************************************************************************
 *  A function prototype for the lexical analyzer:
 */

PROTOTYPE int slex(char *linebuf,    /* string from user */ // gets the entire line that the user has input
                   Cmd cmdtable[],   /* cmd table to use */ // gets the table of commands available (md, ms, s, h)
                   int *cnum_ptr,    /* returned command number */ // returns the command as a number (int), to be used as an address, then find the content of that addess and display it in terminal
                   int *pos_ptr      /* returned new place in linebuf */ // position after the first word/string the user has input
                  ) ;

/****************************************************************************
 *  A function prototype for gettoken, called by lexical analyzer
 */

PROTOTYPE int gettoken(char *linebuf, char *token, int *pos_ptr); // goes thru line user has input and finds a token(command), returns true(0) if it does, false(-1) otherwise
// ...cont -> sets the token found into the token pointer and sets the postion ptr after the token found (current place in linebuf after search)
// this is only an interface basically

/****************************************************************************
 *  A function prototype for getcmd, called by tutor main line
 */

PROTOTYPE void getcmd(char *linebuf, int *cnum_ptr, int *pos_ptr); // gets user input, reads the line (null terminates), uses slex to see if a token exists in that line, if not found, prints the prompt
// ...cont -> again until user goes put some shit there

// this is only an interface basically