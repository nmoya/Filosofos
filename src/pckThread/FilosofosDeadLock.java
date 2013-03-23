package pckThread;

import java.util.Random;
import main.Global;


public class FilosofosDeadLock implements Runnable 
{
	static Global Globais = new Global();
	private int nFilosofos = Globais.nThreads;
	Bakery semaforo = new Bakery(Globais.nThreads);
	private int id;
	private int aceleradorInvertido = 5;
	


	public FilosofosDeadLock(int id) 
	{
		this.id = id;
	}
	public int getID() { return this.id; }
	public void run() 
	{
		Random randint = new Random(42);
		int count = 0;
		int posGarfo = 0;
		this.info("iniciado");
		try 
		{
			while(count <= 100)
			{
				//Quem comeu ou n‹o comeu, volta a meditar.
				this.info("Meditando");
				Thread.sleep(Math.abs(randint.nextInt()%500) * this.aceleradorInvertido);
				
				//Regi‹o Cr’tica para pegar o garfo a direita
				this.semaforo.lock(this.getID());
				if (this.Globais.getGarfo(posGarfo) == -1)
				{
					this.Globais.setGarfo(posGarfo, this.getID());
				}
				this.semaforo.unlock(this.getID());

				
				//Regi‹o Cr’tica para pegar o garfo a esquerda
				this.semaforo.lock(this.getID());
				if (this.Globais.getGarfo((posGarfo+1)%nFilosofos) == -1)
				{
					this.Globais.setGarfo((posGarfo+1)%nFilosofos, this.getID());
				}
				this.semaforo.unlock(this.getID());

				
				//Se voc tem os dois garfos, coma.
				if (this.Globais.getGarfo(posGarfo) == this.getID() && this.Globais.getGarfo((posGarfo+1)%nFilosofos) == this.getID())
				{
					this.info("Comendo");
					Thread.sleep(Math.abs(randint.nextInt()%500) * this.aceleradorInvertido);
					
					//Regi‹o Cr’tica para soltar ambos os garfos
					this.semaforo.lock(this.getID());
					this.confereGarfos();
					this.Globais.setGarfo(posGarfo, -1); 
					this.Globais.setGarfo((posGarfo+1)%nFilosofos, -1);
					this.semaforo.unlock(this.getID());
					//Saida da regi‹o cr’tica para soltar os garfos.
				}

				count++;
			}


		} catch (InterruptedException exc) 
		{
			this.info("Interrompido");
		}
		this.info("FIM");
	}
	public void info (String s)
	{
		System.out.println("Filosofo " + this.getID() + ": " + s);
	}
	public void confereGarfos ()
	{
		int i = 0;
		int contador = 0 ;
		for (i = 0; i< this.Globais.length() ; i++)
		{
			if (this.Globais.getGarfo(i) != -1) //Quantos garfos estao ocupados?
			{
				contador++;
			}
		}
		if (contador <= 4)
			this.info("Antes de soltar meus garfos, estava OK");
		else
		{
			this.info("Opa, tem um erro aqui");
			this.imprimeGarfos();
		}
	}
	public void imprimeGarfos ()
	{
		int i = 0;
		for (i = 0; i< this.Globais.length() ; i++)
		{
			this.info(this.Globais.getGarfo(i) + " ");
		}
	}
}