import math
import stdio
import sys

# λ(average # of events per time), t(time)
λ = float(sys.argv[1])
t = float(sys.argv[2])

# P = P(t)
P = math.exp(-λ * t)

stdio.writeln(P)
