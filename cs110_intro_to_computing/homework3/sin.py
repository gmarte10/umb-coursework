import math
import stdio
import sys


# Return sin(x) calculated using the formula:
#   sin(x) = x - x^3/3! + x^5/5! - x^7/7! + ...
def sin(x):
    # Initialize total (sum of the series) to 0.0.
    total = 0.0

    # Initialize term (each term in the series) to 1.0, and sign
    # (sign of the term) to 1.
    term = 1.0
    sign = 1
    # Initialize i to 1.
    i = 1

    # Repeat until convergence.
    while total != total + term:
        # Set term to its previous value times x divided by i.
        term = (term * x) / i

        # If i is odd, increment total by sign * term, and
        # toggle (negate) sign.
        if i % 2 != 0:
            total += sign * term
            sign *= -1
        # Increment i by 1.
        i += 1

    # Return the result, total.
    return total


# Test client [DO NOT EDIT].
def _main():
    x = math.radians(float(sys.argv[1]))
    stdio.writeln(sin(x))
    stdio.writeln(math.sin(x))


if __name__ == '__main__':
    _main()
