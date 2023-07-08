import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GeneticAlgorithm {
    private ArrayList<Chromosome> population; // population with chromosomes
    private ArrayList<Integer> occurrences; // list with chromosomes based on fitness score
    private int n;

    GeneticAlgorithm(int n) {
        this.population = null;
        this.occurrences = null;
        this.n = n;
    }


    Chromosome run(int populationSize, double mutationPropability, int maxIter, int minFitness) {
        // Initialization of the population
        this.createPopulation(this.n, populationSize);
        Random random = new Random();


        for (int i = 0; i < maxIter; i++) {
            // Initialization of the new generated population
            ArrayList<Chromosome> newPopulation = new ArrayList<>();

            for (int j = 0; j < populationSize / 2; j++) {
                // We choose 2 chromosomes from the population
                // The propability of selecting a specific chromosome depends on its fitness score

                // xi : index of chromosome x
                int xi = this.occurrences.get(random.nextInt(this.occurrences.size()));
                // Creation of chromosome x (parent)
                Chromosome x = this.population.get(xi);

                // yi : index of chromosome y
                int yi = this.occurrences.get(random.nextInt(this.occurrences.size()));

                // yi must be different from xi
                while (xi == yi) {
                    yi = this.occurrences.get(random.nextInt(this.occurrences.size()));
                }

                // Creation of chromosome y (parent)
                Chromosome y = this.population.get(yi);


                // Generate the children of the chromosomes x and y
                Chromosome[] children = this.reproduce(x, y);

                // Mutate the children

                if (random.nextDouble() < mutationPropability) {
                    children[0].mutate();
                    children[1].mutate();
                }

                // Now add them to the new population
                newPopulation.add(children[0]);
                newPopulation.add(children[1]);
            }

            this.population = new ArrayList<>(newPopulation);

            // Sort the population by fitness score (desc)
            this.population.sort(Collections.reverseOrder());

            // If the chromosome with the highest fitness is acceptable we return it
            if (this.population.get(0).getFitness() >= minFitness) {
                System.out.println("Number of iterations: " + (i+1));
                return this.population.get(0);
            }

            // Update the occurrences list
            this.updateOccurrenses();
        }
        System.out.println("Number of iterations: " + maxIter);
        return this.population.get(0);
    }

    // Initialize population by creating random chromosomes

    private void createPopulation(int n, int populationSize) {
        this.population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            this.population.add(new Chromosome(n));
        }
        this.updateOccurrenses();
    }

    // Updates the list that contains indexes of the chromosomes in the population ArrayList
    private void updateOccurrenses() {
        this.occurrences = new ArrayList<>();

        for(int i = 0; i < this.population.size(); i++) {
            for (int j = 0; j < this.population.get(i).getFitness(); j++) {
                // Each chromosome index exists in the list as many times as its fitness score
                // [0][0][0][0][0][0][0] ==> index 0 of population has a fitness score of 7
                // By creating this list this way, and choosing a random index from it,
                // the greater the fitness score of a chromosome, the greater the chance it will be chosen
                this.occurrences.add(i);
            }
        }
    }

    // Reproduces 2 chromosomes and generates their children
    private Chromosome[] reproduce(Chromosome x, Chromosome y) {
        Random random = new Random();

        // Randomly choose the intersection point
        int point = random.nextInt(x.getN()+1);

        int[] firstChild = new int[this.n];
        int[] secondChild = new int[this.n];

        // 1st child : 1st half of x + 2nd half of y
        // 2nd child : 1st half of y + 2nd half of x

        for (int i = 0; i < point; i++) {
            firstChild[i] = x.getGenes()[i];
            secondChild[i] = y.getGenes()[i];
        }

        for (int i = point; i < firstChild.length; i++) {
            firstChild[i] = y.getGenes()[i];
            secondChild[i] = x.getGenes()[i];
        }

        return new Chromosome[] {new Chromosome(firstChild), new Chromosome(secondChild)};
    }

}
