#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>

/**
 * AUTHOR:
 *	Calvin MASSONNET
 */

#define EMPTY_TILE 0
#define READER_TILE 1
#define WRITER_TILE 2

int NB_READERS;
int NB_WRITERS;

/**
 * structure
 */
typedef struct {
	pthread_mutex_t* lock;
	pthread_cond_t* condReader;
	pthread_cond_t* condWriter;
	int bufferSize;
	int* buffer;
	int currentBufferStart;
	int currentBufferEnd;
	int currentNbReaders;
	int iterations;
	int data;
} rw_t;

/**
 * initialise_readers_writers
 */
void initialise_readers_writers(rw_t* rw) {
	rw->lock = malloc(sizeof(pthread_mutex_t));
	rw->condReader = malloc(sizeof(pthread_cond_t));
	rw->condWriter = malloc(sizeof(pthread_cond_t));
	rw->bufferSize = NB_READERS + NB_WRITERS;
	rw->buffer = malloc(sizeof(int) * rw->bufferSize);
	for (int i = 0; i < rw->bufferSize; i++) {
		rw->buffer[i] = EMPTY_TILE;
	}
	rw->currentBufferStart = 0;
	rw->currentBufferEnd = 0;
	rw->currentNbReaders = 0;
	rw->iterations = 0;
	rw->data = 0;
}

void show_buffer(rw_t* rw) {
	printf("[");
	for (int i = 0; i < rw->bufferSize; i++) {
		if (i == rw->currentBufferStart) {
			printf("{");
		}
		else if ((i == rw->currentBufferEnd)
			  || (i > rw->currentBufferStart  &&  i < rw->currentBufferEnd)
			  || (rw->currentBufferStart > rw->currentBufferEnd  &&  i > rw->currentBufferStart)
			  || (rw->currentBufferStart > rw->currentBufferEnd  &&  i < rw->currentBufferEnd)) {
			printf(".");
		}
		else {
			printf("[");
		}
		switch (rw->buffer[i]) {
			case EMPTY_TILE:
				printf(" ");
				break;
			case READER_TILE:
				printf("r");
				break;
			case WRITER_TILE:
				printf("w");
				break;
			default:
				printf("?");
				break;
		}
		if (i == rw->currentBufferEnd) {
			printf("}");
		}
		else if ((i == rw->currentBufferStart)
			  || (i > rw->currentBufferStart  &&  i < rw->currentBufferEnd)
			  || (rw->currentBufferStart > rw->currentBufferEnd  &&  i > rw->currentBufferStart)
			  || (rw->currentBufferStart > rw->currentBufferEnd  &&  i < rw->currentBufferEnd)) {
			printf(".");
		}
		else {
			printf("]");
		}
	}
	printf("]\n");
}

/**
 * occupied
 */
void occupied(int scale) {
	usleep(rand() / (RAND_MAX * 1.0) * 500000.0 * scale);
}

/**
 * start_read
 */
void start_read(rw_t* rw) {
	pthread_mutex_lock(rw->lock);
	if (rw->buffer[rw->currentBufferEnd] == EMPTY_TILE) {
		rw->buffer[rw->currentBufferEnd] = READER_TILE;
	}
	else if (rw->buffer[rw->currentBufferEnd] != READER_TILE) {
		// While buffer is full
		while (rw->buffer[(rw->currentBufferEnd + 1) % rw->bufferSize] != EMPTY_TILE) {
			pthread_cond_wait(rw->condReader, rw->lock);
		}
		rw->currentBufferEnd = (rw->currentBufferEnd + 1) % rw->bufferSize;
		rw->buffer[rw->currentBufferEnd] = READER_TILE;
	}
	int ticket = rw->currentBufferEnd;
	printf("Reader %x : waiting ticket %d...\n", (int)pthread_self(), ticket);
	show_buffer(rw);
	while (ticket != rw->currentBufferStart) {
		pthread_cond_wait(rw->condReader, rw->lock);
	}
	rw->currentNbReaders++;
	pthread_cond_broadcast(rw->condReader);
	// Release lock for others to read aswell
	pthread_mutex_unlock(rw->lock);
}

/**
 * end_read
 */
void end_read(rw_t* rw) {
	pthread_mutex_lock(rw->lock);
	rw->currentNbReaders--;
	if (rw->currentNbReaders == 0) {
		rw->buffer[rw->currentBufferStart] = EMPTY_TILE;
		if (rw->currentBufferStart != rw->currentBufferEnd) {
			rw->currentBufferStart = (rw->currentBufferStart + 1) % rw->bufferSize;
		}
		pthread_cond_broadcast(rw->condReader);
		pthread_cond_broadcast(rw->condWriter);
		printf("Ticket %d\n", rw->currentBufferStart);
		show_buffer(rw);
	}
	pthread_mutex_unlock(rw->lock);
}

/**
 * reader
 */
void *reader(void* rw_structure) {
	rw_t* rw = (rw_t*) rw_structure;
	int value;
	int needToRead = rw->iterations;
	srandom((int) pthread_self());
	while (needToRead > 0) {
		occupied(2);
		start_read(rw);
		printf("Reader %x : reading...\n", (int) pthread_self());
		value = rw->data;
		occupied(1);
		printf("Reader %x : ", (int)pthread_self());
		if (value != rw->data) {
			printf("DATA CHANGED WHILE READING!!!\n");
		}
		else {
			printf("finished reading\n");
		}
		end_read(rw),
		needToRead--;
	}
	pthread_exit(0);
}

/**
 * start_write
 */
void start_write(rw_t* rw) {
	pthread_mutex_lock(rw->lock);
	if (rw->buffer[rw->currentBufferEnd] != EMPTY_TILE) {
		// While buffer is full
		while (rw->buffer[(rw->currentBufferEnd + 1) % rw->bufferSize] != EMPTY_TILE) {
			pthread_cond_wait(rw->condWriter, rw->lock);
		}
		rw->currentBufferEnd = (rw->currentBufferEnd + 1) % rw->bufferSize;
	}
	rw->buffer[rw->currentBufferEnd] = WRITER_TILE;
	int ticket = rw->currentBufferEnd;
	printf("Writer %x : waiting ticket %d...\n", (int)pthread_self(), ticket);
	show_buffer(rw);
	while (ticket != rw->currentBufferStart) {
		pthread_cond_wait(rw->condWriter, rw->lock);
	}
	// Keep lock so no one can read nor write
}

/**
 * end_write
 */
void end_write(rw_t* rw) {
	rw->buffer[rw->currentBufferStart] = EMPTY_TILE;
	if (rw->currentBufferStart != rw->currentBufferEnd) {
		rw->currentBufferStart = (rw->currentBufferStart + 1) % rw->bufferSize;
	}
	printf("Ticket %d\n", rw->currentBufferStart);
	show_buffer(rw);
	pthread_cond_broadcast(rw->condReader);
	pthread_cond_broadcast(rw->condWriter);
	pthread_mutex_unlock(rw->lock);
}

/**
 * writer
 */
void *writer(void* rw_structure) {
	rw_t* rw = (rw_t*) rw_structure;
	int value;
	int needToWrite = rw->iterations;
	srandom((int)pthread_self());
	while (needToWrite) {
		occupied(2);
		start_write(rw);
		printf("Writer %x : writing...\n", (int)pthread_self());
		value = random();
		rw->data = value;
		occupied(1);
		printf("Writer %x : ", (int)pthread_self());
		if (value != rw->data) {
			printf("DATA CHANGED WHILE WRITING!!!\n");
		}
		else {
			printf("finished writing\n");
		}
		end_write(rw),
		needToWrite--;
	}
	pthread_exit(0);
}

/**
 * create_readers_writers
 */
void create_readers_writers(rw_t* rw, pthread_t* threads) {
	printf("Ticket %d\n", rw->currentBufferStart);
	show_buffer(rw);
	pthread_t* currentThread = threads;
	for (int i = 0; i < NB_READERS; i++) {
		pthread_create(currentThread, NULL, reader, rw);
		currentThread++;
	}
	for (int i = 0; i < NB_WRITERS; i++) {
		pthread_create(currentThread, NULL, writer, rw);
		currentThread++;
	}
}

/**
 * wait_readers_writers
 */
void wait_readers_writers(rw_t* rw, pthread_t* threads) {
	for (int i = 0; i < (NB_READERS + NB_WRITERS); i++) {
		pthread_join(threads[i], NULL);
	}
	printf("All threads ended\n");
}

/**
 * free_readers_writers
 */
void free_readers_writers(rw_t* rw) {
	free(rw->buffer);
	free(rw->condWriter);
	free(rw->condReader);
	free(rw->lock);
	free(rw);
}

/**
 * main
 */
int main(int argc, char const* argv[]) {
	if (argc < 4) {
		fprintf(stderr, "%s <nb_readers> <nb_writers> <nb_iterations>\n", argv[0]);
		exit(EXIT_FAILURE);
	}

	if (atoi(argv[1]) < 0 || atoi(argv[2]) < 0 || atoi(argv[3]) < 0) {
		fprintf(stderr, "<nb_readers>, <nb_writers> and <nb_iterations> must be equal or above 0 : %s %s %s\n", argv[1], argv[2], argv[3]);
		exit(EXIT_FAILURE);
	}

	NB_READERS = atoi(argv[1]);
	NB_WRITERS = atoi(argv[2]);

	rw_t* rw = malloc(sizeof(rw_t));
	initialise_readers_writers(rw);
	rw->iterations = atoi(argv[3]);

	pthread_t* threads = malloc(sizeof(pthread_t) * (NB_READERS + NB_WRITERS));
	create_readers_writers(rw, threads);

	wait_readers_writers(rw, threads);

	free_readers_writers(rw);
	free(threads);

	return EXIT_SUCCESS;
}
