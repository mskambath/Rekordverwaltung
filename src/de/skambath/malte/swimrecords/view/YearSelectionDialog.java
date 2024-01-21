package de.skambath.malte.swimrecords.view;

import javax.swing.*;

import java.awt.BorderLayout;
import java.util.Calendar;

import javax.swing.*;
import java.util.Calendar;

public class YearSelectionDialog extends JDialog {
    private JComboBox<Integer> yearComboBox;
    private JFrame parentFrame;
    private boolean Submitted = true;
    
    public YearSelectionDialog(JFrame parentFrame) {
    	super(parentFrame, "Select Year", true);
    	this.parentFrame = parentFrame;
        initializeYearComboBox();
        
    }

    private void initializeYearComboBox() {
    	

        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setDefaultCloseOperation(ABORT);
        
        //this.add(this, BorderLayout.CENTER);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Integer[] years = new Integer[6];
        for (int i = 0; i < years.length; i++) {
        	years[i] = currentYear - i;
        }
        yearComboBox = new JComboBox<>(years);
        add(yearComboBox);

        // OK and Cancel buttons
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Button listeners
        final Integer[] selectedYear = {null};
        okButton.addActionListener(e -> {
        	this.Submitted = true;
        	this.dispose();
        	});
        cancelButton.addActionListener(e -> {
        	this.Submitted = false;
        	this.dispose();
        	});
    	
    }

    public int getSelectedYear() {
        return (int) yearComboBox.getSelectedItem();
    }

    public void setSelectedYear(int year) {
        yearComboBox.setSelectedItem(year);
    }
    
    public boolean showModal() {
        // Create a modal dialog
        

        // Display the dialog
        this.pack();
        this.setLocationRelativeTo(parentFrame);
        this.setVisible(true);

        return this.Submitted;
    }
}
