/******************************************************************
*
*   file:   slex.c
*   author: betty o'neil
*   date:   ?
*
*   simple lexical analyzer, front part of a parser                   
*   --compare to UNIX tool "lex", general lexical analyzer            
*   gets a (space-delimited) token from linebuf and tries to match    
*   it to one of the cmdtokens in the provided cmdtable             
*
*   accepts:       
*         linebuf--string to get token from                         
*         cmdtable--cmdtable to use                                 
*   returns:                                                          
*         *cnum_ptr--command # (offset in cmdtable) or -1 if no match
*         *pos_ptr--new place in linebuf after token match          
*
*   improvements needed:
*         make token matching case independent
*         skip whitespace, not just blanks
*
*   revisions: 
*     9/90 cleanup, convert function headers to ansi form
*
*/

#include <stdio.h> // get system library stdio.h & put it's content here
//#include <string.h>
#include "slex.h"		/* for definition of type cmd */ // get slex.h content & put it here

int slex(char *linebuf,    /* string from user */ // line input from user
         Cmd cmdtable[],   /* cmd table to use */ // list of commands that exist
         int *cnum_ptr,    /* returned command number */ // index of command in command table/array
         int *pos_ptr)      /* returned new place in linebuf */ // return after the first string
{
  int i = 0; // index for command
  char token[MAXTOKENLEN]; // max len of a command is 80
  int newpos; // new postion, after the first command

  if (gettoken(linebuf,token,&newpos)<0) /* get token from linebuf */ // if there are no commands in linebuf(user input), return -1, tell user to try again, fills in the token & posit
    return -1;			/* couldn't find token */

  while ((cmdtable[i].cmdtoken != NULL)) { // go through the entire command table until each commands token does not == to null, basically the last element that == null
    if (strcmp(cmdtable[i].cmdtoken,token)==0) { // if the command from table == to the token/command found
	*cnum_ptr = i;		/* success--return command # */ // set the command index to the index where you found the command in the table
	*pos_ptr = newpos;	/* and where we got to in linebuf */ // make the current position == to the postion after the command for linebuf
	return 0; // return success
    }
    else
      i++;			/* keep scanning table */ // otherwsie keep goign through the table
  }
  return -1;			/* no match */ // command doesn't exist
}

/******************************************************************
 * get one space-delimited token from string in linebuf, also return 
 * new position in string 
 */
int gettoken(char *linebuf, char *token, int *pos_ptr) // get the token & current new postion
{
  int i = 0; // command index
  int j = 0; // array loop index

  while (linebuf[i] == ' ') // skips empty spaces if user being a dumbass at beginiing
    i++;			/* skip blanks */
  while (linebuf[i] != ' '&&linebuf[i]!='\0') // while no white space & curr char != end of line or enter
    token[j++] = linebuf[i++];	/* copy chars to token */ // put the char from linebuff to token
  if (j==0) // if loop never happened, means nothing was entered
    return -1;			/* nothing there */ // return mission failed & to try program again
  else
    {
      token[j] = '\0';		/* null-terminate token */ // otherwise give the token an EOL char at the end
      *pos_ptr = i;		/* return place in linebuf we got to */ // make the new position where the string/command ended for future parsing
      return 0;			/* success */ // return the program to keep going
    }
}
