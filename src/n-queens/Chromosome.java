import java.util.Random;

public class Chromosome implements  Comparable<Chromosome> {

    /*
   FORMAT FOR N = 8 QUEENS :

    1 2 3 4 5 6 7 8
  1 [][][][][][][][]
  2 [][][][][][][][]
  3 [][][][][][][][]
  4 [][][][][][][][]
  5 [][][][][][][][]
  6 [][][][][][][][]
  7 [][][][][][][][]
  8 [][][][][][][][]

example for a chromosome sequence : [3,7,8,1,4,3,7,2]
    ex. 3 : row , index(3) : column

    1 2 3 4 5 6 7 8
  1 [][][] .[][][][]
  2 [][][][][][][] .
  3  .[][][][] .[][]
  4 [][][][] .[][][]
  5 [][][][][][][][]
  6 [][][][][][][][]
  7 [] .[][][][] .[]
  8 [][] .[][][][][]

     */


    //Each position shows the vertical position of a queen in the corresponding column
    private int[] genes;

    //Integer that holds the fitness score of the chromosome
    private int fitness;

    // Number of queens
    private int n;


    // Creates a randomly created chromosome
    Chromosome(int n) {
        this.n = n;
        this.genes = new int[this.n];
        Random random = new Random();
        for (int i = 0; i < this.genes.length; i++) {
            this.genes[i] = random.nextInt(this.genes.length);
        }
        this.calculateFitness();
    }



    // Creates a copy of a chromosome
    Chromosome(int[] sq) {
        this.genes = new int[sq.length];
        for (int i = 0; i < this.genes.length; i++) {
            this.genes[i] = sq[i];
        }
        this.calculateFitness();
    }

    // Calculates the fitness score of the chromosome as the number queen pairs that are not threatened.
    // score++; if every pair of queens are non-attacking
    //The maximum number of queen pairs that are NOT threatened is (n-1) + (n-2) + ... + (n-n)
    // For n = 8 queens the maximum fitness score is 28.
    /*
               i     j
             1 2 3 4 5 6 7 8
          1 [][][][][][][][]
          2 [][][][][.][][][]
          3 [][][][][][][][]
          4 [][][][][][][][]
          5 [][.][][][][][][]
          6 [][][][][][][][]
          7 [][][][][][][][]
          8 [][][][][][][][]

          genes[2]=5 , genes[5]=2


     */

    void calculateFitness() {
        int score = 0;
        // Never in the same column because i will never be equal to j
        for (int i = 0; i < this.genes.length; i++) {
            for (int j = i+1; j < this.genes.length; j++) {
                // if genes[i] == genes[j] then the 2 queens are in the same row
                if ((this.genes[i] != this.genes[j])) {
                    // i = 2 and j = 5 ==  3 == 3  == genes[2] = 5 and genes[5] = 2
                    // queens are threatening each other
                    // Checking if the pair is in the same diagonal
                    if (Math.abs(i - j) != Math.abs(this.genes[i] - this.genes[j])) {
                        score++;
                    }
                }
            }
        }
        this.fitness = score;
    }


   void mutate() {
        Random random = new Random();
        this.genes[random.nextInt(this.genes.length)] = random.nextInt(this.genes.length);
        this.calculateFitness();
   }


   public int[] getGenes() {
        return this.genes;
   }

   public int getFitness() {
        return this.fitness;
   }

   public int getN() {
       return this.n;
   }


   @Override
    public int compareTo(Chromosome chromosome) {
        return this.fitness - chromosome.getFitness();
   }

    void print() {
       System.out.print("Chromosome : |");
       for(int i = 0; i < this.genes.length; i++)
       {
           System.out.print(this.genes[i]);
           System.out.print("|");
       }
       System.out.print(", Fitness : ");
       System.out.println(this.fitness);

       System.out.println("------------------------------------");
       for(int i = 0; i < this.genes.length; i++)
       {
           for(int j=0; j < this.genes.length; j++)
           {
               if(this.genes[j] == i)
               {
                   System.out.print("|Q");
               }
               else
               {
                   System.out.print("| ");
               }
           }
           System.out.println("|");
       }
       System.out.println("------------------------------------");
   }
}
