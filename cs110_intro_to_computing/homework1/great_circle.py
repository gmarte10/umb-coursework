import math
import stdio
import sys

# x1,y1,x2,y2(latitude and longitude of 2 points in degrees)
x1 = math.radians(float(sys.argv[1]))
y1 = math.radians(float(sys.argv[2]))
x2 = math.radians(float(sys.argv[3]))
y2 = math.radians(float(sys.argv[4]))

# Calculations, d(great-circle distance in km
d = 111 * (math.degrees(math.acos(math.sin(x1) * math.sin(x2) + math.cos(x1) *
                        math.cos(x2) * math.cos(y1 - y2))))

stdio.writeln(d)
