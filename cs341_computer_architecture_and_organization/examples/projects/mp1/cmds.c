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
              {NULL,  NULL,        NULL}};  /* null cmd to flag end of table */

char xyz = 110;  /* test global variable  */
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
  // print address with right amount of zeros
  //
  // get len of argument
  int len = 0;
  char *arg = arguments;
  while (*arg) {
	  len++;
	  arg++;
  }
  len--;
  printf("len: %d\n", len);
  // print zero's first
  int zeros = 8 - len;
  char z = '0';
  printf("testing zeros: ");
  for (int i = 0; i < zeros; i++) {
	  printf("%c", z);
  }
  printf("\n");
  
  
  // convert arguments to int
  int arg_int = 0;
  int sf = sscanf(arguments, "%x", &arg_int);
  char *addr_p = (char *)arg_int;
  printf("address pointer: %x\n",(unsigned)addr_p);
  printf("sscanf: %d\n", sf);
 
  // get value
  int i = 0;
  while (i < 16) {
	  printf("%x", addr_p[i]);
	  i++;
	  printf("%x ", addr_p[i]);
  }
  printf("\n");
  
  // first print without conversion
  printf("half way point: \n");
  for (int j = 0; j < zeros; j++) {
	  printf("%c", z);
  }
  printf("%x ", (unsigned)addr_p);
  
  char *print_hex = addr_p;
  for (int j = 0; j < 16; j++) {
	  printf("%02x ", *print_hex++);
  }

  for (int j = 0; j < 16; j++) {
	  if (*addr_p > 0x20 && *addr_p < 0x7e) {
		  printf("%c", *addr_p);
		  addr_p++;
	  }
	  else {
		  printf(".");
	  }
  }

  printf("\n");
  
  printf("Reached mem_display, passed argument string: |%s|\n", arguments);
  printf("        help message: %s\n", cp->help);
  return 0;
}
