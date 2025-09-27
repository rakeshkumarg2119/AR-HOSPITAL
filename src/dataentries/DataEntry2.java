package dataentries;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

import database.DatabaseHelper;
public class DataEntry2 extends JFrame implements ActionListener {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Components
	JFrame frame;
    JTextField DrivernameField, ageField, vechilenoField, noofassField, ambutypeField,phnoField;
    JButton submitButton;
    JTextArea displayArea;
    DefaultTableModel tableModel;

    public  DataEntry2( TableModel tableModel) {
    	if (tableModel instanceof DefaultTableModel) {
            this.tableModel = (DefaultTableModel) tableModel;
        } else {
            throw new IllegalArgumentException("Provided model is not a DefaultTableModel.");
        }
        // Frame setup
    	ImageIcon icon = new ImageIcon("C:/Users/HP/Downloads/icons8-hospital-32.png");
	     Image image= icon.getImage();
	     this.setIconImage(image);
    	
        setTitle("Data Entry Form");
        setSize(400, 400);
        setLayout(new GridLayout(10, 5,5,10));
        setLocationRelativeTo(null); // center on screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Labels and fields
        DrivernameField = new JTextField();
        ageField = new JTextField();
        vechilenoField = new JTextField();
        noofassField = new JTextField();
        ambutypeField = new JTextField();
        phnoField=new JTextField(10);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

        add(new JLabel("Driver Name:")); add(DrivernameField);
        add(new JLabel("Age:")); add(ageField);
        add(new JLabel("vechile no:")); add(vechilenoField);
        add(new JLabel("No of assistant:")); add(noofassField);
        add(new JLabel("Ambulance Type:")); add(ambutypeField);
        add(new JLabel("phone No:")); add(phnoField);
        add(new JLabel()); add(submitButton);

        // Make frame visible
        setVisible(true);
    }

    // Handle button click
    public void actionPerformed(ActionEvent e) {
    	
             String Drivername=   DrivernameField.getText().trim();
               String ageText= ageField.getText().trim();
               String vechilno= vechilenoField.getText().trim();
               String noofassText= noofassField.getText().trim();
                String ambu=ambutypeField.getText().trim();
              String phno=  phnoField.getText().trim();
              if(Drivername.isEmpty() || ageText.isEmpty() || vechilno.isEmpty() || noofassText.isEmpty()|| ambu.isEmpty()|| phno.isEmpty()) {
            	  showError("Please fill all fields.");
            	  return;// stop processing, don't insert or close
              }
              if (!Drivername.matches("[a-zA-Z ]+")) {
            	  
            	  showError("Name must contain only letters and spaces.");
            	    return;
            	}
              if (!phno.matches("(\\+91)?\\d{10}")) {
            	  showError("Phone number must be 10 digits (with optional +91).");
            	  return;
            	}
              try {
           		int age=Integer.parseInt(ageText);
           		int noofass=Integer.parseInt(noofassText);
           		String[] rowData= {Drivername,ageText,vechilno,noofassText,ambu,phno,"Allocate"};
            
            try {
            DatabaseHelper.insertAmbulanceDriver(Drivername, age, vechilno, noofass, ambu, phno);// Add to JTable
            tableModel.addRow(rowData);
            dispose(); // Close the form
            }
            catch(Exception dbEx) {
            	 dbEx.printStackTrace();  // For logging
            	 showError("Database Error:");
            }
              }catch(NumberFormatException ex) {
            	showError("age or no of assistant is invalid");
    
              }
    }
    private void showError(String message) {
        UIManager.put("Button.focus", new Color(0,0,0,0));
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

