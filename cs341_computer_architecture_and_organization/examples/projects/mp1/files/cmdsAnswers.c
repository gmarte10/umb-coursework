/******************************************************************
*
*   file:     cmds.c
*   author:   betty o'neil
*   date:     ?
*
*   semantic actions for commands called by tutor (cs341, mp1)
*
*   revisions:
*      9/90  eb   cleanup, convert function declarations to ansi
*      9/91  eb   changes so that this can be used for hw1
*      9/02  re   minor changes to quit command
*/
/* the Makefile arranges that #include <..> searches in the right
   places for these headers-- 200920*/

#include <stdio.h>
#include "slex.h"

/*===================================================================*
*
*   Command table for tutor program -- an array of structures of type
*   cmd -- for each command provide the token, the function to call when
*   that token is found, and the help message.
*
*   slex.h contains the typdef for struct cmd, and declares the
*   cmds array as extern to all the other parts of the program.
*   Code in slex.c parses user input command line and calls the
*   requested semantic action, passing a pointer to the cmd struct
*   and any arguments the user may have entered.
*
*===================================================================*/

PROTOTYPE int stop(Cmd *cp, char *arguments);
PROTOTYPE int mem_display(Cmd *cp, char *arguments);

/* command table */

Cmd cmds[] = {{"md",  mem_display, "Memory display: MD <addr>"},
              {"s",   stop,        "Stop" },

              {"ms",   mem_set,        "Memory set: MS<addr><value>" }  // ADDED !!!!!!!!!!!!!!!!
              {"h",   help,        "Help: H<command>" }


              {NULL,  NULL,        NULL}};  /* null cmd to flag end of table */

char xyz = 6;  /* test global variable  */
char *pxyz = &xyz;  /* test pointer to xyz */
/*===================================================================*
*		command			routines
*
*   Each command routine is called with 2 args, the remaining
*   part of the line to parse and a pointer to the struct cmd for this
*   command. Each returns 0 for continue or 1 for all-done.
*
*===================================================================*/

int stop(Cmd *cp, char *arguments)
{
	return 1;			/* all done flag */
}

/*===================================================================*
*
*   mem_display: display contents of 16 bytes in hex
*
*/

int mem_display(Cmd *cp, char *arguments)
{
  // print the contents at whatever *cp is in hex of 16 byrs, same format as real utor program, 16 pairs of hex chars folled by 16 char interpreting each byte as ASCII
  // or "." for nonprintable ASCII
	char *ptr_addr = (char *)cp;
	int i = 0;
	while (ptr_addr[i] != NULL) {
		printf("%x  ", ptr_addr[i]);
	}


  printf("Reached mem_display, passed argument string: |%s|\n", arguments);
  printf("        help message: %s\n", cp->help);
  return 0;			/* not done */
}




int help(Cmd *cp, char *arguments) {
	// if argument != null then find the specific cp(will be string) and print it's help
	int i = 0;
	printf("command     help message");
	while (cp[i].token != NULL) {
		printf("%s          %s", cp[i].token, cp[i].help);
	}
}


int mem_set(Cmd *cp, char *arguments) {
	// go through linebuff starting at arguments and parse the value, set the value into the cp address
	// parse the val
	int *i = (int *)arguments;

	while (linebuf[i] == ' ') {
		i++;
	}
	char *val;
	int v = 0;
	while (linebuf[i] != ' '&&linebuf[i]!='\0') {
		val[v++] = linebuf[i];
	}
}