#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>
#include <limits.h>

// Huffman Node
struct Node {
	char character;
	int frequency;
	struct Node* left;
	struct Node* right;
	char code[256];
	int codeSize;
};

// Min Priority Queue - unordered array implementation
struct MinPriorityQueue {
	int size;
	struct Node** nodes;
};

// read command line arguments, return input file name and output file name in an array
char** commandLine(int argc, char* argv[]) {
	char** input = (char**)malloc(2 * sizeof(char*));
	// no input
	if (argc == 1) {
		// default file names
		char* in = "completeShakespeare.txt";
		char* out = "huffman.out";
		input[0] = in;
		input[1] = out;
		printf("inFile: %s | outFile: %s\n", input[0], input[1]);
		return input;
	}
	// missing arguments
	if (argc == 2 || argc == 3 || argc == 4 || argc > 5) {
		printf("Incorrect number of inputs\n");
		return NULL;
	}
	// full arguments
	if (argc == 5) {
		char* inOpt = argv[1];
		input[0] = argv[2];
		char* outOpt = argv[3];
		input[1] = argv[4];
		// check options
		if ((strcmp(inOpt, "-i") != 0) || (strcmp(outOpt, "-o") != 0)) {
			printf("Incorrect command line options (-i, -o)...\n");
			return NULL;
		}
	}
	return input;
}

// return an empty queue
struct MinPriorityQueue* makeQueue() {
	int size = 256;
	struct MinPriorityQueue* temp = (struct MinPriorityQueue*)malloc(size * sizeof(struct MinPriorityQueue));
	temp->size = 0;
	temp->nodes = (struct Node**)malloc(size * sizeof(struct Node*));
	return temp;
}

// returns node for char with freq == 1
struct Node* makeNode(char c) {
	struct Node* temp = (struct Node*)malloc(sizeof(struct Node));
	temp->character = c;
	temp->frequency = 1;
	temp->left = NULL;
	temp->right = NULL;
	temp->codeSize = 0;
	return temp;
}

// initial node with freq == 0
struct Node* makeStartNode(char c) {
	struct Node* temp = (struct Node*)malloc(sizeof(struct Node));
	temp->character = c;
	temp->frequency = 0;
	temp->left = NULL;
	temp->right = NULL;
	temp->codeSize = 0;
	return temp;
}

// place first node in queue
void startQueue(struct MinPriorityQueue* mpq, char c) {
	if (mpq->size == 0) {
		mpq->nodes[0] = makeStartNode(c);
		mpq->size++;
	}
}

// check if element is in queue
int elementInsideQueue(struct MinPriorityQueue* mpq, char c) {
	int i = 0;
	while (mpq->nodes[i] != NULL) {
		if (mpq->nodes[i]->character == c) {
			mpq->nodes[i]->frequency++;
			return 1;
		}
		i++;
	}
	return -1;
}

void updateQueue(struct MinPriorityQueue* mpq, char c) {
	startQueue(mpq, c);
	int u = elementInsideQueue(mpq, c);
	if (u == -1) {
		mpq->nodes[mpq->size] = makeNode(c);
		mpq->size++;
	}
}

// read file, return queue with nodes representing each unique char seen
struct MinPriorityQueue* readText(char* inFile) {
	struct MinPriorityQueue* mpq = makeQueue();
    FILE* fp = fopen(inFile,"r");
    if (fp == NULL) {
    	printf("text not readable\n");
    	return NULL;
    }
    while(1) {
    	char c = fgetc(fp);
    	updateQueue(mpq, c);
    	if (feof(fp)) {
    		break;
    	}
    }
    fclose(fp);
    return mpq;
}

// dequeues element with smallest frequency
struct Node* dequeue(struct MinPriorityQueue* mpq) {
	int minIndex = -1;
	int minFreq = INT_MAX;
	for (int i = 0; i < mpq->size; i++) {
		int f = mpq->nodes[i]->frequency;
		if (f < minFreq) {
			minFreq = f;
			minIndex = i;
		}
	}
	struct Node* min = mpq->nodes[minIndex];
	for (int i = minIndex; i < mpq->size - 1; i++) {
		mpq->nodes[i] = mpq->nodes[i + 1];
	}
	mpq->size--;
	return min;
}

void enqueueNode(struct MinPriorityQueue* mpq, struct Node* n) {
	mpq->nodes[mpq->size] = n;
	++mpq->size;
}

// huffman algorithm implementation
void huffman(struct MinPriorityQueue* mpq) {
	while (mpq->size > 1) {
		struct Node* sum = (struct Node*)malloc(sizeof(struct Node));
		struct Node* min1 = dequeue(mpq);
		struct Node* min2 = dequeue(mpq);
		sum->character = '\0';
		sum->frequency = min1->frequency + min2->frequency;
		sum->codeSize = 0;
		sum->left = min1;
		sum->right = min2;
		enqueueNode(mpq, sum);
	}
}

// fill out the codes for each Node in queue
void getCodes(struct MinPriorityQueue* mpq, struct Node* n, char codeArr[], int index) {
	if (n->right) {
		codeArr[index] = '1';
		getCodes(mpq, n->right, codeArr, index + 1);
	}
	if (n->left) {
		codeArr[index] = '0';
		getCodes(mpq, n->left, codeArr, index + 1);
	}
	if ((n->left == NULL) && (n->right == NULL)) {
	    strcpy(n->code, codeArr);
		n->codeSize = index;
		enqueueNode(mpq, n);
	}
}

char* getCharCode(char c, struct MinPriorityQueue* mpq) {
	for (int i = 0; i < mpq->size; i++) {
		if (mpq->nodes[i]->character == c) {
			return mpq->nodes[i]->code;
		}
	}
}

int getCodeSize(char c, struct MinPriorityQueue* mpq) {
	for (int i = 0; i < mpq->size; i++) {
		if (mpq->nodes[i]->character == c) {
			return mpq->nodes[i]->codeSize;
		}
	}
}

// read, substitute each char with it's code in a new output file
void writeToFile(struct MinPriorityQueue* mpq, char* outFile, char* inFile) {
	FILE* fpOut = fopen(outFile, "w");
    FILE* fp = fopen(inFile,"r");
    if (fp == NULL) {
    	printf("text not readable");
    }

    unsigned char byte = 0;
    unsigned int tempBuff = 0;
	unsigned int buffer = 0;
	unsigned int buffSize = 0;

    while(1) {
    	char c = fgetc(fp);
    	char* code = getCharCode(c, mpq);
    	int codeSize = getCodeSize(c, mpq);
    	int bitsLeft = 32;
    	tempBuff = 0;
  
    	while (*code) {
    		tempBuff = tempBuff << 1;
    		int codeBit = 0;
    		if (*code == '1') {
    			codeBit = 1;
    		}
    		else {
    			codeBit = 0;
    		}
    		tempBuff = tempBuff | codeBit;
    		bitsLeft--;
    		code++;
    	}
    	tempBuff = tempBuff << bitsLeft;
    	if (buffSize >= 8) {
    		byte = buffer >> (32 - buffSize);
    		fwrite(&byte, 1, 1, fpOut);
    		byte = 0;
    		buffer = buffer << 8;
    		buffSize = buffSize - 8;
    	}
    	tempBuff = tempBuff >> buffSize;
    	buffer = buffer | tempBuff;
    	buffSize = buffSize + codeSize;
    	if (feof(fp)) {
    		byte = buffer >> 24;
    		fwrite(&byte, 1, 1, fpOut);
    		break;
    	}
    }
    fclose(fp);
}

int main(int argc, char* argv[]) {
	char** input = commandLine(argc, argv);
	if (input == NULL) {
		return -1;
	}
	char* inFile = input[0];
	char* outFile = input[1];
	struct MinPriorityQueue* mpq = makeQueue();
	mpq = readText(inFile);
	if (mpq == NULL) {
		return -1;
	}
    huffman(mpq);
    struct Node* root = dequeue(mpq);
    char arr[256];
    int index = 0;
    struct MinPriorityQueue* codedq = makeQueue();
    getCodes(codedq, root, arr, index);
    writeToFile(codedq, outFile, inFile);
}