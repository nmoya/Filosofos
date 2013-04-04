package main;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import pckThread.*;


class Begin
{
	public static void main(String args[]) throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));  
		String entrada = null;
		String numeroThreads = null;
		int i = 0;

		System.out.println("1- Com DeadLock para N threads");
		System.out.println("2- Sem Deadlock e sem Inanicao para N threads. Usando Bakery");
		System.out.println("3- Sem Deadlock e com Inanicao para N threads. Usando Semaforos JAVA");
		System.out.println("Digite o algoritmo a ser executado: ");
		entrada = in.readLine();
		System.out.println("Voce digitou: " + entrada);  

		System.out.println("Digite o Numero de Threds: ");
		numeroThreads = in.readLine();
		Global.nThreads = Integer.valueOf(numeroThreads);			


		Thread [] arrFilosofos = new Thread[Global.nThreads];
		if (entrada.equals("1"))
		{
			Global.iteracoes = Global.iteracoes * 100;
			//Instancia N filosofos
			for (i = 0 ; i < Global.nThreads ; i ++) 
			{
				FilosofosDeadLock tFilo = new FilosofosDeadLock(i);
				arrFilosofos[i] = new Thread(tFilo);
				arrFilosofos[i].start();
			}

		}
		//Com o Bakery
		if (entrada.equals("2"))
		{
			Global.aceleradorInvertido = 1;
			//Instancia N filosofos
			for (i = 0 ; i < Global.nThreads ; i ++) 
			{
				FilosofosSemDeadlockEInanicao tFilo = new FilosofosSemDeadlockEInanicao(i);
				arrFilosofos[i] = new Thread(tFilo);
				arrFilosofos[i].start();
			}
		}
		//Com semáforo do java
		if (entrada.equals("3"))
		{
			//Instancia N filosofos
			for (i = 0 ; i < Global.nThreads ; i ++) 
			{
				FilosofosSemaforo tFilo = new FilosofosSemaforo(i);
				arrFilosofos[i] = new Thread(tFilo);
				arrFilosofos[i].start();
			}
		}


	}
}