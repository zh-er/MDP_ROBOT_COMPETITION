package mdp.algo;

import java.util.Stack;

import mdp.Config;
import mdp.competition.Communicator;
import mdp.simulation.SimCommunicator;



public class Robot {

	ArenaMap mapKnowledgeBase;
	VirtualPerceptron sensors;
	Explorer explorer;
	PathCalculator pathCalculator;
	
	
	boolean isExploring, isMoving, isOnTheWayReturning; 
	private Point currentLocation;
	private Stack<Point> route;
	
	
	public Robot() {
		isExploring = false;
		isMoving = false;
		isOnTheWayReturning = false;
		currentLocation = ArenaMap.START_POINT;
		mapKnowledgeBase = new ArenaMap();
		explorer = new Explorer();
		pathCalculator = new PathCalculator();
		route = new Stack<Point>();
	}
	
	public void start(){
		setCurrentLocation(ArenaMap.START_POINT);
		isExploring = true;
		isOnTheWayReturning = false;
	}
	
	@SuppressWarnings("unchecked")
	public Stack<Point> generateShortestPath(){
		pathCalculator.setMap(getMapKnowledgeBase().getArrayMap());
		if(pathCalculator.findShortestPath()){
			route = (Stack<Point>) pathCalculator.getShortestPath().clone();
			return pathCalculator.getShortestPath();
		}
		else 
			return null;
	}
	
	@SuppressWarnings("unchecked")
	public Stack<Point> generateRandomPath(){
		pathCalculator.setMap(this.getMapKnowledgeBase().getArrayMap());
		if(pathCalculator.findRandomPath()){
			route = (Stack<Point>) pathCalculator.getRandomPath().clone();
			return pathCalculator.getRandomPath();
		}
		else 
			return null;
	}
	
	public void move(){
		if (route!=null && !route.empty() ){
			if (Config.debugOn) System.out.println("Robot route exists");
			currentLocation = route.pop();
			updateLocation(currentLocation);
		} else {
			if (Config.debugOn) System.out.println("Robot route is empty..");
			isExploring = false;
		}
	}
	
	public void updateLocation(Point p){
		if (sensors.communicator instanceof SimCommunicator){
			SimCommunicator s ;
			s = (SimCommunicator) sensors.communicator;
			s.getMapPanel().updateRobot(p);
		} else if (sensors.communicator instanceof Communicator){
			//TODO
		}
	}

	public void moveForwardByOneStep(){
		
	}
	
	public void turnLeft(){
		
	}
	
	public void turnRight(){
	
	}
	
	public void updatePerceptronToKnowledgebase(){
		mapKnowledgeBase.setArrayMap(sensors.getEnvironment());
	}

	public Point getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Point currentLocation) {
		this.currentLocation = currentLocation;
	}
	
	public ArenaMap getMapKnowledgeBase() {
		return mapKnowledgeBase;
	}

	public void setMapKnowledgeBase(ArenaMap mapKnowledgeBase) {
		this.mapKnowledgeBase = mapKnowledgeBase;
	}

	public VirtualPerceptron getSensors() {
		return sensors;
	}

	public void setSensors(VirtualPerceptron sensors) {
		this.sensors = sensors;
	}

}
