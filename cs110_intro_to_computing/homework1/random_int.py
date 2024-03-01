import random
import stdio
import sys

# Get a from command line, as an int.
a = int(sys.argv[1])

# Get b from command line, as an int.
b = int(sys.argv[2])

# Set r to a random integer between a and b, obtained by calling
# random.randrange().
r = random.randrange(a, b)

# Write r.
stdio.writeln(r)
