import java.util.ArrayList;
import java.util.Stack;

public class State {

    // these variables help with the generation of children
    private int lastCanTrasnported;
    private int lastMisTransported;

    // Number of cannibals in the left bank.
    private int canLeft;
    // Number of missionaries in the left bank.
    private int misLeft;
    // same for the right bank...
    private int canRight;
    // same for the right bank...
    private int misRight;
    // Size of the boat
    private int size;
    // Depth of state (from the root)
    private int depth;
    // The bank (left or right) where the boat is located
    private Bank bank;
    // The ancestor of the state
    private State father;
    
    public State(int N, int M){
        this.canLeft = N;
        this.misLeft = N;
        this.canRight = 0;
        this.misRight = 0;
        this.size = M;
        this.bank = Bank.LEFT;
        this.depth = 0;
        this.father = null;
    }
    
    public State(State state){
        // COPY CONSTRUCTOR
        this.canLeft = state.getCannibalsLeft();
        this.misLeft = state.getMissionariesLeft();
        this.canRight = state.getCannibalsRight();
        this.misRight = state.getMissionariesRight();
        this.size = state.getBoatSize();
        this.bank = state.getBoatLocation();
        this.depth = state.getDepth();
        this.father = state.getFather();
    }

    // MOVE BOAT TO RIGHT BANK OPERATOR
    // numCan: number of cannibals to transport
    // numMis: number of missionaries to transport
    // RETURNS the new state
    public State moveRight(int numCan, int numMis){

        // If the boat is already at the right bank,
        // there is no new state to return.
        if(this.bank == Bank.RIGHT){
            return null;
        }

        if(numCan == 0 && numMis == 0)
            return null;

        // If there is not enough room in the boat for the
        // cannibals and the missionaries, there is no new
        // state to return.
        if(numCan + numMis > this.getBoatSize()){
            return null;
        } else {
            // If the number of cannibals/missionaries requested to be transported
            // is more than the existing on the left bank, there is no new state to
            // return.
            if((this.getCannibalsLeft()-numCan < 0) || (this.getMissionariesLeft()-numMis <0)){
                return null;
            } else {

                // make sure that the number of cannibals on the boat
                // is not more than the number of missionaries
                if(numMis != 0 && numCan > numMis )
                    return null;

                // Create a copy of the current state in order to modify it
                State child = new State(this);
                // Start modifying the new state:
                // Move the boat to the right bank...
                child.setBoatLocation(Bank.RIGHT);
                // Decrease the cannibals of the left bank
                child.setCannibalsLeft(child.getCannibalsLeft() - numCan);
                // Increase the cannibals of the right bank
                child.setCannibalsRight(child.getCannibalsRight() + numCan);
                // Decrease the missionaries of the left bank
                child.setMissionariesLeft(child.getMissionariesLeft() - numMis);
                // Increase the missionaries of the right bank
                child.setMissionariesRight(child.getMissionariesRight() + numMis);
                // Set the father of the new state as the current state
                child.setFather(this);
                // Increase the depth by 1
                child.setDepth(child.getDepth()+1);
                // Return the new (child) state

                // note the last number of peopl transported
                // this is used by the getChildren function.
                child.setLastCanTrasnported(numCan);
                child.setLastMisTransported(numMis);

                return child;
                
            }
        }
    }
    
    public State moveLeft(int numCan, int numMis){

        // SIMILAR PROCEDURE AS DESCRIBED IN 'moveRight'.

        if(this.bank == Bank.LEFT){
            return null;
        }

        if(numCan == 0 && numMis == 0)
            return null;

        if(numCan + numMis > this.getBoatSize()){
            return null;
        } else {
            if((this.getCannibalsRight() - numCan < 0) || (this.getMissionariesRight() - numMis < 0)){
                return null;
            } else {

                if(numMis != 0 && numCan > numMis )
                    return null;

                State child = new State(this);
                child.setBoatLocation(Bank.LEFT);
                child.setCannibalsRight(child.getCannibalsRight() - numCan);
                child.setCannibalsLeft(child.getCannibalsLeft() + numCan);
                child.setMissionariesRight(child.getMissionariesRight() - numMis);
                child.setMissionariesLeft(child.getMissionariesLeft() + numMis);
                child.setFather(this);
                child.setDepth(child.getDepth()+1);

                child.setLastCanTrasnported(numCan);
                child.setLastMisTransported(numMis);

                return child;
            }
        }
    }

    // This method will be used by the SpaceSearcher to
    // explore states
    public ArrayList<State> getChildren(){

        // If the current state is not permitting
        // more children, don't return children
        // (return an empty arraylist)
        if (!this.isAcceptable())
            return new ArrayList();

        // Create the children arraylist
        ArrayList<State> children = new ArrayList();
        // Create a stack for the purpose of the
        // child generation algorithm.
        Stack<State> tempstates = new Stack();
        // If the boat is at the left bank
        if(this.bank == Bank.LEFT){
            // The first possible child state is transporting 1
            // and just moving the boat from the left bank to the right.
            State initChild = this.moveRight(0, 1);
            // Add the initial child state to the stack
            tempstates.add(initChild);
            initChild = this.moveRight(1, 0);
            // Add the initial child state to the stack
            tempstates.add(initChild);

            // While the stack is not empty:
            while(!tempstates.empty()){
                // Remove the top state from the stack
                State child = tempstates.pop();
                // If it is a valid state: not null
                // (moveRight/Left return null when the move cannot be done)
                if(child != null) {
                    // If the state examined at the moment is not already
                    // in the children arraylist...
                    if(children.indexOf(child) < 0){
                        // ... add it to the children.
                        children.add(child);
                        // Create the next child state by increasing the
                        // cannibal number transported by 1
                        State childNext = this.moveRight(child.getLastCanTrasnported() + 1, child.getLastMisTransported());
                        if(childNext != null) // if the move is possible...
                            tempstates.add(childNext); // add the state to the stack
                        // Create the next child state by increasing the
                        // cannibal number transported by 1
                        childNext = this.moveRight(child.getLastCanTrasnported(), child.getLastMisTransported() + 1);
                        if(childNext != null) // same...
                            tempstates.add(childNext); // same...
                    }
                }
            }
        } else {

            // SIMILAR PROCEDURE AS DESCRIBED FOR THE RIGHT BANK

            State initChild = this.moveLeft(0, 1);
            // Add the initial child state to the stack
            tempstates.add(initChild);
            initChild = this.moveLeft(1, 0);
            // Add the initial child state to the stack
            tempstates.add(initChild);

            while(!tempstates.empty()){
                State child = tempstates.pop();
                if(child != null) {
                    if(children.indexOf(child) < 0){
                        children.add(child);
                        State childNext = this.moveLeft(child.getLastCanTrasnported() + 1, child.getLastMisTransported());
                        if(childNext != null)
                            tempstates.add(childNext);
                        childNext = this.moveLeft(child.getLastCanTrasnported(), child.getLastMisTransported() + 1);
                        if(childNext != null)
                            tempstates.add(childNext);
                    }
                }
            }
        }

        for(State s : children)
            s.resetLastPeopleTransported();

        return children;
    }

    // checks whether the state is final.
    public boolean isFinal(){
        return ((this.canLeft == 0) && (this.misLeft == 0)) ? true : false;
    }

    // This method judges if the current state is acceptable
    // and can produce children
    private boolean isAcceptable(){
        // If the missionaries at each side are more than the cannibals (at each side), then the
        // state is acceptable.
        return ((this.misLeft >= this.canLeft || this.misLeft == 0) && (this.misRight >= this.canRight || this.misRight == 0) && !this.isFinal()) ? true : false;
    }

    public State getFather(){
        return this.father;
    }
    
    public int getDepth(){
        return this.depth;
    }
    
    public Bank getBoatLocation(){
        return this.bank;
    }
    
    public int getCannibalsLeft(){
        return this.canLeft;
    }
    
    public int getMissionariesLeft(){
        return this.misLeft;
    }
    
    public int getCannibalsRight(){
        return this.canRight;
    }
    
    public int getMissionariesRight(){
        return this.misRight;
    }
    
    public int getBoatSize(){
        return this.size;
    }

    public void setFather(State father){
        this.father = father;
    }

    public void setDepth(int depth){
        this.depth = depth;
    }

    public void setBoatLocation(Bank bank) {
        this.bank = bank;
    }

    public void setCannibalsLeft(int cannibals){
        this.canLeft = cannibals;
    }

    public void setMissionariesLeft(int missionaries){
        this.misLeft = missionaries;
    }

    public void setCannibalsRight(int cannibals){
        this.canRight = cannibals;
    }

    public void setMissionariesRight(int missionaries){
        this.misRight = missionaries;
    }

    public void setBoatSize(int capacity){
        this.size = capacity;
    }

    public int getLastCanTrasnported() {
        return lastCanTrasnported;
    }

    public void setLastCanTrasnported(int lastCanTrasnported) {
        this.lastCanTrasnported = lastCanTrasnported;
    }

    public int getLastMisTransported() {
        return lastMisTransported;
    }

    public void setLastMisTransported(int lastMisTransported) {
        this.lastMisTransported = lastMisTransported;
    }

    public void resetLastPeopleTransported(){
        lastCanTrasnported = 0;
        lastMisTransported = 0;
    }

    @Override
    public String toString() {
        if(this.bank == Bank.LEFT){
            return "C: "+this.canLeft+", M: "+this.misLeft+", B | C: "+this.canRight+", M: "+this.misRight + " (Depth: "+this.depth+")";
        } else {
            return "C: "+this.canLeft+", M: "+this.misLeft+" | B, C: "+this.canRight+", M: "+this.misRight + " (Depth: "+this.depth+")";
        }
    }

    @Override
    public boolean equals(Object obj){
        State rhs = (State) obj;
        if(this.bank == rhs.getBoatLocation() && this.size == rhs.getBoatSize()){
            if(this.canLeft == rhs.getCannibalsLeft()){
                if(this.misLeft == rhs.getMissionariesLeft()){
                    if(this.canRight == rhs.getCannibalsRight()){
                        if(this.misRight == rhs.getMissionariesRight()){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int prime = 65713;
        int prefix = (this.bank == Bank.LEFT) ? 1 : -1;
        String hash = "" + (this.canLeft % prime) + (this.misLeft % prime);
        long l = Long.parseLong(hash) % prime;
        return prefix * ((int) l);
    }

    public int simpleHeuristic() {
        int numOfPeopleOnLeftSide = this.canLeft + this.misLeft;

        if(numOfPeopleOnLeftSide == 0)
            return 0;

        if(this.bank == Bank.LEFT) {
            return 1;
        } else {
            return 2;
        }
    }

    public int heuristic(){
        double numOfPeopleOnLeftSide = this.canLeft + this.misLeft;

        if (numOfPeopleOnLeftSide == 0)
            return 0;

        if(this.bank == Bank.RIGHT){
            if(numOfPeopleOnLeftSide <= this.size -1)
                return 2;

            return 2 * ( (int) Math.ceil((numOfPeopleOnLeftSide)/((double) this.size-1)));
        } else {
            if(numOfPeopleOnLeftSide <= this.size)
                return 1;

            return 2*((int) Math.ceil((numOfPeopleOnLeftSide - 1)/((double) this.size -1))) - 1;
        }
    }
}