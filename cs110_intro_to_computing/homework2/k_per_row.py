import stdio
import sys

# Get a, b, and k from command line, as ints.
a = int(sys.argv[1])
b = int(sys.argv[2])
k = int(sys.argv[3])

# Iterate over the integers a to b (both inclusive), and
# write k of them per row.
for i in range(a, b + 1):
    # If i is a multiple of k, write i followed by a
    # new line. Otherwise, write i followed by a space.
    if i % k == 0:
        stdio.writeln(i)
    else:
        stdio.write(str(i) + ' ')
