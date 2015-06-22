import javax.swing.UIManager;

/**	The class Main creates a View object. */
public class Main {
	public static void main(String[] args) {
		// FORCE DEFAULT JAVA UI STYLE TO PREVENT OSX FROM USING THE STUPID BROKEN ONE
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new View();
	}
}