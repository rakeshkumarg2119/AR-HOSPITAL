package dataentries;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

import database.DatabaseHelper;
public class DataEntry3 extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//public static void main(String[]args) {
	private Runnable onSubmitCallback;
    // Components
	JFrame frame;
    JTextField paitentnameField, addressField, phnoField, vechilenoField;
    JButton submitButton;
    JTextArea displayArea;
    DefaultTableModel tableModel;
    public  DataEntry3( DefaultTableModel tableModel,Runnable onSubmitCallback) {
        // Frame setup
    	ImageIcon icon = new ImageIcon("C:/Users/HP/Downloads/icons8-hospital-32.png");
	     Image image= icon.getImage();
	     this.setIconImage(image);
    	this.tableModel=tableModel;
    	this.onSubmitCallback = onSubmitCallback;
        setTitle("Data Entry Form");
        setSize(400, 400);
        setLayout(new GridLayout(6, 2,2,5));
        setLocationRelativeTo(null); // center on screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Labels and fields
       paitentnameField = new JTextField();
        addressField = new JTextField();
        phnoField = new JTextField(10);
        vechilenoField = new JTextField();
        //positionField = new JTextField();

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

        add(new JLabel("Paitent Name:")); add(paitentnameField);
        add(new JLabel("Address:")); add(addressField);
        add(new JLabel("Phone No:")); add(phnoField);
        add(new JLabel("Vechile No:")); add(vechilenoField);
        //add(new JLabel("Position:")); add(positionField);
        add(new JLabel()); add(submitButton);

        // Make frame visible
        setVisible(true);
    }

    // Handle button click
    public void actionPerformed(ActionEvent e) {
    	
    	String patient = paitentnameField.getText().trim();;
    	String address = addressField.getText().trim();
    	String phone = phnoField.getText().trim();
    	String vehicleNo = vechilenoField.getText().trim();
    	
    	if(patient.isEmpty() || address.isEmpty() || phone.isEmpty() || vehicleNo.isEmpty()) {
    		
    		showError("Please fill all fields.");
    		return;
    	}
    	if (!patient.matches("[a-zA-Z ]+")) {
    		
    		showError("Name must contain only letters and spaces.");
    	    return;
    	}
    	if (!phone.matches("(\\+91)?\\d{10}")) {
    		
    	  showError("Phone number must be 10 digits (with optional +91).");
       	  return;
    	}
        try {
    	DatabaseHelper.insertAmbulanceCase(patient, address, phone, vehicleNo);
        // positionField.getText()
     	if (onSubmitCallback != null) {
             onSubmitCallback.run();
         }
             //tableModel.addRow(rowData); // Add to JTable
            dispose(); // Close the form
        }
        catch(Exception ex) {
        	 ex.printStackTrace();
        	 showError("Failed to save case. Please check input or try again.");
        	  
        }
        
    
	
	}
    private void showError(String message) {
        UIManager.put("Button.focus", new Color(0,0,0,0));
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}


