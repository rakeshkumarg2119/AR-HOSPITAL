package features;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import database.DatabaseHelper;
import dataentries.DataEntry3;

import java.awt.event.*;
import java.util.EventObject;

@SuppressWarnings("unused")
public class ButtonRendererEditor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2039994963692093954L;
	private JButton button;
    private String currentStatus;
    @SuppressWarnings("unused")
	private JTable table;
    private int row;
    @SuppressWarnings("unused")
	private DefaultTableModel model;

    public ButtonRendererEditor(JTable table, DefaultTableModel model) {
        this.table = table;
        this.model = model;
        button = new JButton();
        button.setOpaque(true);

        button.addActionListener(e -> {
            if ("Allocate".equals(currentStatus) || currentStatus == null) {
                // Open patient form with a callback to update status after submit
                new DataEntry3(model, () -> {
                    currentStatus = "Allocated";
                    button.setText(currentStatus);
                    button.setBackground(Color.RED);
                    model.setValueAt(currentStatus, row, 6); // Update the status in model
                    Object vehicleObj = model.getValueAt(row, 2);
                    if (vehicleObj != null) {
                    String vehicleNo = vehicleObj.toString(); // 3rd column is vehicle_no
                    DatabaseHelper.updateDriverStatus(vehicleNo, "OUT");
                    UIManager.put("Button.focus", new Color(0,0,0,0));
                    JOptionPane.showMessageDialog(table, "Its Hero Time!Hero will be updated soon!");
                    fireEditingStopped();
                    }
                });
            } else {
                currentStatus = "Allocate";
                button.setText(currentStatus);
                button.setBackground(Color.GREEN);
                UIManager.put("Button.focus", new Color(0,0,0,0));
                JOptionPane.showMessageDialog(table, "Mission Passed Repect+!Waiting for new Missions");
                model.setValueAt(currentStatus, row, 6);
                Object vehicleObj = model.getValueAt(row, 2);
                if (vehicleObj != null) {
                String vehicleNo = vehicleObj.toString();  //  3rd column is vehicle_no
                DatabaseHelper.updateDriverStatus(vehicleNo, "IN");
                fireEditingStopped();
                } }
        });
    }

    // TableCellRenderer method
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        currentStatus = (value == null) ? "Allocate" : value.toString();
        button.setText(currentStatus);

        if ("Allocate".equals(currentStatus)) {
            button.setBackground(Color.GREEN);
        } else if ("Allocated".equals(currentStatus)) {
            button.setBackground(Color.RED);
        } else {
            button.setBackground(UIManager.getColor("Button.background"));
        }
        return button;
    }

    // TableCellEditor method
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        this.row = row;
        currentStatus = (value == null) ? "Allocate" : value.toString();
        button.setText(currentStatus);

        if ("Allocate".equals(currentStatus)) {
            button.setBackground(Color.GREEN);
        } else if ("Allocated".equals(currentStatus)) {
            button.setBackground(Color.RED);
        } else {
            button.setBackground(UIManager.getColor("Button.background"));
        }
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return currentStatus;
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }
}
