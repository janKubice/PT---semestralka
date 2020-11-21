package Scripts;

import javax.swing.JFrame;

/**
 * Trida spravujici okna aplikace
 * @author Jan Kubice & Michaela Benešová
 *
 */
public class FrameManager {
	public static JFrame frameMenu;
	public static JFrame frameSim;
	public static JFrame frameSettings;
	
	/**
	 * Otevre predane okno
	 * @param okno k otevreni
	 */
	public static void openFrame(JFrame frame) {
		frame.setVisible(true);
	}
	
	/**
	 * Zavre predane okno
	 * @param okno k zavreni
	 */
	public static void closeFrame(JFrame frame) {
		frame.setVisible(false);
	}
}
