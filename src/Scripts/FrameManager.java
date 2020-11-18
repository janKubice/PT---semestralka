package Scripts;

import javax.swing.JFrame;

public class FrameManager {
	public static JFrame frameMenu;
	public static JFrame frameSim;
	public static JFrame frameSettings;
	
	public static void openFrame(JFrame frame) {
		frame.setVisible(true);
	}
	
	public static void closeFrame(JFrame frame) {
		frame.setVisible(false);
	}
}
