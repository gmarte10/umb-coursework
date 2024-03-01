/* greedy algorithm solution */

int change = 60;
int quarter = 0;
int dime = 0;
int nickel = 0;
int penny = 0;
int totalCoins = 0;

while (change != 0) {
	if (change >= 25) {
		quarter++;
		change = change - 25;
	}
	else if (change >= 10) {
		dime++;
		change = change - 10;
	}
	else if (change >= 5) {
		nickel++;
		change = change - 5;
	}
	else {
		penny++;
		change = change - 1;
	}
}

totalCoins = quarter + dime + nickel + penny;


/* This is not the best solution because it sometimes doesn't work for certain amount of change (like 30) and
   adding or taking away some coinage will affect it. The best solution is a dynamic programming
   implementation.

/* 1 = 1p
   2 = 2p
   3 = 3p
   4 = 4p
   5 = 1n
   6 = 1n, 1p = 2
   7 = 1n, 2p = 3
   8 = 1n, 3p = 4
   9 = 1n, 4p = 5
   10 = 1d
   11 = 1d, 1p = 2
   12 = 1d, 2p = 3
   13 = 1d, 3p = 4
   14 = 1d, 4p = 5
   15 = 1d, 1n = 2
   16 = 1d, 1n, 1p = 3
   17 = 1d, 1n, 2p = 4
   18 = 1d, 1n, 3p = 5
   19 = 1d, 1n, 4p = 6
   20 = 2d
   21 = 2d, 1p = 3
   22 = 2d, 2p = 4

*/


int[] cointsUsed = {1, 2, 3, 4, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 2, 3, 4, 5, 6, 2, 3, 4};
int numOfCoins = 0;

public static void makeChange(int[] coins, intmaxChange, int[] coinsUsed, int[] lastCoin) {
	coinsUsed[0] = 0; lastCoin[ 0 ] = 1;
	for(int cents = 1; cents <= maxChange; cents++) {
		int minCoins = cents;
		int newCoin = 1;
		for(int j = 0; j < coins.length; j++) {
			if(coins[j] > cents )   continue; // Cannot use coin j
			if(coinsUsed[cents - coins[j]] + 1 < minCoins) {
				minCoins = coinsUsed[cents -coins[j]] + 1;
				newCoin = coins[j];
			}
		}
		coinsUsed[cents] = minCoins;
		lastCoin[cents]  = newCoin;
	}
}