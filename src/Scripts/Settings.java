package Scripts;

public class Settings {
	public static int simulationDelay = 200;
	public static int loopDelay = 200;
	public static int lastLoopDelay;
	
	
	public static void addLoopDelay() {
		if (loopDelay < 200)
			loopDelay += 20;
		else if (loopDelay < 1000)
			loopDelay += 100;
		
		lastLoopDelay = loopDelay;
	}
	
	public static void takeLoopDelay() {
		if (loopDelay > 200)
			loopDelay -= 100;
		else if (loopDelay > 70)
			loopDelay -= 20;
		else if (loopDelay > 10)
			loopDelay -= 5;
		
		lastLoopDelay = loopDelay;
	}
	
	public static void letFree() {
		loopDelay = 0;
		
		lastLoopDelay = loopDelay;
	}
	
}
