package dataentries;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

import database.DatabaseHelper;



public class DataEntry extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
    // Components
	JFrame frame;
    JTextField nameField, ageField, emailField, departmentField, positionField;
    JButton submitButton;
    JTextArea displayArea;
    DefaultTableModel tableModel;

    public  DataEntry( DefaultTableModel tableModel) {
        // Frame setup
    	ImageIcon icon = new ImageIcon("C:/Users/HP/Downloads/icons8-hospital-32.png");
	     Image image= icon.getImage();
	     this.setIconImage(image);
    	this.tableModel=tableModel;
        setTitle("Data Entry Form");
        setSize(400, 400);
        setLayout(new GridLayout(6, 2,2,5));
        setLocationRelativeTo(null); // center on screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Labels and fields
        nameField = new JTextField();
        ageField = new JTextField();
        emailField = new JTextField();
        departmentField = new JTextField();
        positionField = new JTextField();

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

        add(new JLabel("Name:")); add(nameField);
        add(new JLabel("Age:")); add(ageField);
        add(new JLabel("Email:")); add(emailField);
        add(new JLabel("Department:")); add(departmentField);
        add(new JLabel("Position:")); add(positionField);
        add(new JLabel()); add(submitButton);

        // Make frame visible
        setVisible(true);
    }

    // Handle button click
    public void actionPerformed(ActionEvent e) {
    	String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();
        String email = emailField.getText().trim();
        String department = departmentField.getText().trim();
        String position = positionField.getText().trim();
        
        if(name.isEmpty() || ageText.isEmpty() || email.isEmpty() || department.isEmpty()||position.isEmpty()) {
        	showError("Please fill all fields.");
        	return;
    	}
        if (!name.matches("[a-zA-Z ]+")) {
        	showError("Name must contain only letters and spaces.");
    	    return;
        }
       int age;
        try {
        	
        	age=Integer.parseInt(ageText);
        	
        }
        catch(NumberFormatException ex) {
        	showError("Please enter a valid age.");
             return; // Stop if age is not a valid integer
        }
        
         // Use DatabaseHelper to insert
        try {
    		DatabaseHelper.insertStaff(name, age, email, department, position);

    		// Refresh table
    		DatabaseHelper.loadAllStaff(tableModel);
            dispose(); // Close the form
        }
        catch(Exception x) {
        x.printStackTrace(); // Or log this better
        showError("Error saving to database.");
        }
    }
	
    private void showError(String message) {
        UIManager.put("Button.focus", new Color(0,0,0,0));
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
  
}
