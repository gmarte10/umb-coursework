import stdio
import sys


class Interval:
    """
    Represents a 1-dimensional interval [lbound, rbound].
    """

    def __init__(self, lbound, rbound):
        """
        Construct a new interval given its lower and
        upper bounds.
        """
        self.lower = lbound
        self.upper = rbound

    def lbound(self):
        """
        Return the lower bound of the interval.
        """
        return self.lower

    def rbound(self):
        """
        Return the upper bound of the interval.
        """
        return self.upper

    def contains(self, x):
        """
        Return True if self contains the point x and False otherwise.
        """
        return self.lower <= x <= self.upper

    def intersects(self, other):
        """
        Return True if self intersects other and
        False othewise.
        """
        return (self.lower <= other.lower and self.upper >= other.upper) or (
                    other.lower <= self.lower <= other.upper) or (
                    other.lower <= self.upper <= other.upper)

    def __str__(self):
        """
        Returns a string representation of self.
        """

        result = '[' + str(self.lower) + ', ' + str(self.upper) + ']'
        return result


# Test client [DO NOT EDIT].
def _main():
    x = float(sys.argv[1])
    intervals = []
    while not stdio.isEmpty():
        lbound = stdio.readFloat()
        rbound = stdio.readFloat()
        intervals += [Interval(lbound, rbound)]
    for i in range(len(intervals)):
        if intervals[i].contains(x):
            stdio.writef('%s contains %f\n', intervals[i], x)
    for i in range(len(intervals)):
        for j in range(i + 1, len(intervals)):
            if intervals[i].intersects(intervals[j]):
                stdio.writef('%s intersects %s\n',
                             intervals[i], intervals[j])


if __name__ == '__main__':
    _main()
