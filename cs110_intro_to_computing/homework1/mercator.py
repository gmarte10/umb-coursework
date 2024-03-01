import math
import stdio
import sys

# φ(latitude in degrees), λ(longitude in degrees)
φ = math.radians(float(sys.argv[1]))
λ = (float(sys.argv[2]))

# Calculations, x,y(rectangular coordinates)
x = λ
y = (math.log((1 + math.sin(φ)) / (1 - math.sin(φ)))) / (2)

stdio.writeln(str(x) + ' ' + str(y))
