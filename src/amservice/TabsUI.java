package amservice;
import java.awt.*;
import java.awt.event.*;

import javax.swing.table.*;

import database.DatabaseHelper;
import dataentries.DataEntry;
import dataentries.DataEntry2;
import features.ButtonRendererEditor;
import features.ClosableTabComponent;

import javax.swing.*;
public class TabsUI extends JPanel {
	
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
// public static void main(String[] args) {
    public TabsUI() {	
        //JFrame frame = new JFrame("AR HOSPITALS");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(500, 500);

        JTabbedPane tabbedPane = new JTabbedPane();
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/icons8-hospital-32.png"));
        //ImageIcon icon = new ImageIcon("C:/Users/HP/Downloads/icons8-hospital-32.png");
	     @SuppressWarnings("unused")
		Image image= icon.getImage();
	     ImageIcon List= new ImageIcon(getClass().getResource("/icons/icons8-list-32.png"));
	     ImageIcon Ambulance = new ImageIcon(getClass().getResource("/icons/icons8-ambulance-32.png"));
        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("Welcome Admin"));
        JButton list = new JButton("List Doctors /Other Staff     ");
   	    list.setFocusPainted(false);//To remove hightlighting effect when clicked on button
    	JButton ambulance = new JButton("Emergency Cases/Allocate");
   	    ambulance.setFocusPainted(false);
   	    list.setIcon(List);
   	    ambulance.setIcon(Ambulance);
   	    
	   	 list.setPreferredSize(new Dimension(150,100));
		 ambulance.setPreferredSize(new Dimension(500,100));
		 
		 panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));// vertical stacking
		 list.setAlignmentX(Component.CENTER_ALIGNMENT); // center list button horizontally
		 ambulance.setAlignmentX(Component.CENTER_ALIGNMENT);// center ambulance button horizontally
		 panel1.add(Box.createVerticalGlue());
		 panel1.add(list);
		 panel1.add(Box.createRigidArea(new Dimension(0, 20))); // add fixed 20px vertical gap
		 panel1.add(ambulance);
		 panel1.add(Box.createVerticalGlue()); // Add vertical glue to center buttons vertically
		 //frame.add(panel1);
//	 frame.setIconImage(image);
		 //frame.setVisible(true); 
		 list.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	 String tabTitle = "DOCTORS/OTHER STAFF";
			         int index = tabbedPane.indexOfTab(tabTitle);
			         if (index != -1) {
			             // Tab already exists, just select it
			             tabbedPane.setSelectedIndex(index);
			             return;
			         }
			        JPanel panel2 = new JPanel(new BorderLayout());
			        JScrollPane scrollPane = new JScrollPane();
			        JTable table = new JTable();
			        scrollPane.setViewportView(table);

			        JButton addButton = new JButton("Add");
			        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			        topPanel.add(addButton);

			        panel2.add(topPanel, BorderLayout.NORTH);
			        panel2.add(scrollPane, BorderLayout.CENTER);

//			        int index = tabbedPane.indexOfTab("DOCTORS/OTHER STAFF");
//			        if (index == -1) {
//			            tabbedPane.addTab("DOCTORS/OTHER STAFF", panel2);
//			        }
//			        tabbedPane.setSelectedComponent(panel2);
			        DefaultTableModel model = new DefaultTableModel(
		                    new String[]{"Name", "Age", "Email", "Department", "Position"}, 0);
			        int delay=5000;
			        new javax.swing.Timer(delay,evt->{
			        new SwingWorker<Void, Void>() {
			            //Void-Object type

			            @Override
			            protected Void doInBackground(){
			            	try {
			                DatabaseHelper.loadAllStaff(model);
			            	}
			            	catch(Exception ex) {
			            		 ex.printStackTrace();  // Log the error to console or logger
			            	        // Optionally show a user-friendly message in the UI thread
			            	        SwingUtilities.invokeLater(() ->
			            	        
			            	           showError("Failed to load staff data from database.")
			            	        );
			            	}
			            	return null;
			            }
			            

			            @Override
			            protected void done() {
			                table.setModel(model);
			            }
			        }.execute();
			        }).start();
			        addButton.setFocusPainted(false);
			        addButton.addActionListener(evt -> new DataEntry(model));
			        addClosableTab(tabbedPane, tabTitle, panel2);
			       // tabbedPane.addTab(tabTitle, panel2);
			        tabbedPane.setSelectedComponent(panel2);
			    }
			});
//		 ambulance.addActionListener(new ActionListener()
//			{
//				public void actionPerformed(ActionEvent e) {
//					DefaultTableModel model = new DefaultTableModel(
//	                        new String[]{"SNO","Driver Name", "Age", "vechile no", "no of assistant", "ambulance type","phone No","Status"}, 0);
//					DatabaseHelper.loadAmbulanceDrivers(model);
//	                JTable table = new JTable(model);
//	                int statusColIndex =  model.findColumn("Status"); // "Status" is the 8th column (index 6)
//	                table.getColumnModel().getColumn(statusColIndex).setCellRenderer(new ButtonRendererEditor(table, model));
//	                table.getColumnModel().getColumn(statusColIndex).setCellEditor(new ButtonRendererEditor(table, model));
//	                JScrollPane scrollPane = new JScrollPane();
//	                scrollPane.setViewportView(table);
//	                JPanel panel2 = new JPanel(new BorderLayout());
//	                tabbedPane.addTab("Emergency Cases/Allocate", panel2);
//	                int index = tabbedPane.indexOfTab("Emergency Cases/Allocate");
//	                if (index == -1) {
//	                    tabbedPane.addTab("Emergency Cases/Allocate", panel2);
//	                }
//	                JButton addButton = new JButton("Add");
////panel2.add(addButton);
//	                JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//	                topPanel.add(addButton);
//	                panel2.add(topPanel, BorderLayout.NORTH);
//	                panel2.add(scrollPane, BorderLayout.CENTER);
//			        addButton.setFocusPainted(false);
//			        new SwingWorker<Void, Void>() {
//			            @Override
//			            protected Void doInBackground() {
//			                DatabaseHelper.loadAmbulanceDrivers(model);
//			                return null;
//			            }
//
//			            @Override
//			            protected void done() {
//			                int statusColIndex = model.findColumn("Status");
//			                table.getColumnModel().getColumn(statusColIndex).setCellRenderer(
//			                    new ButtonRendererEditor(table, model));
//			                table.getColumnModel().getColumn(statusColIndex).setCellEditor(
//			                    new ButtonRendererEditor(table, model));
//			            }
//			        }.execute();
//			       // JButton allocateButton = new JButton("Allocate");
//
//	           //     JPanel topPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//	               // topPanel2.add(addButton);
//	               // topPanel.add(allocateButton);
//	                panel2.add(topPanel, BorderLayout.NORTH);
//	                panel2.add(scrollPane, BorderLayout.CENTER);
//			       // allocateButton.setFocusPainted(true);
//			        
//					}
//					
//						
//
//			        
//					
//			});
		 ambulance.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	 String tabTitle = "EMERGENCY CASE/ALLOCATE";
			         int index = tabbedPane.indexOfTab(tabTitle);
			         if (index != -1) {
			             // Tab already exists, just select it
			             tabbedPane.setSelectedIndex(index);
			             return;
			         }
			        JPanel panel2 = new JPanel(new BorderLayout());
			        JScrollPane scrollPane = new JScrollPane();
			        JTable table = new JTable();
			        scrollPane.setViewportView(table);

			        JButton addButton = new JButton("Add");
			        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			        topPanel.add(addButton);

			        panel2.add(topPanel, BorderLayout.NORTH);
			        panel2.add(scrollPane, BorderLayout.CENTER);

			        // Only add the tab if not already added
//			        int index = tabbedPane.indexOfTab("Emergency Cases/Allocate");
//			        if (index == -1) {
//			            tabbedPane.addTab("Emergency Cases/Allocate", panel2);
//			        }
//			        tabbedPane.setSelectedComponent(panel2);

			        // Run DB loading in background
			        int delay=5000;
			        new javax.swing.Timer(delay,evt->{
			        new SwingWorker<Void, Void>() {
			            DefaultTableModel model;

			            @Override
			            protected Void doInBackground() {
			                model = new DefaultTableModel(
			                    new String[]{ "Driver Name", "Age", "vechile no", "no of assistant", "ambulance type", "phone No", "Status"}, 0);
			                try {
			                DatabaseHelper.loadAmbulanceDrivers(model);
			                }
			                catch(Exception ex) {
			                	ex.printStackTrace();
			                }
			                return null;
			            }

			            @Override
			            protected void done() {
			                table.setModel(model);
			                table.getColumnModel().getColumn(6).setCellRenderer(new ButtonRendererEditor(table, model));
			                table.getColumnModel().getColumn(6).setCellEditor(new ButtonRendererEditor(table, model));
			            }
			        }.execute();
			        }).start();
			        addButton.setFocusPainted(false);
			        addButton.addActionListener(evt -> new DataEntry2(table.getModel()));
			        addClosableTab(tabbedPane, tabTitle, panel2);
			       // tabbedPane.addTab(tabTitle, panel2);
			        tabbedPane.setSelectedComponent(panel2);
			    }
			});




        // Add tabs to tabbedPane
        tabbedPane.addTab("AR HOSPITALS", panel1);
        this.add(tabbedPane, BorderLayout.CENTER);
        

        // Add tabbedPane to frame
        this.setLayout(new BorderLayout());
        this.add(tabbedPane, BorderLayout.CENTER);
        //frame.setVisible(true);
    }
    private static void addClosableTab(JTabbedPane tabbedPane, String title, JPanel panel) {
        tabbedPane.addTab(title, panel);
        int index = tabbedPane.indexOfComponent(panel);
        tabbedPane.setTabComponentAt(index, new ClosableTabComponent(tabbedPane, panel, title));
    }
    private void showError(String message) {
        UIManager.put("Button.focus", new Color(0,0,0,0));
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

