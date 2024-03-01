#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdbool.h>

// Accept up to 16 command-line arguments
#define MAXARG 16

// Allow up to 64 environment variables
#define MAXENV 64

// Keep the last 500 commands in history
#define HISTSIZE 500

// Accept up to 1024 bytes in one command
#define MAXLINE 1024

// Parses the command line arguments.
static char **parseCmd(char cmdLine[]) {
  char **cmdArg, *ptr;
  int i;

  //(MAXARG + 1) because the list must be terminated by a NULL ptr
  cmdArg = (char **) malloc(sizeof(char *) * (MAXARG + 1));
  if (cmdArg == NULL) {
    perror("parseCmd: cmdArg is NULL");
    exit(1);
  }
  for (i = 0; i <= MAXARG; i++) //note the equality
    cmdArg[i] = NULL;
  i = 0;
  ptr = strsep(&cmdLine, " ");
  while (ptr != NULL) {
    // (strlen(ptr) + 1)
    cmdArg[i] = (char *) malloc(sizeof(char) * (strlen(ptr) + 1));
    if (cmdArg[i] == NULL) {
      perror("parseCmd: cmdArg[i] is NULL");
      exit(1);
    }
    strcpy(cmdArg[ i++ ], ptr);
    if (i == MAXARG)
      break;
    ptr = strsep(&cmdLine, " ");
  }
  return(cmdArg);
}

/* Returns a copy of the pointer passed in as a parameter. In this case it's
used for copying the environment variables pointer.
*/
char **copyEnvp(char **envp) {
  char **envVariables;
  envVariables = (char **) malloc(sizeof(char *) * (MAXENV + 1));
  int k = 0;
  while (envp[k] != NULL) {
    envVariables[k] = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
    strcpy(envVariables[k], envp[k]);
    k++;
  }
  return envVariables;
}

/* Used to print out the environment variables directly from the pointer.
This method was only used for testing.
*/
void printEnvVariables(char **envVariables) {
  int x = 0;
  while (envVariables[x] != NULL) {
    printf("%s\n", envVariables[x]);
    x++;
  }
}

// Returns the environment variables.
char **getVariables(char **envp) {
  char **variables;
  variables = (char **) malloc(sizeof(char *) * (MAXENV + 1));
  int k = 0;
  while (envp[k] != NULL) {
    variables[k] = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
    char *temp = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
    strcpy(temp, envp[k]);
    variables[k] = strsep(&temp, "=");
    k++;
  }
  return variables;
}

// Returns the values/paths of the environment variables.
char **getValues(char **envp) {
  char **values;
  values = (char **) malloc(sizeof(char *) * (MAXENV + 1));
  int k = 0;
  while (envp[k] != NULL) {
    values[k] = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
    char *temp = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
    strcpy(temp, envp[k]);
    strsep(&temp, "=");
    strcpy(values[k], temp);
    k++;
  }
  return values;
}

// Checks if a variable exists.
bool hasVariable(char *variable, char **envVariables) {
  int k = 0;
  while (envVariables[k] != NULL) {
    if (strcmp(envVariables[k], variable) == 0) {
      return true;
    }
    k++;
  }
  return false;
}

/* Returns the index of an environment variable. Can be used as an index for
the value of that environment variable.
*/
int getVariableIndex(char* variable, char **envVariables) {
  int k = 0;
  while (envVariables[k] != NULL) {
    if (strcmp(envVariables[k], variable) == 0) {
      return k;
    }
    k++;
  }
  return -1;
}

// Updates the value of an environment variable.
char **updatedValues(int index, char *newVal, char **envValues) {
  char **values = copyEnvp(envValues);
  free(values[index]);
  values[index] = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
  strcpy(values[index], newVal);
  return values;
}

// Prints out variables and their values/paths
void printAll(char **variables, char **values) {
  int x = 0;
  while (variables[x] != NULL) {
    printf("%s=%s\n", variables[x], values[x]);
    x++;
  }
}

// Returns the number of environment variables.
int envSize(char** variables) {
  int x = 0;
  while (variables[x] != NULL) {
    x++;
  }
  return x;
}

// Deletes a value based on a given index.
char **deleteValues(int index, char **envValues) {
  char **values = copyEnvp(envValues);
  free(values[index]);
  values[index] = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
  for (int i = index; i < envSize(values) - 1; i++) {
    strcpy(values[index], values[index + 1]);
  }
  int s = envSize(values);
  values[s - 1] = NULL;
  return values;
}

// Deletes a variable based on a given index.
char **deleteVariables(int index, char **envVariables) {
  char **variables = copyEnvp(envVariables);
  variables[index] = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
  for (int i = index; i < envSize(variables) - 1; i++) {
    strcpy(variables[index], variables[index + 1]);
  }
  int s = envSize(variables);
  variables[s - 1] = NULL;
  return variables;
}

// Returns the index of a variable's value.
int findValue(char *variable, char **variables) {
  int x = 0;
  while (variables[x] != NULL) {
    if (strcmp(variable, variables[x]) == 0) {
      return x;
    }
    x++;
  }
  return -1;
}

// Update the PWD environment variable value.
void updatePWD(char **variables, char **values, char *dir) {
  int pwdIndex = findValue("PWD", variables);
  free(values[pwdIndex]);
  values[pwdIndex] = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
  strcpy(values[pwdIndex], dir);
  printf("PWD is now: %s\n", dir);
}

// Changes current directory to the path/value of the HOME environment variable.
void defaultChangeDirectory(char **variables, char **values) {
  char *dir = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
  int index = findValue("HOME", variables);
  strcpy(dir, values[index]);
  int chd = chdir(dir);
  if (chd == 0) {
    updatePWD(variables, values, dir);
    printf("current directory is now: %s\n", dir);
  } else {
    printf("Not an existing path\n");
  }
}

// Returns the size of an argument.
int argSize(char *arg) {
  int size = 0;
  while (arg[size] != '\0') {
    size++;
  }
  return size;
}


// Change directory to a string that starts with two dots.
void *getPathFromTwoDots(char *path, char **variables, char **values) {
  char *twoDots = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
  int pathIndex = 2;
  int tdIndex = 0;
  while (path[pathIndex] != '\0') {
    twoDots[tdIndex] = path[pathIndex];
    pathIndex++;
    tdIndex++;
  }
  int chd = chdir(twoDots);
  if (chd == 0) {
    printf("current directory is now: %s\n", twoDots);
    updatePWD(variables, values, twoDots);
  } else {
    printf("Not an existing path\n");
  }
}

// Change directory to a string that starts with one dot.
void *getPathFromOneDot(char *path, char **variables, char **values) {
  char *oneDot = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
  int pathIndex = 1;
  int odIndex = 0;
  while (path[pathIndex] != '\0') {
    oneDot[odIndex] = path[pathIndex];
    pathIndex++;
    odIndex++;
  }
  int chd = chdir(oneDot);
  if (chd == 0) {
    printf("current directory is now: %s\n", oneDot);
    updatePWD(variables, values, oneDot);
  } else {
    printf("Not an existing path\n");
  }
}

// Used to initialize the history pointer.
char **setUpHistory() {
  char **history;
  history = (char **) malloc(sizeof(char *) * (HISTSIZE + 1));
  for (int i = 0; i <= HISTSIZE; i++) {
    history[i] = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
  }
  return history;
}

/* Deletes the oldest command in history and shifts all commands in the
pointer array down by 1.
*/
char **refreshHistory(char **history) {
  char **temp;
  temp = (char **) malloc(sizeof(char *) * (HISTSIZE + 1));
  for (int i = 0; i <= HISTSIZE; i++) {
    strcpy(temp[i], history[i]);
  }
  temp[0] = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
  for (int i = 0; i <= HISTSIZE; i++) {
    temp[i] = temp[i + 1];
  }
  return temp;
}

int main(int argc, char *argv[], char *envp[]) {
  // Get the environment variables, their paths and initialize the history pointer.
  char cmdLine[MAXLINE], **cmdArg;
  char **envVariables = copyEnvp(envp);
  char **variables = getVariables(envVariables);
  char **values = getValues(envVariables);
  char **history = setUpHistory();
  int h = 0;

  while (( 1 )) {
    printf("bsh> ");                      //prompt
    fgets(cmdLine, MAXLINE, stdin);       //get a line from keyboard
    cmdLine[strlen(cmdLine) - 1] = '\0';  //strip '\n'

    // Check if maximum commands in history is reached
    if (h == HISTSIZE) {
      history = refreshHistory(history);
      h--;
    }
    strcpy(history[h], cmdLine);
    h++;

    // Parse the command line arguments
    cmdArg = parseCmd(cmdLine);

    // Check if there is an I/O redirection
    if (cmdArg[1] != NULL) {
      if (strcmp(cmdArg[1], ">") == 0) {
        printf("redirect stdout to file\n");
      } else if (strcmp(cmdArg[1], "<") == 0) {
        printf("redirect stdin from file\n");
      }
    }

    // Built-in command exit
    if (strcmp(cmdArg[0], "exit") == 0) {
      printf("exiting\n");
      break;
    }
    // Built-in command env
    else if (strcmp(cmdArg[0], "env") == 0) {
      printAll(variables, values);
    }
    // Built-in command setenv
    else if (strcmp(cmdArg[0], "setenv") == 0) {
      // Variable already exists
      if (hasVariable(cmdArg[1], variables)) {
        int index = getVariableIndex(cmdArg[1], variables);
        values = updatedValues(index, cmdArg[2], values);
      }
      // Create new environment variable
      else {
        int size = envSize(variables);
        variables[size] = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
        values[size] = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
        strcpy(variables[size], cmdArg[1]);
        strcpy(values[size], cmdArg[2]);
      }
    }
    // Built-in command unsetenv
    else if (strcmp(cmdArg[0], "unsetenv") == 0) {
      // Variable exists
      if (hasVariable(cmdArg[1], variables)) {
        int index = getVariableIndex(cmdArg[1], variables);
        values = deleteValues(index, values);
        variables = deleteVariables(index, variables);
      }
      else {
        printf("Cannot unset a variable that does not exist\n");
      }
    }
    // Built-in command cd
    else if (strcmp(cmdArg[0], "cd") == 0) {
      // Command is ("cd" or "cd ")
      if (cmdArg[1] == NULL) {
        defaultChangeDirectory(variables, values);
      } else if (argSize(cmdArg[1]) == 0) {
        defaultChangeDirectory(variables, values);
      } else {
        char *path = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
        strcpy(path, cmdArg[1]);
        // Command has ".."
        if (path[0] == '.' && path[1] == '.') {
          if (argSize(path) == 2) {
            defaultChangeDirectory(variables, values);
          } else {
            getPathFromTwoDots(path, variables, values);
          }
        }
        // Command has "."
        else if (path[0] == '.') {
          getPathFromOneDot(path, variables, values);
        }
        // User put in the path directly 
        else {
          int chd = chdir(path);
          if (chd == 0) {
            printf("current directory is now: %s\n", path);
            updatePWD(variables, values, path);
          } else {
            printf("Not an existing path\n");
          }
        }
      }
    }
    // Built-in command history
    else if (strcmp(cmdArg[0], "history") == 0) {
      printf("History:\n");
      for (int i = 0; i < h; i++) {
        printf("%d  %s\n", (i + 1), history[i]);
      }
    }
    // Unsupported commands
    else {
      // Declare and initialize variables.
      char *pathCopy, *splitPath, *command, **allPaths, **pathCommands;
      pathCopy = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
      command = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
      splitPath = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
      allPaths = (char **) malloc(sizeof(char *) * (MAXLINE + 1));
      for (int i = 0; i <= MAXLINE; i++) {
        allPaths[i] = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
      }
      pathCommands = (char **) malloc(sizeof(char *) * (MAXLINE + 1));
      for (int i = 0; i < MAXLINE + 1; i++) {
        pathCommands[i] = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
      }

      // Get each directory in PATH
      int p = getVariableIndex("PATH", variables);
      strcpy(pathCopy, values[p]);
      int ap = 0;
      while (pathCopy != NULL) {
        allPaths[ap] = strsep(&pathCopy, ":");
        ap++;
      }

      // Command to add to the end of each directory in PATH
      strcpy(command, "/");
      strcat(command, cmdArg[0]);

      // Create the absolute paths
      for (int i = 0; i < ap; i++) {
        char *temp = (char *) malloc(sizeof(char *) * (MAXLINE + 1));
        strcpy(temp, allPaths[i]);
        strcat(temp, command);
        strcpy(pathCommands[i], temp);
      }

      // Make a system call to each absolute path
      int stat = 0;
      int a;
      for (int i = 0; i < ap; i++) {
        int acc = access(pathCommands[i], a);
        if (acc == 0) {
          pid_t child = fork();
          if (child != 0) {
            printf("\twaiting for child process...\n");
            waitpid(child, &stat, 0);
          } else {
            printf("\trunning (%s)\n", pathCommands[i]);
            stat = execv(pathCommands[i], cmdArg);
            if (stat == 0) {
              printf("\tno such command (%s), execv did not work\n", cmdArg[0]);
            }
          }
        } else {
          printf("\tno such command (%s)\n", allPaths[i]);
        }
      }
    }
    // Clean up before running the next command
    int i = 0;
    while (cmdArg[i] != NULL)
      free( cmdArg[i++] );
    free(cmdArg);
  }
  return 0;
}
