import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/** The class Controller responds to client generated events. */
public class Controller implements ActionListener {
    private Model model;

    /** Create a Controller object linked to a Model object.
    @param mdl, an existin Model object to send messages */
    public Controller(Model mdl) {
        model = mdl;
    }

    /** Define the Java required method to respond to an action event.
    @param event, an ActionEvent from a button on the board or
    a menu item */
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        model.respond(command);
    }
}