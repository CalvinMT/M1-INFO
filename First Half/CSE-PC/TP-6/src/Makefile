EXEC=test test-format test-rand
LDFLAGS=-Wall -Werror -g

all: $(EXEC)

%.o: %.c
	$(CC) $(LDFLAGS) -c $<

test: test.o stdes.o
	$(CC) -o $@ $^ -lm

test-format: test-format.o stdes.o
	$(CC) -o $@ $^ -lm

test-rand: test-rand.o stdes.o
	$(CC) -o $@ $^ -lm

clean:
	rm $(EXEC) *.o rand-file.txt
