# Makefile for building cs341 mp1 programs using the
# Standalone 486 or Pentium IBM PC running in 32-bit protected mode,
# or "SAPC" for short.  LINUX builds also, and "make clean"

# system directories needed for compilers, libraries, header files--
#    /home/cheungr/serl/tutor-linux
#    
# All UNIX tools from ulab had been moved to LINUX by
#
#     Ron Cheung (2/28/2020)
#
SAPC_TOOLS = /home/cheungr/serl/tutor-linux # the custom SAPC_Tools comes with the intel boi
PC_INC = $(SAPC_TOOLS)/include # a library further
PC_LIB = $(SAPC_TOOLS)/libc # another library

# ************** SAPC build **********
PC_CC    = gcc -DSAPC # compile for SAPC boi
PC_CFLAGS  = -gdwarf-2 -gstrict-dwarf -march=i586 -m32 -fno-builtin -fno-stack-protector -nostdlib     -c -Wall -I$(PC_INC) # diff flags & compile warnings
PC_AS    = as --32 # for 32 bit
PC_NM    = nm # for debugging
PC_LD    = ld -m elf_i386 # specifically for the elf_i386

# File suffixes:
# .c	C source (often useful both for UNIX and SAPC)
# .s 	assembly language source (gnu as for protected mode 486)
# .o    relocatable machine code, initialized data, etc., for UNIX and SAPC
# .lnx  executable image (bits as in memory), for SA PC (Linux a.out format)
# .syms text file of .exe's symbols and their values (the "symbol table")
# .usyms text file of UNIX executable's symbols

# where to find files
# change this to your homework folder
PROJ = $(HOME)/cs341/mp1 # proj folder in server

PC_OBJS = $(PROJ)/tutor.o $(PROJ)/slex.o # reps the tutor.o & the slex.o
UNIX_OBJS = $(PROJ)/tutor_u.o $(PROJ)/slex_u.o # reps the tutor_u.o & the slex_u.o

# SAPC executable--tell ld to start code at 0x100100, load special startup
# module, special PC C libraries--

tutor.lnx: $(PROJ)/cmds.o $(PC_OBJS) \ # depends on cmds.o & tutor.o & the slex.o existing
	$(PC_LIB)/startup0.o $(PC_LIB)/startup.o $(PC_LIB)/libc.a # startup/boot, make the code start at 100100, startup again, runs the .o & shit  ?
	$(PC_LD) -N -Ttext 100100 -o tutor.lnx \
	$(PC_LIB)/startup0.o $(PC_LIB)/startup.o \
	$(PC_OBJS) $(PROJ)/cmds.o $(PC_LIB)/libc.a
#
	rm -f syms;$(PC_NM) -n tutor.lnx>syms # remove debug maybe

# this rule allows you to build your own *.o files--
$(PROJ)/cmds.o: $(PROJ)/cmds.c $(PROJ)/slex.h # make a cmds.o depends on the c's being there
	$(PC_CC) $(PC_CFLAGS) -c -o $(PROJ)/cmds.o $(PROJ)/cmds.c # compiles into .o

$(PROJ)/tutor.o: $(PROJ)/tutor.c $(PROJ)/slex.h # same
	$(PC_CC) $(PC_CFLAGS) -c -o $(PROJ)/tutor.o $(PROJ)/tutor.c

$(PROJ)/slex.o: $(PROJ)/slex.c $(PROJ)/slex.h # same
	$(PC_CC) $(PC_CFLAGS) -c -o $(PROJ)/slex.o $(PROJ)/slex.c

# ************** LINUX build **********
# tell make to use gcc as C compiler, -g -W... for C compiler flags--
# use -m32 option to build 32-bit executable 
# use stdio.h from the GNU C library
# check "man gcc" to find out what -W flags do
#
CC = gcc -m32 # linux compiler
CFLAGS = -Wall -Wno-implicit -Wshadow -g # warnings for linux compiler

tutor:  $(UNIX_OBJS) $(PROJ)/cmds_u.o # depends on cmds_u.o dependency command
	$(CC) -g -o tutor $(UNIX_OBJS) $(PROJ)/cmds_u.o # compiles the .o shit
#
	rm -f usyms; nm -vpt x tutor > usyms

# make uses $(CC) and $(CFLAGS)
$(PROJ)/cmds_u.o:	$(PROJ)/cmds.c  $(PROJ)/slex.h # makes the cmds_u.o depends on .c and .h
	$(CC) $(CFLAGS) -c -o $(PROJ)/cmds_u.o $(PROJ)/cmds.c # makes the .o files
$(PROJ)/tutor_u.o: $(PROJ)/tutor.c $(PROJ)/slex.h # same
	$(CC) $(CFLAGS) -c -o $(PROJ)/tutor_u.o $(PROJ)/tutor.c
$(PROJ)/slex_u.o: $(PROJ)/slex.c  $(PROJ)/slex.h # same
	$(CC) $(CFLAGS) -c -o $(PROJ)/slex_u.o $(PROJ)/slex.c
# **************end of LINUX build ********

clean: # remove all .o, lnx, tutor and core files
	rm -f *.o *.lnx tutor core

