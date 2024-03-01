import stdio
import sys


# Return True if pwd is a valid password and False otherwise.
def is_valid(pwd):
    check1 = False  # length check
    check2 = False  # digit check
    check3 = False  # upper case check
    check4 = False  # lower case check
    check5 = False  # alphanumeric check

    # Perform length check on pwd.
    check1 = len(pwd) >= 8

    # Iterate over characters c of pwd.
    for c in pwd:
        # Perform digit check on c.
        if str.isdigit(c):
            check2 = True
        # Perform upper case check on c.
        elif str.isupper(c):
            check3 = True
        # Perform lower case check on c.
        elif str.islower(c):
            check4 = True
        # Perform alphanumeric check on c.
        elif not str.isalnum(c):
            check5 = True

    # Return True if all checks are True and False otherwise.
    return check1 and check2 and check3 and check4 and check5


# Test client [DO NOT EDIT].
def _main():
    pwd = sys.argv[1]
    stdio.writeln(is_valid(pwd))


if __name__ == '__main__':
    _main()
