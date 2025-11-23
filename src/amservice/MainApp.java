package amservice;

import javax.swing.*;

import database.DatabaseHelper;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Objects;


public class MainApp extends JPanel{
	@Serial
    private static final long serialVersionUID = 1L;
//	public static void main(String[]args) {
	JFrame frame;
	CardLayout cardLayout;
	JPanel cardsPanel;
	@SuppressWarnings({"ResultOfMethodCallIgnored", "CallToPrintStackTrace"})
    public  MainApp() {
		 setLayout(new GridBagLayout());
		 frame=new JFrame("AR HOSPITAL");
		// Check for database file first
		
		File lockFile = new File("app.lock");
		if (lockFile.exists()) {
		    System.out.println("⚠️ Last session may not have exited properly.");
		} else {
		    try {
		        lockFile.createNewFile();
		    } catch (IOException e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
		    }
		}
		cardLayout = new CardLayout();
		cardsPanel = new JPanel(cardLayout);
		JPanel loginPanel = new JPanel(new FlowLayout());
		frame.setSize(300,150);
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/icons8-hospital-32.png")));
	    Image image= icon.getImage();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Shutdown
		frame.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	UIManager.put("Button.focus", new Color(0,0,0,0));
		        int confirmed = JOptionPane.showConfirmDialog(
		            frame,
		            "Are you sure you want to exit?",
		            "Exit Confirmation",
		            JOptionPane.YES_NO_OPTION
		        );

		        if (confirmed == JOptionPane.YES_OPTION) {
		            shutdownApp(); 
		        }
		    }
		});
		frame.setLayout(new FlowLayout());
		JButton loginButton=new JButton("CareOPS Console");
		loginButton.setFocusPainted(false);
		loginButton.setBounds(100,40,100,30);
		loginPanel.add(loginButton);
		frame.setVisible(true);
		frame.setIconImage(image);
		cardsPanel.add(loginPanel, "login");
		frame.setContentPane(cardsPanel);
		cardLayout.show(cardsPanel, "login");
		
		frame.setVisible(true);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
		    new File("app.lock").delete();

		    try (FileWriter fw = new FileWriter("app_log.txt", true)) {
		        fw.write("Application closed at " + java.time.LocalDateTime.now() + "\n");
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    }

		    
		    DatabaseHelper.closeConnection();
		}));
		loginButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				 String userid=JOptionPane.showInputDialog(frame,"Username");
				 String password=JOptionPane.showInputDialog(frame,"Password");
				if (userid == null || password == null) return;
				if(password!=null&&password.equals("*******")&&userid.equals("AR"))
				{
					UIManager.put("Button.focus", new Color(0,0,0,0)); // Removes the focus border on buttons
					JOptionPane.showMessageDialog(frame, "Login Successfull");
					TabsUI tabbedPane = new TabsUI();
					cardsPanel.add(tabbedPane, "tabs");
					cardsPanel.remove(cardsPanel.getComponent(0)); 
					cardLayout.show(cardsPanel, "tabs");
					frame.setSize(500, 500);
					cardsPanel.revalidate();
					cardsPanel.repaint();
				}
				else
				{
					UIManager.put("Button.focus", new Color(0,0,0,0));
					JOptionPane.showMessageDialog(frame, "Incorrect Username or Password");
				}
				}
		});
		
	

		frame.setContentPane(cardsPanel);
		cardLayout.show(cardsPanel, "login");
		frame.setLocationRelativeTo(null); // center window
		frame.setVisible(true);
		
	}
	private void shutdownApp() {
		System.out.println("App closed properly.");
	    frame.dispose();
	    System.exit(0);
	} 
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(MainApp::new);
	}

}

