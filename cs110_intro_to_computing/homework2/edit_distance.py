import stdarray
import stdio

# Read x and y.
x = stdio.readString()
y = stdio.readString()

# Create (M + 1) x (N + 1) matrix opt with elements initialized to 0, where
# M and N are lengths of x and y respectively.
M = len(x)
N = len(y)
opt = stdarray.create2D((M + 1), (N + 1), 0)

# Initialize bottom row opt[M][j] (0 <= j <= N) to 2 * (N - j).
for j in range(0, N + 1):
    opt[M][j] = 2 * (N - j)

# Initialize right column opt[i][N] (0 <= i <= M) to 2 * (M - i).
for i in range(0, M + 1):
    opt[i][N] = 2 * (M - i)

# Compute the rest of opt.
for i in range(M - 1, -1, -1):
    for j in range(N - 1, -1, -1):
        if x[i] == y[j]:
            opt[i][j] = min(opt[i + 1][j + 1], opt[i + 1][j] + 2,
                            opt[i][j + 1] + 2)
        else:
            opt[i][j] = min(opt[i + 1][j + 1] + 1, opt[i + 1][j] + 2,
                            opt[i][j + 1] + 2)

# Write x, y, dimensions of opt, and opt.
stdio.writef('%s\n%s\n%d %d\n', x, y, M + 1, N + 1)
for i in range(M + 1):
    for j in range(N):
        stdio.writef('%3d ', opt[i][j])
    stdio.writef('%3d\n', opt[i][N])
