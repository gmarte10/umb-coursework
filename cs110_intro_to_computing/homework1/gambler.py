import stdio
import sys

# n1(player 1 pennies), n2(player2 pennies)
# p(player1 win probability), q(player2 win probability)
n1 = int(sys.argv[1])
n2 = int(sys.argv[2])
p = float(sys.argv[3])
q = 1 - p

# Calculations,P1(player1 penniless), P2(player2 penniless)
P1 = (1 - ((p / q) ** n2)) / (1 - ((p / q) ** (n1 + n2)))
P2 = (1 - ((q / p) ** n1)) / (1 - ((q / p) ** (n1 + n2)))

stdio.writeln(str(P1) + ' ' + str(P2))
