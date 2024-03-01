import math
import stdio
import sys

# θ1(angle of refraction1), n1,n2(indices of refractions)
θ1 = math.radians(float(sys.argv[1]))
n1 = float(sys.argv[2])
n2 = float(sys.argv[3])

# Calculations, θ2(angles of refraction2)
θ2 = math.degrees(math.asin((n1 / n2) * math.sin(θ1)))

stdio.writeln(θ2)
