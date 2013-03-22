#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>

#define MAXTHREADS 5

sem_t palito[5];
sem_t saleiro;

sem_t mutex;
int alimentados = 0;

/*
 TEMPOPAUSA 1 = COM PAUSA
 TEMPOPAUSA 2 = SEM PAUSA
 */
#define TEMPOPAUSA 0

void * filosofo (int tid)
{
	int temporizador;
    while (1)
	{
		printf("Filosofo %d meditou\n", tid);
		//Meditar
		temporizador = rand()%2;
		sleep(temporizador * TEMPOPAUSA);
		
		sem_wait( &palito[tid] );	 				    //Palito esquerdo
		sem_wait( &palito[ (tid+1) % MAXTHREADS ] );    //Palito direito
		
		
		sem_wait(&mutex);
		alimentados+=1;
		sem_post(&mutex);
		//Comer
		temporizador = rand()%2;
		sleep(temporizador * TEMPOPAUSA);
		
		sem_post( &palito[tid] );
		sem_post( &palito[ (tid+1) % MAXTHREADS ]);
		printf("Filosofo %d comeu\n", tid);

	}
	pthread_exit (NULL) ;
}

void * filosofoSaleiro (int tid)
{
	
	int temporizador;
    while (1)
	{
		printf("Filosofo %d meditou\n", tid);		//Meditar
		temporizador = rand()%2;
		sleep(temporizador * TEMPOPAUSA);
		
		sem_wait(&saleiro); //Pega o saleiro para pegar ambos os palitos.
		
		sem_wait( &palito[tid] );					    //Palito esquerdo
		sem_wait( &palito[ (tid+1) % MAXTHREADS ] );    //Palito direito
		
		sem_post(&saleiro); //Pega ambos os palitos e devolve o saleiro.
		
		sem_wait(&mutex);
		alimentados+=1;
		sem_post(&mutex);
		
		//Comer
		temporizador = rand()%2;
		sleep(temporizador * TEMPOPAUSA);
		
		sem_post( &palito[tid] );
		sem_post( &palito[ (tid+1) % MAXTHREADS ]);
		
		printf("Filosofo %d comeu\n", tid);
		
	}
	pthread_exit (NULL) ;
}

void * filosofoMax (int tid)
{	
	int temporizador;
    while (1)
	{
		printf("Filosofo %d meditou\n", tid); //Meditar
		temporizador = rand()%2;
		sleep(temporizador * TEMPOPAUSA);
		if (tid == 1)
		{
			sem_wait( &palito[ (tid+1) % MAXTHREADS ] );					    //Palito esquerdo canhoto
			sem_wait( &palito[ tid ] );    //Palito direito canhoto
		}
		else {
			sem_wait( &palito[ tid ] );					    //Palito esquerdo
			sem_wait( &palito[ (tid+1) % MAXTHREADS  ] );    //Palito direito

		}

		sem_wait(&mutex);
		alimentados+=1;
		sem_post(&mutex);
		
		//Comer
		temporizador = rand()%2;
		sleep(temporizador * TEMPOPAUSA);
		
		sem_post( &palito[tid] );
		sem_post( &palito[ (tid+1) % MAXTHREADS ]);
		
		printf("Filosofo %d comeu\n", tid);
		
		printf("Filosofo %d comeu\n", tid);
		
	}
	pthread_exit (NULL) ;
}



int main (int argc,const char * argv[])
{
	if (argv[1][0] == '\0')
	{
		printf("Digite:\n");
		printf("1- Problema dos filosofos\n");
		printf("2- Solucao do Saleiro\n");
		printf("3- Max. Paralelismo\n");
		return 0;
	}	
	int status, i;
	pthread_t thread [MAXTHREADS];
	pthread_attr_t attr ;
    pthread_attr_init (&attr);
	pthread_attr_setdetachstate (&attr, PTHREAD_CREATE_JOINABLE);

	for (i=0 ; i< MAXTHREADS ; i++)
		sem_init (&palito[i], 0, 1);
	sem_init (&saleiro, 0, 1);
	sem_init (&mutex, 0, 1);
	printf("Inicializou tudo\n");
	
	if (argv[1][0] == '1')
	{
		printf("Argv: %s\n", argv[1]);
		printf("Filosofos Dead Lock\n");
		
		for(i=0; i<MAXTHREADS; i++)
		{
			status = pthread_create (&thread[i], &attr, filosofo, i);
			if (status)
			{
				perror ("pthread_create") ;
				exit (1) ;
			}
			printf("Criou thread %d\n", i);
		}
	}
	else if (argv[1][0] == '2')
	{
		printf("Argv: %s\n", argv[1]);
		printf("Filosofos Saleiro\n");
		
		for(i=0; i<MAXTHREADS; i++)
		{
			status = pthread_create (&thread[i], &attr, filosofoSaleiro, i);
			printf("Criou thread %d\n", i);
			if (status)
			{
				perror ("pthread_create") ;
				exit (1) ;
			}
		}

	}
	
	else if (argv[1][0] == '3')
	{
		printf("Argv: %s\n", argv[1]);
		printf("Filosofos canhoto\n");
		for(i=0; i<MAXTHREADS; i++)
		{
			status = pthread_create (&thread[i], &attr, filosofoMax, i);
			printf("Criou thread %d\n", i);
			if (status)
			{
				perror ("pthread_create") ;
				exit (1) ;
			}
		}

	}
	sleep(4);
	printf("\n\n\n\n\nAlimentados: %d\n", alimentados);
	/*
	for (i=0; i<MAXTHREADS; i++)
	{
		
		status = pthread_join (thread[i], NULL);
		if (status)
		{
			perror ("pthread_join") ;
			exit (1);
		}
	}*/

	
	
}
