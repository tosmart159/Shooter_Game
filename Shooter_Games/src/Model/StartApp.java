package Model;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class StartApp extends JFrame{
        
    private StartApp()
    {
        this.setTitle("Shooter_Games");
        if(true)
        {
        	this.setSize(1000, 600);
            this.setLocationRelativeTo(null);
            this.setResizable(false);
        }
        else // Full screen
        {
            this.setUndecorated(true);
            this.setExtendedState(this.MAXIMIZED_BOTH);
        }
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(new View());
        this.setVisible(true);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StartApp();
            }
        });
    }
}
