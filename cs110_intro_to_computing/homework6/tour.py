import stdio
from point import Point


class Tour:
    """
    Represents a tour in the traveling salesperson problem.
    """

    def __init__(self):
        """
        Creates an empty tour.
        """

        self._tour = []

    def show(self):
        """
        Prints the tour to standard output.
        """
        for p in self._tour:
            stdio.writeln(p)

    def draw(self):
        """
        Draws the tour to standard draw.
        """
        length = len(self._tour)
        for i in range(length):
            self._tour[i].draw()
            self._tour[i].drawTo(self._tour[(i + 1) % length])

    def size(self):
        """
        Returns the number of points on the tour.
        """
        return len(self._tour)

    def distance(self):
        """
        Returns the total distance of the tour.
        """
        length = len(self._tour)
        distance = 0
        for i in range(length):
            distance += self._tour[i].distanceTo(self._tour[(i + 1) % length])
        return distance

    def insertNearest(self, p):
        """
        Inserts the point p using the nearest neighbor heuristic.
        """
        index = -1
        min = float('inf')
        for i in range(len(self._tour)):
            distance = self._tour[i].distanceTo(p)
            if distance < min:
                index = i
                min = distance
        self._tour.insert(index + 1, p)

    def insertSmallest(self, p):
        """
        Inserts the point p using the smallest increment heuristic.
        """
        index = 1
        min = float('inf')
        for i in range(1, len(self._tour)+1):
            a = self._tour[i - 1]
            b = self._tour[i % (len(self._tour))]
            distance = a.distanceTo(p) + b.distanceTo(p) - a.distanceTo(b)
            if distance < min:
                index = i
                min = distance
        self._tour.insert(index, p)
