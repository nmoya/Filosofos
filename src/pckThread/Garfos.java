package pckThread;

public class Garfos {
	public static int[] Garfos;
	
	public Garfos (int nFilosofos)
	{
		this.Garfos = new int[nFilosofos];
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
