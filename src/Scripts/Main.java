package Scripts;

import java.io.FileNotFoundException;

import javax.swing.JFrame;

/**
 * 
 * @author Jan Kubice & Michaela Bene�ov�
 *
 */
public class Main {

	/**
	 * Vstupn� bod programu
	 * @param args
	 */
	public static void main(String[] args) {
		FrameMaker frameMaker = new FrameMaker();
		Simulation simulation = new Simulation();
		ReadData readData = new ReadData("Inputs/real_medium.txt", simulation);
		
		try {
			readData.setDataToSimulation();
		} catch (FileNotFoundException e) {
			System.out.print("Nepoda�ilo se na��st data");
			System.out.println(e.getMessage());
		}
		
		System.out.println(simulation.toString());

	}

}
