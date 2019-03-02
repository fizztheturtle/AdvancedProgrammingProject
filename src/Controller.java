import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JFrame;

/**
 * The Controller class has the main method inside and is also 
 * used to call the  {@link MainForm} class that is ran after the application is launched.
 * 
 *  @author Faisal Kabeer
 *  @version 1.0
 */



public class Controller {
	
/**
 * This creates the instance for the form to display.
 * @param args Unused as not required to run the form.
 */
	public static void main(String[] args) {
	
	
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
			
			try {
				
			MainForm form1 = new MainForm();	
		
				form1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				

				form1.setVisible(true);
				
			
			} catch (ClassNotFoundException|SQLException|IOException e) {
				e.printStackTrace();
			}
			
			}
			});
		}


}

