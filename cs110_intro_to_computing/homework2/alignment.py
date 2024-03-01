import stdarray
import stdio

# Read x, y, and opt from standard input.
x = stdio.readString()
y = stdio.readString()
opt = stdio.readAllInts()
stdio.writeln(opt[0][0])
# Compute M and N.
for i in range(0, M - 1):
    for j in range(0, N - 1):
        if opt[i][j] == opt[i + 1][j] + 2:
            i += 1
        elif opt[i][j] == opt[i][j + 1] + 2:
            j += 1
        else:
            if x[i] == y[j]:
                opt[i][j] = opt[i + 1][j + 1]
            else:
                opt[i][j] = opt[i + 1][j + 1] + 1
            i += 1
            j += 1

# Write edit distance between x and y.
stdio.writeln('Edit distance = ' + opt[0][0])
# Recover and write an optimal alignment.

i = 0
j = 0
while i < M and j < N:
    opt[i][j] = opt[i + 1][j + 1] + 2
    i += 1
    j += 1

# x is exhausted before y.
while j < N:
    opt[i][j] = opt[i + 1][j] + 2
    j += 1

# y is exhausted before x.
while i < M:
    opt[i][j] = opt[i][j + 1] + 2
    i += 1
