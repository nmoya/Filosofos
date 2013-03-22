package pckThread;
import java.util.Random;

public class MThread implements Runnable 
{
	private int id;

	private static int nFilosofos = 5;
	static int[] Garfos = new int[nFilosofos];

	public MThread(int id) 
	{
		this.id = id;
	}
	public int getID() { return this.id; }
	public void run() 
	{
		Random randint = new Random(42);
		Bakery semaforo = new Bakery(nFilosofos);
		int count = 0;
		int posGarfo = 0;
		System.out.println("MyThread starting.");
		try 
		{
			do
			{
				posGarfo = randint.nextInt()%nFilosofos;
				Thread.sleep(randint.nextInt()%500);
				if (randint.nextInt()%2 == 0)
				{
					Garfos[posGarfo] = 1;
					System.out.println("Thread " + this.getID() + "pegou o garfo na pos " + posGarfo);
				}

				else
				{
					Garfos[posGarfo] = 0;
					System.out.println("Thread " + this.getID() + "largou o garfo na pos " + posGarfo);
				}
				count++;
			} while (count < 10);
		} catch (InterruptedException exc) 
		{
			System.out.println("MyThread interrupted.");
		}
		System.out.println("MyThread terminating.");
	}
}