import java.util.Comparator;

public class StateComparator implements Comparator<State> {

    private int MODE = 0;

    public StateComparator(int MODE){
        this.MODE = MODE;
    }

    @Override
    public int compare(State state1, State state2) {
        int f1,f2;

        if(this.MODE == 0){
            f1 = state1.simpleHeuristic() + state1.getDepth();
            f2 = state2.simpleHeuristic() + state2.getDepth();
        } else {
            f1 = state1.heuristic() + state1.getDepth();
            f2 = state2.heuristic() + state2.getDepth();
        }

        if(f1 < f2){
            return -1;
        } else if (f1 > f2){
            return 1;
        } else {
            return 0;
        }
    }
}
