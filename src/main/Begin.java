package main;
import pckThread.MThread;

class Begin
{
	public static void main(String args[])
	{
		int i = 0;
		Thread [] arrFilosofos = new Thread[5];

		//Instancia 5 filosofos
		for (i = 0 ; i < 5 ; i ++) 
		{
			MThread tFilo = new MThread(i);
			arrFilosofos[i] = new Thread(tFilo);
			arrFilosofos[i].start();
		}
		
	}
}