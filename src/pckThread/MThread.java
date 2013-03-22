package pckThread;
import java.util.Random;


public class MThread implements Runnable 
{
	private int id;

	private static int nFilosofos = 5;
	
	//Instancia o vetor global de garfos e o sem‡foro
	static Garfos Garfos = new Garfos(nFilosofos);
	Bakery semaforo = new Bakery(nFilosofos);

	public MThread(int id) 
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
				//Regi‹o Cr’tica para pegar os garfos
				this.semaforo.lock(this.getID());
				if (this.Garfos.getGarfo(posGarfo) == -1 && this.Garfos.getGarfo((posGarfo+1)%nFilosofos) == -1)
				{
					//Pega um garfo para voc
					this.Garfos.setGarfo(posGarfo, this.getID()); 
					this.Garfos.setGarfo((posGarfo+1)%nFilosofos, this.getID());

					//Se voc pegou um garfo, sai da regi‹o cr’tica
					this.semaforo.unlock(this.getID());

					//Come
					this.info("Comendo");
					Thread.sleep(Math.abs(randint.nextInt()%500));

					//Regi‹o Cr’tica para soltar os garfos
					this.semaforo.lock(this.getID());
					this.confereGarfos();
					this.Garfos.setGarfo(posGarfo, -1); 
					this.Garfos.setGarfo((posGarfo+1)%nFilosofos, -1);
					this.semaforo.unlock(this.getID());
					//Saida da regi‹o cr’tica para soltar os garfos.
				}
				else
				{
					posGarfo++;
					if (posGarfo == nFilosofos)
						posGarfo = 0;
				}
				//Quem comeu ou n‹o comeu, volta a meditar.
				this.info("Meditando");
				Thread.sleep(Math.abs(randint.nextInt()%500));
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
		for (i = 0; i< this.Garfos.length() ; i++)
		{
			if (this.Garfos.getGarfo(i) != -1) //Quantos garfos estao ocupados?
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
		for (i = 0; i< this.Garfos.length() ; i++)
		{
			this.info(this.Garfos.getGarfo(i) + " ");
		}
	}
}