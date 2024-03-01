import stdio
import sys

# m1(mass 2 kg), m2(mass2 kg), r(distance m), G(gravitational constant)
m1 = float(sys.argv[1])
m2 = float(sys.argv[2])
r = float(sys.argv[3])
G = 6.674e-11

# Calculations, F(gravitational force in N)
F = ((G * m1 * m2) / (r ** 2))

stdio.writeln(F)
