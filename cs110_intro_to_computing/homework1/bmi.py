import stdio
import sys

# Get weight from command line, as a float.
weight = float(sys.argv[1])

# Get height from command line, as a float.
height = float(sys.argv[2])

# Calculate bmi as weight divided by square of the height.
bmi = weight / height**2

# Write bmi.
stdio.writeln(bmi)
