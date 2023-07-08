import java.util.ArrayList;
import java.util.HashSet;

public class SpaceSearcher {

    private ArrayList<State> frontier;
    private HashSet<State> closedset;
    private int k;
    private int MODE;


    public SpaceSearcher() {
        this.frontier = new ArrayList<>();
        this.closedset = new HashSet<>();
        // k == -1 means that there is no step limit
        this.k = -1;
        this.MODE = 0;
    }

    public SpaceSearcher(int k, int MODE) {
        this.frontier = new ArrayList<>();
        this.closedset = new HashSet<>();
        this.k = k;
        this.MODE = MODE;
    }

    public State aStar(State init){

        if (init.isFinal())
            return init;

        StateComparator sc = new StateComparator(MODE);
        // step 1 add state to the frontier.
        this.frontier.add(init);

        // step 2 while the frontier is not empty...
        while(this.frontier.size() > 0){

            // step 3: get the first node out of the frontier.
            State currentState = this.frontier.remove(0);

            // Check if the current state requires more steps than
            // given number K.
            if(this.k != -1 && currentState.getDepth() > this.k)
                continue;

            // step 4: if final state, return.
            if (currentState.isFinal())
            {
                return currentState;
            }
            // step 5: if the node is not in the closed set, put the children at the END of the frontier (queue).
            // else go to step 2.
            if(!this.closedset.contains(currentState))
            {
                this.closedset.add(currentState);
                this.frontier.addAll(currentState.getChildren());
                this.frontier.sort(sc);
            }
        }

        if(this.k == -1)
            System.out.println("No solution found!");
        else
            System.out.println("No solution found with less than K = "+this.k+" steps!");
        return null;
    }

}
