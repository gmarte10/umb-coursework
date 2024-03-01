import stdarray
import stdio
import sys

# Get n from command line, as an int.
n = int(sys.argv[1])
n = n + 1

# Construct a 2D ragged list a of integers. The list must
# have n + 1 rows, with the ith (0 <= i <= n) row a[i] having
# i + 1 elements, each initialized to 1. For example, if n = 3,
# a should be initialized to [[1], [1, 1], [1, 1, 1], [1, 1, 1, 1]].
a = []
for i in range(n):
    a += [[1]*(i+1)]

# Fill the ragged list a using the formula for Pascal's triangle
#     a[i][j] = a[i - 1][j - 1] + a[i - 1][j]
# where 0 <= i <= n and 1 <= j < i.
for i in range(0, n):
    for j in range(1, i):
        a[i][j] = a[i-1][j-1] + a[i-1][j]

# Write out the elements of the ragged list a.
for i in range(0, n):
    for j in range(0, i + 1):
        # If j is not the last column, write the element with a
        # space after.
        if j != i:
            stdio.write(str(int(a[i][j])) + str(' '))
        # Otherwise, write the element with a newline after.
        else:
            stdio.writeln(a[i][j])
            break
