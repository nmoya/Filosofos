package pckThread;

import java.util.Random;
import main.Global;


public class FilosofosDeadLock implements Runnable 
{
	private int nFilosofos = Global.nThreads;
	Bakery semaforo = new Bakery(Global.nThreads);
	private int id;
	


	public FilosofosDeadLock(int id) 
	{
		this.id = id;
	}
	public int getID() { return this.id; }
	public void run() 
	{
		Global.criarFilosofos();
		Random randint = new Random(42);
		int posGarfo = this.getID();
		this.info("iniciado");
		try 
		{
			int count = 0;
			while(count <= Global.iteracoes)
			//while(true)
			{
				//Quem comeu ou nao comeu, volta a meditar.
				//this.info("Meditando");
				Thread.sleep(Math.abs(randint.nextInt()%500) * Global.aceleradorInvertido);
				
				//Regi�o Cr�tica para pegar o garfo a direita
				
				/*synchronized (this.Globais) {				}*/
				this.semaforo.lock(this.getID());
				if (Global.getGarfo(posGarfo) == -1)
				{
					Global.setGarfo(posGarfo, this.getID());
					//this.info("Estou segurando o garfo direito");
				}	
				this.semaforo.unlock(this.getID());

				
				//Regi�o Cr�tica para pegar o garfo a esquerda
				this.semaforo.lock(this.getID());
				if (Global.getGarfo((posGarfo+1)%nFilosofos) == -1)
				{
					Global.setGarfo((posGarfo+1)%nFilosofos, this.getID());
					//this.info("Estou segurando o garfo esquerdo");
				}
				this.semaforo.unlock(this.getID());

				
				//Se voc� tem os dois garfos, coma.
				if (Global.getGarfo(posGarfo) == this.getID() && Global.getGarfo((posGarfo+1)%nFilosofos) == this.getID())
				{
					this.info("Comendo");
					Thread.sleep(Math.abs(randint.nextInt()%500) * Global.aceleradorInvertido);
					Global.fazRefeicao(this.getID());
					//Regi�o Cr�tica para soltar ambos os garfos
					this.semaforo.lock(this.getID());
					//this.confereGarfos();
					Global.setGarfo(posGarfo, -1); 
					Global.setGarfo((posGarfo+1)%nFilosofos, -1);
					this.info("Parei de comer");
					this.semaforo.unlock(this.getID());
					//Saida da regi�o cr�tica para soltar os garfos.
				}

				count++;
			}
			//Global.mostraRefeicoes();
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
		for (i = 0; i< Global.length() ; i++)
		{
			if (Global.getGarfo(i) != -1) //Quantos garfos estao ocupados?
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
		for (i = 0; i< Global.length() ; i++)
		{
			this.info(Global.getGarfo(i) + " ");
		}
	}
}