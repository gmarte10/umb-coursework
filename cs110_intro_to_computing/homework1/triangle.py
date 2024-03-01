import stdio
import sys


# Get x from command line, as an int.
x = int(sys.argv[1])

# Get y from command line, as an int.
y = int(sys.argv[2])

# Get z from command line, as an int.
z = int(sys.argv[3])

# Build an expression which is True if each of x, y, and z is less
# than or equal to the sum of the other two; and False otherwise.
isTriangle = (x <= y + z) and (y <= x + z) and (z <= x + y)
# Write the expression.
stdio.writeln(isTriangle)
