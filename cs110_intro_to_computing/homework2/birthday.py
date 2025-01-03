import random
import stdarray
import stdio
import sys

DAYS_PER_YEAR = 365

# Get trials from command line, as an int.
trials = int(sys.argv[1])

# Define a variable count denoting the total number of
# individuals sampled across the trials number of experiments,
# and initialize it to 0.
count = 0

# Peform trials number of experiments, where each experiment
# involves sampling individuals until a pair of them share
# a birthday.
for t in range(0, trials + 1):
    # Setup a 1D list birthdays_seen of DAYS_PER_YEAR booleans,
    # all set to False by default. This list will keep track
    # of the birthdays encountered in this experiment.
    birthdays_seen = stdarray.create1D(DAYS_PER_YEAR, False)

    # Sample individuals until match.
    while True:
        # Increment count by 1.
        count += 1

        # Define a variable birthday with a random integer
        # from the interval [0, DAYS_PER_YEAR).
        birthday = random.randrange(0, DAYS_PER_YEAR)

        # If birthday has been encountered, abort this experiment.
        if birthdays_seen[birthday]:
            break

        # Record the fact that we are seeing this birthday for
        # the first time.
        else:
            birthdays_seen[birthday] = True

# Write the average number of people that must be sampled before
# a match, as an int.
stdio.writeln(int(count/trials))
