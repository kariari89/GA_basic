package anyEquationSolver;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;


public class Population {
	
	private DNA population[];
	public static int generations = 0;
	public boolean isFinished = false;
	static double target[];
	static double totalWeight = 0;
	private NavigableMap<Double, Integer> map;
	
	public String best = "";
	
	public Population(double target[])
	{
		Population.target = target;
		population = new DNA[Simulation.getPop()];
		
		for(int i = 0; i < this.population.length; i++)
		{
			this.population[i] = new DNA (Main.liste[0].length);
			
		}
	}
	
	public void popInitialize()
	{
		totalWeight = 0;
		DNA.setTotal(0);
		for(int i = 0; i < this.population.length; i++)
		{
			population[i].fitnessCalc(target);
			for (int j = 0; j<target.length; j++)
				totalWeight += (population[i].getScore()[j]);
			
			
		}
	}
	
	public void naturalSelection()
	{
		map = new TreeMap<Double, Integer>();
		
		for(int i = 0; i < this.population.length; i++)
		{
			double tmpScore = 0;
			
			for(int m = 0; m <target.length; m++)
				tmpScore += (this.population[i].getScore()[m]);
			
			if(tmpScore > 0)
			{
				map.put(population[i].gethWeight(), i);
			}
		}
		
		
	}
	
	public void generate()
	{
		Random rn = new Random();
		DNA[] tmp = new DNA[population.length];
		
		for (int i = 0; i < this.population.length; i++)
		{
			double a = rn.nextDouble()*(totalWeight);
			double b = rn.nextDouble()*(totalWeight);
			
			DNA partnerA = population[ map.ceilingEntry(a).getValue()];
			DNA partnerB = population[ map.ceilingEntry(b).getValue()];
			
			DNA child = partnerA.crossover(partnerB);
			if( (Simulation.getMutationRate() >= 0.002) && (Simulation.getMutationRate() % 100) == 0)
				Simulation.setMutationRate(Simulation.getMutationRate() - 0.001);
			child.mutate(Simulation.getMutationRate());

			tmp[i] = child;				
			
		}
		population = tmp;
		generations++;
	}

	public void evaluate()
	{
		int index = 0;
		double recordScore[]= {0, 0, 0};
		
		
		for (int i = 0; i < this.population.length; i++)
		{
			this.population[i].fitnessCalc(target);
			boolean tmp = true;
			for(int j = 0; j<target.length; j++)
			{
				tmp = tmp &&(this.population[i].getScore()[j] > recordScore[j]);
			}
			
			if(tmp)
			{
				index = i;
				for (int j = 0; j<target.length; j++)
					recordScore[j] = this.population[i].getScore()[j];
			}
		}
		
		this.best = this.population[index].getGenesPhrase();
		
		boolean tmp = true;
		for (int i = 0; i < recordScore.length; i++)
			tmp = tmp && (( ( recordScore[i]*100 > Simulation.getPrecision()) && (recordScore[i]*100 <200 - Simulation.getPrecision())));
		
		this.isFinished = tmp;
		
	}
	
	public static int getGenerations() {
		return generations;
	}
	
	public static void setGenerations(int g) {
		generations = g;
	}

	public boolean isFinished() {
		return isFinished;
	}
	
	public String getBest() {
		return best;
	}
	
}
