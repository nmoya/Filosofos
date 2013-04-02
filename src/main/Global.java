package main;

public class Global {
	
	public static int[] Garfos;
	public static int[] Refeicoes;
	public static int nThreads = 5;
	public static int aceleradorInvertido = 0;
	public static int iteracoes = 1000000;
	
	public static void criarFilosofos ()
	{
		Garfos = new int[nThreads];
		Refeicoes = new int[nThreads];
		int i = 0;
		for (i = 0 ; i< nThreads ; i++)
		{
			Garfos[i] = -1;
			Refeicoes[i] = 0;
		}
	}
	public static int getGarfo (int pos)
	{
		return Garfos[pos];
	}
	public static void setGarfo (int pos, int val)
	{
		Garfos[pos] = val;
	}
	public static int length()
	{
		return Garfos.length;
	}
	public static void fazRefeicao (int pos)
	{
		Refeicoes[pos]++;
	}
	public static void mostraRefeicoes ()
	{
		for (int i = 0; i<nThreads; i++) 
		{
			System.out.println("Filosofo " + i + " comeu " + Refeicoes[i]);
		}
	}
}
