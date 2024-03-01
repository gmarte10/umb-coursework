import random
import stdio
import sys

# n(number of sides)
n = int(sys.argv[1])

# r1 = roll 1, r2 = roll 2
r1 = random.randint(1, n)
r2 = random.randint(1, n)

# s = sum of the two rolls
s = r1 + r2

stdio.writeln(s)
