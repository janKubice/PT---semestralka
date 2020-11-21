package Scripts;

import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * 
 * @author Jan Kubice & Michaela Benešová
 *
 */
public class Main {

	/**
	 * Vstupni bod programu
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//WriteData.setFile("", "slozka");
		//WriteData.openFile();
		Simulation simulation = new Simulation();
		ReadData readData = new ReadData(simulation);
		FrameMaker frameMaker = new FrameMaker(readData, simulation);
		FrameManager.frameMenu = frameMaker.makeMenu();
		FrameManager.openFrame(FrameManager.frameMenu);
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				if (simulation.canSimulate) {
					simulation.simulate();
					simulation.nextStep();
				}
			}
		}, 0, Settings.simulationDelay);
	}

}
