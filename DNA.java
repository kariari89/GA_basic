package anyEquationSolver;

import java.util.Random;

import javax.swing.plaf.synth.SynthScrollBarUI;

import java.math.*;


public class DNA {

	// The number of genes the DNA contains
	private double[] genes;

	/* This give us a number based on the target that let's us know how the DNA
	 * fits to our problem -> target*/
	private double score[] = new double[Main.target.length];

	// ???
	private double hWeight;

	// j'ai oublié!!!!
	private static double total;


	public DNA(){}

	public DNA(int size)
	{
		this.genes = new double[size];

		for (int i = 0; i < size; i++)
		{
			this.genes[i] = newDouble();
		}
	}

	private double newDouble()
	{
		Random rn = new Random();
		double c = rn.nextFloat()*10;

		return c;
	}

	public void fitnessCalc (double target[])
	{
		int score[] = {0, 0, 0};
		for(int j = 0; j <score.length;j++)
		{
			for(int i = 0; i < genes.length; i++)
			{
				score[j] += genes[i]*Main.liste[j][i];
			}

			if(Math.abs(target[j]-score[j]) <= 1)
				this.score[j] = 1;
			else
				this.score[j] = ((double)1)/(Math.abs(target[j]-score[j]));

		}


		boolean tmp = true;

		for (int i = 0; i < score.length; i++)
			tmp = tmp && (this.score[i] > 0);

		if(tmp)
		{
			for (int i = 0; i < score.length; i++)
				total += this.score[i];
			hWeight = total;
		}

		else
			hWeight = 0;

	}

	public String getGenesPhrase()
	{
		String tmp = "";
		for(int i = 0; i<genes.length; i++)
		{
			tmp += " " + genes[i];
		}
		return tmp;
	}

	public DNA crossover(DNA partner)
	{
		DNA child = new DNA(this.genes.length);

		Random rn = new Random();


		for(int i = 0; i < this.genes.length; i++)
		{
			int midpoint = rn.nextInt((this.genes.length));
			if(i > midpoint)
					child.genes[i] = this.genes[i];
			else
					child.genes[i] = partner.genes[i];
		}

		return child;

	}

	public void mutate(double mutationRate)
	{
		Random rn = new Random();

		for (int i = 0; i < this.genes.length; i++)
		{
			if (rn.nextFloat() < mutationRate)
				this.genes[i] = newDouble();
		}
	}

	double[] getScore(){return score;}
	public static void setTotal(int t){total = t;}

	public double gethWeight() {
		return hWeight;
	}
}
