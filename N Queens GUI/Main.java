/*

LOOKBACK
========

Joey: The hardest part of this lab was working with your code. It would appear
that the base (unfinished) code we were provided as a business layer to work
with and add to was poorly designed, leaving us with only very convoluted
and inefficient ways of accomplishing any of the numerous tasks required
of us by the abilities granted to the user by the GUI. Because of this, I ditched
your business layer entirely and ported my code from Data Structures to
work with your GUI. This was a bit troublesome, however, it was doable.
I had a few weird bugs I had to work out, but pretty much everything
seems to work except for a few rare graphical glitches and exceptions that
seem to be issues with Java, as I’ve been unable to trace them to any lines
in my (or your) code. Charmaine was very willing to help, however, she
had (understandable) issues with interpreting my code when we decided to
ditch yours. Of course, she had issues understanding your code as well, but 
BOTH of us did. Most of the class that I've talked to are still confused with
your code. She was very willing to help and eager to understand, which I greatly
appreciated in contrast to previous experiences with partners from Data Structures.

Charmaine: None. I have been unable to contact her since Friday. I have
been unable to gather her opinion on the difficulties of the assignment.
I have no idea what is wrong.

GROUP: None, I guess. I suppose mine will have to act as an individual
and group look-back.


*/

import javax.swing.UIManager;

/**	The class Main creates a View object.
	@author yanushka
 */
public class Main {
	public static void main(String[] args) {
		//FORCE DEFAULT JAVA UI STYLE TO PREVENT OSX FROM USING THE STUPID BROKEN ONE
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new View();
	}
}