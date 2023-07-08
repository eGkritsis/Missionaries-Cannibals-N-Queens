
public class MisCan {
    public static void main(String[] args) {
        
        int N = 3, M = 2, K = -1, MODE = 0;
        boolean fail = false;

        if (args.length == 2) {
            N = Integer.parseInt(args[0]);
            M = Integer.parseInt(args[1]);
        } else if (args.length == 3) {
            N = Integer.parseInt(args[0]);
            M = Integer.parseInt(args[1]);
            K = Integer.parseInt(args[2]);
        } else if (args.length == 4) {
            N = Integer.parseInt(args[0]);
            M = Integer.parseInt(args[1]);
            K = Integer.parseInt(args[2]);
            MODE = Integer.parseInt(args[3]);
        } else {
            fail = true;
        }

        if (!fail) {
            State initial = new State(N, M);
            SpaceSearcher ss = new SpaceSearcher(K, MODE);
            long start = System.currentTimeMillis();
            State finalstate = ss.aStar(initial);
            long end = System.currentTimeMillis();
            while (finalstate != null) {
                System.out.println(finalstate);
                finalstate = finalstate.getFather();
            }
            System.out.println((end - start) / 1000.D + " sec(s)");

        } else {
            System.out.println("Error! Invalid arguments.");
            System.out.println("Usage: '\"java MisCan <N> <M> [<K> <MODE>]\"'");
        }

    }
}
