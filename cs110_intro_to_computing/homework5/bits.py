import stdio
import sys


def check_zeros(s):
    i = 0
    if s[0] == "0":
        i += 1
    return i


def check_ones(s):
    j = 0
    if s[0] == "1":
        j += 1
    return j


# Return the number of zeros in s, computed recursively.
def zeros(s):
    if s == "":
        return 0
    return check_zeros(s) + zeros(s[1:])


# Return the number of ones in s, computed recursively.
def ones(s):
    if s == "":
        return 0
    return check_ones(s) + ones(s[1:])


# Test client [DO NOT EDIT]. Reads a string s from command line and writes the
# the number of zeros and ones in s, both computed recursively.
def _main():
    s = sys.argv[1]
    stdio.writef('zeros = %d, ones = %d, total = %d\n',
                 zeros(s), ones(s), len(s))


if __name__ == '__main__':
    _main()
