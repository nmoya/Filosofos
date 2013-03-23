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
		System.out.println("1- DeadLock");
		System.out.println("2- Sem DeadLock");
		System.out.println("3- Sem inanição");
		System.out.println("4- Sem inanição para N");
		//entrada = in.readLine();
		entrada = "2";  
		System.out.println("Você digitou: " + entrada);  
		
		Global G = new Global();
		int i = 0;
		int nThreads = G.nThreads;
		Thread [] arrFilosofos = new Thread[nThreads];
		
		if (entrada.equals("1"))
		{
			//Instancia N filosofos
			for (i = 0 ; i < nThreads ; i ++) 
			{
				FilosofosDeadLock tFilo = new FilosofosDeadLock(i);
				arrFilosofos[i] = new Thread(tFilo);
				arrFilosofos[i].start();
			}
		}
		if (entrada.equals("2"))
		{
			//Instancia N filosofos
			for (i = 0 ; i < nThreads ; i ++) 
			{
				FilosofosSemDeadlock tFilo = new FilosofosSemDeadlock(i);
				arrFilosofos[i] = new Thread(tFilo);
				arrFilosofos[i].start();
			}
		}
		
		
	}
}