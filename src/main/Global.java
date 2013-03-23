package main;

public class Global {
	
	public static int[] Garfos;
	public static int nThreads = 5;
	
	public Global ()
	{
		this.Garfos = new int[this.nThreads];
		int i = 0;
		for (i = 0 ; i< this.Garfos.length ; i++)
			this.Garfos[i] = -1;
	}
	public int getGarfo (int pos)
	{
		return this.Garfos[pos];
	}
	public void setGarfo (int pos, int val)
	{
		this.Garfos[pos] = val;
	}
	public int length()
	{
		return this.Garfos.length;
	}
}
