#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>

/**
 * AUTEURS :
 *	Calvin MASSONNET
 *	Mathis PERRIER
 */

	#define NB_CLIENT_MAX 10
	#define NB_THREAD_CLIENT_MAX 100

 /**
	* Structure de donnée
	*/
typedef struct {
	pthread_mutex_t* lock;
	//** car tableau de pointeur (tampon)
	pthread_cond_t** condClient;
	pthread_cond_t* condBarbier;
	//Indice du client dans le tableau
	int clientCourant;
	//Nombre de client dans la boutique
	int nbClient;
	//0 si libre / 1 si occupe
	int occupe;
} barberShop_t;

/**
 * Initialise la structure
 * /!\ LA STRUCTURE DOIT ÊTRE ALLOUÉE /!\
 */
void init(barberShop_t* bS) {
	bS->lock = malloc(sizeof(pthread_mutex_t));
	pthread_mutex_init(bS->lock, NULL);

	bS->condClient = malloc(sizeof(pthread_cond_t) * NB_CLIENT_MAX);
	for (int i = 0 ; i < NB_CLIENT_MAX; i++) {
		bS->condClient[i] = malloc(sizeof(pthread_cond_t));
		pthread_cond_init(bS->condClient[i], NULL);
	}

	bS->condBarbier = malloc(sizeof(pthread_cond_t));
	pthread_cond_init(bS->condBarbier, NULL);

	bS->clientCourant = 0;
	bS->nbClient = 0;
	bS->occupe = 0;
}

/**
 * Fonction du barbier
 * bs - barberShop_t - Structure contenant les informations
 */
void* barbier(void* bs) {
	barberShop_t* bS = (barberShop_t*) bs;

	pthread_t tid = pthread_self();

	//Randomisation de la seed
	srand((int) pthread_self());

	while (1) {
		pthread_mutex_lock(bS->lock);

		//Si il n'y a pas de client en attente, il s'endort et n'est pas occupé
		if (bS->nbClient == 0) {
			bS->occupe = 0;
			printf("Je suis barbier, je m'endors\n");
			pthread_cond_wait(bS->condBarbier, bS->lock);
			//Il est reveillé donc il est occupé
			bS->occupe = 1;
		}

		//Il est reveillé, il y a un client
		//Le client sort de la file d'attente
		bS->nbClient--;
		printf("Je rase le client sur la chaise n°%d, il reste %d places\n", bS->clientCourant, NB_CLIENT_MAX-bS->nbClient);

		//Le barbier se prépare à appeler le prochain client
		bS->clientCourant++;
		bS->clientCourant %= NB_CLIENT_MAX;

		pthread_mutex_unlock(bS->lock);

		//Il rase le client
		usleep(rand() / (RAND_MAX * 1.0) * 1000000.0);

		//S'il y a d'autre client, il les appelle
		if (bS->nbClient > 0) {
			//Appelle le prochain
			pthread_cond_signal(bS->condClient[bS->clientCourant]);
		}
	}

	//Jamais atteint, mais pour que ce soit cohérent avec le type de retour
	return (void*) tid;
}

/**
 * Fonction du client
 * bs - barberShop_t - Structure contenant les informations
 */
void* client(void* bs) {
	barberShop_t* bS = (barberShop_t*) bs;

	pthread_t tid = pthread_self();

	pthread_mutex_lock(bS->lock);

	//Calcul de la place du client
	//On peut voir ça comme une salle d'attente, avec des chaises
	int indiceDeLaPlace = (bS->clientCourant+bS->nbClient)%NB_CLIENT_MAX;

	//Si la boutique est vide et que le barbier n'est pas occupé
	if (bS->nbClient == 0 && bS->occupe == 0) {
		bS->nbClient++;
		printf("Je suis seul, je fais du bruit :)\n");
		pthread_cond_signal(bS->condBarbier);
	} else {
		//S'il y a suffisamment de chaise pour s'assoir
		if (bS->nbClient < NB_CLIENT_MAX) {
			bS->nbClient++;
			printf("Youpi, j'ai de la place, il reste %d places :D\n", NB_CLIENT_MAX-bS->nbClient);
			pthread_cond_wait(bS->condClient[indiceDeLaPlace], bS->lock);
		} else {
			//La boutique est pleine
			printf("Arf, pas de place, je m'en vais :(\n");
		}
	}

	pthread_mutex_unlock(bS->lock);

	return (void*) tid;
}

/**
 * main
 * premier argument : nombre de client avant l'arrivée du barbier
 * second argument : nombre de client après l'arrivée du barbier
 */
int main(int argc, char const *argv[]) {
	barberShop_t* bS = malloc(sizeof(barberShop_t));
	pthread_t* tidBarbier;
  pthread_t* tidsClients;

	int nbClientAvantBarbier;
	int nbClientApresBarbier;

	init(bS);

	tidBarbier = malloc(sizeof(pthread_t));
	tidsClients = malloc(NB_THREAD_CLIENT_MAX*sizeof(pthread_t));

	if (argc != 3){
    fprintf(stderr, "usage : %s nbClientAvantBarbier nbClientAprèsBarbier\n", argv[0]) ;
    exit (-1) ;
  }


	nbClientAvantBarbier = atoi(argv[1]);
	nbClientApresBarbier  = atoi(argv[2]);

	int i = 0;
	//Quelques clients sont déjà présents à l'arrivé du barier
	for (; i < nbClientAvantBarbier; i++){
		pthread_create(&tidsClients[i], NULL, client, bS);
	}

	//Le barbier arrive en retard
	pthread_create (tidBarbier, NULL, barbier, bS);

	//Le reste des clients pour le reste de la journée
	for (; i < nbClientApresBarbier; i++){
		pthread_create(&tidsClients[i], NULL, client, bS);
		usleep(rand() / (RAND_MAX * 1.0) * 500000.0);
	}

	/* Wait threads */
	for (; i > 0; i--){
		pthread_join(tidsClients[i], NULL) ;
	}

	if (bS->nbClient == 0) {
		printf("OK\n");
	} else {
		printf("PAS OK\n");
	}

	free(tidBarbier);
	free(tidsClients);

	//Libération de la mémoire de notre structure
	free(bS->condBarbier);
	for (int i = 0; i < NB_CLIENT_MAX; i++) {
		free(bS->condClient[i]);
	}
	free(bS->condClient);
	free(bS->lock);
	free(bS);

	return EXIT_SUCCESS;
}
