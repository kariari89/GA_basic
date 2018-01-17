package anyEquationSolver;

public class Simulation {

	private static int pop;
	private static int precision;
	private static double mutationRate;
	private static double target[];


	public Simulation (double[] target, int pop, double mutationRate, int precision)
	{
		this.target = target;
		Simulation.pop = pop;
		Simulation.precision = precision;
		Simulation.mutationRate = mutationRate;
	}


	public String run ()
	{

		    double calory = target[0];
				double protein = target[1];
				double lipid = target[2];

				double target[] = {calory, protein, lipid};

				Population population = new Population(target);


				while( (!population.isFinished()))
				{
					population.popInitialize();

					population.naturalSelection();
					population.generate();
					population.evaluate();

				}
				System.out.println("generations : " + Population.getGenerations());
				Population.setGenerations(0);


		return population.best;
	}

	public static int getPop() {
		return pop;
	}

	public static int getPrecision() {
		return precision;
	}

	public static double getMutationRate() {
		return mutationRate;
	}

	public static void setMutationRate (double m){
		mutationRate = m;
	}

}
