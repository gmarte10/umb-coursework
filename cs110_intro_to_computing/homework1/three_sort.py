import stdio
import sys

# x,y,z(user input integers)
x = int(sys.argv[1])
y = int(sys.argv[2])
z = int(sys.argv[3])

# Calculations, m(smallest integer), M(largest integer), n(middle integer)
m = min(x, y, z)
M = max(x, y, z)
n = (x + y + z) - (M + m)

stdio.writeln(str(m) + ' ' + str(n) + ' ' + str(M))
