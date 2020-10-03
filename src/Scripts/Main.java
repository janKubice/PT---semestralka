package Scripts;

import java.io.FileNotFoundException;

import javax.swing.JFrame;

/**
 * 
 * @author Jan Kubice & Michaela Benešová
 *
 */
public class Main {

	/**
	 * Vstupní bod programu
	 * @param args
	 */
	public static void main(String[] args) {
		FrameMaker frameMaker = new FrameMaker();
		Simulation simulation = new Simulation();
		ReadData readData = new ReadData("Inputs/real_medium.txt", simulation);
		
		try {
			readData.setDataToSimulation();
		} catch (FileNotFoundException e) {
			System.out.print("Nepodaøilo se naèíst data");
			System.out.println(e.getMessage());
		}
		
		System.out.println(simulation.toString());

	}

}
