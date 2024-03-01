import stdio
import sys

# Get N from command line, as an int.
N = int(sys.argv[1])

# Define primes to store the result (number of primes <= N).
primes = 0

# Iterate over integers 2 to N (inclusive).
for i in range(2, N + 1):
    # Define a variable j to store the potential divisors of i,
    # and initialize it to 2.
    j = 2

    # Repeat as long as j is less than or equal to i / j.
    while j <= i / j:
        # If i is divisible by j, it is not a prime so exit
        # (break) this inner loop.
        if i % j == 0:
            break

        # Increment j by 1.
        j += 1

    # If j is greater than i / j, then i is a prime. So
    # increment primes by one.
    if j > i / j:
        primes += 1

# Write primes.
stdio.writeln(primes)
