#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <stdbool.h>
#include <sys/time.h>
#include <math.h>

typedef struct customer customer;
struct customer {
	struct timeval arrivalTime;
	customer *next;
};

customer *qHead, *qTail;
unsigned qLength;
pthread_mutex_t qMutex;

void *thread1(void *p) {
	customer *newCustomer;
}

void *thread2(void *p) {
	customer *aCustomer;
	struct timeval tv;
	double waitingTime;

	gettimeofday(&tv, NULL);
	waitingTime = tv.tv_sec - aCustomer->arrivalTime.tv_sec +
	(tv.tv_usec - aCustomer->arrivalTime.tv_usec) / 1000000.0;
	free(aCustomer);
}

void onlineAvgStd(double *avg, double *std, double array[], int size) {
  double sumX, sumX2;
  int i;
  
  sumX = sumX2 = 0.0;
  for (i = 0; i < size; i++) {
    sumX += array[i];
    sumX2 += array[i] * array[i];
  }
  *avg = sumX / size;
  *std = sqrt( (sumX2 - (*avg) * (*avg) * size) / (size - 1) );
}

void printStats(int arrival, int waiting, int service, int qLen, int servUtil) {
	printf("inter arrival: %d, waiting: %d, service: %d, queue length: %d, server utilization: %d",
		arrival, waiting, service, qLen, servUtil);
}

// Go to sleep for 0.005 seconds. Used for thread 3.
int goToSleepThread3() {
	struct timespec sleepTime;
	sleepTime.tv_sec = 0;
	sleepTime.tv_nsec = 5000000L;
	return nanosleep(&sleepTime, NULL);
}

double rndExp(struct drand48_data *randData, double lambda) {
	double tmp;
	drand48_r(randData, &tmp);
	return -log(1.0 - tmp) / lambda;
}

void *threadXYZ(void *p) {
	struct drand48_data randData;
	struct timeval tv;
	double result;
	gettimeofday(&tv, NULL);
	//to seed the generator
	srand48_r(tv.tv_sec + tv.tv_usec, &randData);
	//to draw a number from [0, 1) uniformly and store it in "result"
	drand48_r(&randData, &result);
}

int main(int argc, char *argv[]) {
	// Default values
	int lambda = 5;
	int mu = 7;
	int numCustomer = 1000;
	int numServer = 1;

	// Get the arrival rate, service rate, number of customers and number of servers.
	// Only supports 1 server.
	char opt;
	while ((opt = getopt(argc, argv, "l:m:c:s:")) != -1) {
		switch(opt) {
			case 'l':
				lambda = atoi(optarg);
				break;
			case 'm':
				mu = atoi(optarg);
				break;
			case 'c':
				numCustomer = atoi(optarg);
				break;
			case 's':
				numServer = atoi(optarg);
				break;
			default:
				fprintf(stderr, "Something went wrong with command line arguments\n");
				return -1;
		}
	}

	if (numServer > 1) {
		printf("Currently does not support multiple servers\n");
	}

	if (lambda > mu * numServer) {
		printf("Lambda cannot be greater than mu * numServer\n");
		return -1;
	}

	// printf("l: %d, m: %d, c: %d, s: %d\n", lambda, mu, numCustomer, numServer);

	// Create 3 threads for customers, server and queue length.
	pthread_t threads[3];
	int status, i;
	for (int i = 0; i < 3; i++) {
		status = pthread_create(&threads[i], NULL, threadXYZ, (void *)&i);
		if (status != 0){
      		printf("pthread_create returned error code %d\n",status);
      		return -1;
   	 	}
	}
	return 0;
}