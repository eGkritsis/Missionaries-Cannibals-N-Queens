public class Main {
    public static void main(String[] args) {

        int N, popSize, maxIter, minFitness;
        float mutationProb;
        long start, end;
        boolean fail = false;

        if (args.length != 5){
            System.out.println("Error! Wrong parameters.");
            System.out.println("Usage: \"java Queens <N> <popSize> <mutprob> <maxIter> <minFitness>\"\n");
        } else {

            N = Integer.parseInt(args[0]);
            popSize = Integer.parseInt(args[1]);
            mutationProb = Float.parseFloat(args[2]);
            maxIter = Integer.parseInt(args[3]);
            minFitness = Integer.parseInt(args[4]);

            if(N < 4){
                System.out.println("The problem cannot be solved for boards with size smaller than 4!!");
                System.out.println("Please, provide another number for the size of the board...");
                fail = true;
            }

            if(!fail){
                GeneticAlgorithm algorithm = new GeneticAlgorithm(N);
                start = System.currentTimeMillis();
                Chromosome solution = algorithm.run(popSize, mutationProb, maxIter, minFitness);
                end = System.currentTimeMillis();
                solution.print();
                System.out.println("Runtime: "+(end-start)/1000.0D);
            }
            
        }
        
    }
}
