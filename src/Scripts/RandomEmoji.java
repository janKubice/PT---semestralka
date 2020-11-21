package Scripts;

import java.util.Random;

/**
 * Metoda pro vytvareni emoji
 * @author Jan Kubice & Michaela Benešová
 *
 */
public class RandomEmoji {

	/**
	 * Metoda pro vytvoreni stringu s emoji
	 * @return nahodne emoji dopravniho prostredku
	 */
	public static String getRandomTransportEmoji() {
		Random rnd = new Random();
		int i = rnd.nextInt(6);

		switch (i) {
		case 0:
			return new String(new int[] { 0x1f69a }, 0, 1);
		case 1:
			return new String(new int[] { 0x1F686 }, 0, 1);
		case 2:
			return "\u26F5";
		case 3:
			return new String(new int[] { 0x1F680 }, 0, 1);
		case 4:
			return new String(new int[] { 0x1F414 }, 0, 1);
		case 5:
			return new String(new int[] { 0x1F6B2 }, 0, 1);

		default:
			return new String(new int[] { 0x1f69a }, 0, 1);
		}

	}
}
