import stdio
import sys

# Get x, y, and z from command line, as ints.
x = int(sys.argv[1])
y = int(sys.argv[2])
z = int(sys.argv[3])

# If x, y, and z are all equal, ie, x equals y and y equals z, then
# write "equal". Otherwise, write "not equal".
if x == y and y == z:
    stdio.writeln('equal')
else:
    stdio.writeln('not equal')
