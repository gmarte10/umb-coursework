import stdio
import sys
import math


# Return the Euclidean distance between x and y, calculated as as
# the square root of the sums of the squares of the differences
# between corresponding entries. You may assume that x and y have
# the same length.
def distance(x, y):
    # Initialize total to 0.0.
    total = 0.0

    # Iterate over the lists x and y.
    for u, v in zip(x, y):
        # Add square of (u - v) to total.
        total += (u - v) ** 2

    # Return the square root of total.
    return math.sqrt(total)


# Test client [DO NOT EDIT].
def _main():
    n = int(sys.argv[1])
    x = [stdio.readFloat() for i in range(n)]
    y = [stdio.readFloat() for i in range(n)]
    stdio.writeln(distance(x, y))


if __name__ == '__main__':
    _main()
