/*
 * This class is keeping track of the status information in the game. It will be a JPanel that you will attach to your 
 * frame. It will display how many ships the user has. 
 * It will display all the other information the user will want to know during the game.
 */

package asteroids;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;




public class Status extends JPanel {

	
	private JPanel northSubPanel;
	private JLabel status;
	
	private AsteroidGame game;
	
	public Status(Container container, AsteroidGame g)
	{
		
		game = g;
		//****set up North Panel to gbe the status bar
		
		northSubPanel = new JPanel();
		status = new JLabel("Sound: On Missiles 0 Asteroids 0 Ships 0 Difficulty Level 1 Score 0 Time: 0");
		northSubPanel.add(status);
		container.add(northSubPanel, BorderLayout.NORTH);

	}
	
	public void setStatus(String temp)
	{
		status.setText(temp);
	}
	
}
