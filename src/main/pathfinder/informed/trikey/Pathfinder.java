package main.pathfinder.informed.trikey;
import java.lang.Math;
import java.util.*;

/**
 * Maze Pathfinding algorithm that implements a basic, uninformed, breadth-first
 * tree search.
 */
public class Pathfinder {

    /**
     * Given a MazeProblem, which specifies the actions and transitions available in
     * the search, returns a solution to the problem as a sequence of actions that
     * leads from the initial state to the collection of all three key pieces.
     * 
     * @param problem A MazeProblem that specifies the maze, actions, transitions.
     * @return A List of Strings representing actions that solve the problem of the
     *         format: ["R", "R", "L", ...]
     */
    
	public static ArrayList<String> buildPath(SearchTreeNode current) {
		ArrayList<String> solution = new ArrayList<String>();
		while(current.parent != null) {
			solution.add(current.action);
			current = current.parent;
		}
		Collections.reverse(solution);
		System.out.println(solution);
		return solution;
	}
	public static List<String> solve (MazeProblem problem) {
        
		PriorityQueue<SearchTreeNode> frontier = new PriorityQueue<>();
		
		MazeState initialState = problem.getInitial();
		//List<MazeState> keyStates = new ArrayList<>(problem.getKeyStates());
		Set<SearchTreeNode> graveYard = new HashSet<>();
		SearchTreeNode initialNode = new SearchTreeNode(initialState, null, null, getClosestKey(initialState, problem.getKeyStates(), new HashSet<>()), 0, new HashSet<>());
		frontier.add(initialNode);
		
		
		while (!frontier.isEmpty()) {
			SearchTreeNode expandingNode = frontier.poll();
			MazeState expandingState = expandingNode.state;
			if (expandingNode.keysCollected.size() == 3) {
				return buildPath(expandingNode);
			}
			//Set<MazeState> keysCollecteds = expandingNode.keysCollected; 
			Map<String, MazeState> transitions = problem.getTransitions(expandingState);
			for (Map.Entry<String, MazeState> entry : transitions.entrySet()) {
				MazeState childState = entry.getValue();

        		SearchTreeNode childNode = new SearchTreeNode(childState, entry.getKey(), expandingNode, getClosestKey(childState, problem.getKeyStates(), expandingNode.keysCollected), problem.getCost(childState), new HashSet<>(expandingNode.keysCollected));
        		
        		if (problem.getKeyStates().contains(childNode.state)) {
        			childNode.keysCollected.add(childNode.state);
        			//keep track of all the keys youve found so far, set
        			//we only build our spath after finding akk tthe keys
        			
        			
        		} 
        		if(!graveYard.contains(childNode)) {
        			frontier.add(childNode);
        		}
        			
        		graveYard.add(expandingNode);
			}	
			/**
			 * right now, we are only looking for one specific key " getKeyState.get(0)"
			 * how do I change this so that it picks which key to get to by comparing the cost.
			 */
			
	        //*while all three keys are not found {
		        //if node is key {
		        //	store solution
		        //	add solution to grave yard
		        //else {
		        //	expand
	}
		return null; 
}
     

    /**
     * SearchTreeNode private static nested class that is used in the Search
     * algorithm to construct the Search tree.
     * [!] You may do whatever you want with this class -- in fact, you'll need 
     * to add a lot for a successful and efficient solution!
     */
    private static class SearchTreeNode implements Comparable<SearchTreeNode> {

        MazeState state;
        String action;
        SearchTreeNode parent;
       // MazeState goal;
        int pathCost; 
        int estimateCost;
        Set<MazeState> keysCollected;

        /**
         * Constructs a new SearchTreeNode to be used in the Search Tree.
         * 
         * @param state  The MazeState (row, col) that this node represents.
         * @param action The action that *led to* this state / node.
         * @param parent Reference to parent SearchTreeNode in the Search Tree.
         * @param pathCost The cost we have so far
         * @param estimateCost The estimated cost to get to our key
         */
        
        
        SearchTreeNode(MazeState state, String action, SearchTreeNode parent, int estimateCost, int cost, Set<MazeState> keysCollected) {
            this.state = state;
            this.action = action;
            this.parent = parent;
            this.keysCollected = keysCollected; 
            //this.goal = goal;
            //this.cost = getCost(state);
            this.estimateCost = estimateCost;
            if (parent != null) {
            	this.pathCost = parent.pathCost + cost;
            	//this
            }
            else {
            	this.pathCost = 0;
            	//this.keysCollected = null;
            }
            
            
             
            
        }


		@Override
		/**
		 * returns > 0 if this is > other
		 * returns = 0 if this == other
		 * returns < 0 if this < other
		 * compareTo compares and adds to our frontier
		 */
		public int compareTo(SearchTreeNode o) {
			// TODO Auto-generated method stub
			int compareCost = (this.estimateCost + this.pathCost) - (o.estimateCost + o.pathCost);
			return compareCost;
		}
		 @Override
		    public boolean equals(Object other) {
		        if (this == other) {
		            return true;
		        }

		        return other.getClass() == this.getClass()
		                ? this.state.equals(((SearchTreeNode) other).state) && this.keysCollected.equals(((SearchTreeNode) other).keysCollected)
		                : false;
		    }
		 @Override
		 public int hashCode () {
			return Objects.hash(this.state, this.keysCollected);
			 
		 }
    }
    

	
    public static int getManhatan(MazeState start, MazeState finish) {
    
    	return Math.abs((start.col() - finish.col())) + Math.abs((start.row() - finish.row()));
    }
    
    /**
     * This method returns the distance from our current state to the keys
     * @param graveyard is the collection of all the states we have explored so far
     * @param current is the current MazeState we are on
     * @param problem is the MazeProblem we are solving
     * @return distance from our current state to the key 
     */
    private static int getClosestKey(MazeState current, Set <MazeState> problemkeys, Set <MazeState> keysFound){  
    	
    	//loop through every key state and compare distance from current node
    	//Set<MazeState> allKeyStates = problem.getKeyStates();
    	int minimumDist = 100;
    	int distancefromKey = 0;
    	for (MazeState key : problemkeys) { 
    		if(!keysFound.contains(key)){
    			distancefromKey = getManhatan(current, key);
    			if(distancefromKey < minimumDist) {
    				minimumDist = distancefromKey;
    			}
    		}
    		
    			
    	}
    	return minimumDist;
    		
    	}
    	
    
		
    
}
