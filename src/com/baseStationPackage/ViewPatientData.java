package com.baseStationPackage;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;


public class ViewPatientData extends ViewPatientDataBO implements ActionListener {


    private JFrame frame;
    private JPanel panelOne;
    private JPanel panelTwo;
    private JPanel panelMain;
    private JScrollPane jpane;
    private JButton lookUpButton;
    private JButton saveChangesButton;
    private JButton cancelChangesButton;
    private JComboBox searchByCombo;
    private JTextField searchByTextField;
    private JLabel searchByLabel;
    private JTable table;
    private Vector rowData = null;
    private Vector dataLabels;

    String select = "";
    String from = "";
    String where = "";
    String is = "";

    //test


    public ViewPatientData() {
        initialize();


    }
    public void initialize() {

        frame = new JFrame("View Patient Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,200);
        frame.setResizable(true);
        frame.setLayout(new BorderLayout());

        panelMain = new JPanel();
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));

        panelOne = new JPanel();
        panelOne.setLayout(new FlowLayout());


        table = new JTable();



        panelTwo = new JPanel();
        panelTwo.setLayout(new BorderLayout());
        //jpane = new JScrollPane(table);
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,
                5, true), "Search Results", TitledBorder.CENTER, TitledBorder.TOP);
        panelTwo.setBorder(border);
        panelTwo.add(table, BorderLayout.CENTER);
        panelTwo.add(table.getTableHeader(), BorderLayout.NORTH);



        searchByLabel = new JLabel("Search By: ");
        searchByTextField = new JTextField(20);
        dataLabels = getLabelData();
        searchByCombo = new JComboBox(dataLabels);

        lookUpButton = new JButton("Look Up");
        lookUpButton.setActionCommand("Look Up");
        lookUpButton.addActionListener(this);

        saveChangesButton = new JButton("Save Changes");
        saveChangesButton.setActionCommand("Save Changes");
        saveChangesButton.addActionListener(this);

        cancelChangesButton = new JButton("Cancel Changes");
        cancelChangesButton.setActionCommand("Cancel Changes");
        cancelChangesButton.addActionListener(this);

        panelOne.add(searchByLabel);
        panelOne.add(searchByCombo);
        panelOne.add(searchByCombo);
        panelOne.add(searchByTextField);
        panelOne.add(lookUpButton);
        panelOne.add(saveChangesButton);
        panelOne.add(cancelChangesButton);


        panelMain.add(panelOne);
        panelMain.add(panelTwo);
        frame.add(new JScrollPane(panelMain));
        frame.setVisible(true);
        isEditing(false);
    }


    public void lookUp() {
        String searchBySelection = searchByCombo.getSelectedItem().toString();
        String searchByText = searchByTextField.getText();
        rowData = getRowData(searchBySelection, searchByText);
        //update rows of data
        ((DefaultTableModel) table.getModel()).setDataVector(rowData, dataLabels);
        isEditing(true);
    }

    public void saveChanges() {
     //edit database here
        Vector changedData = new Vector(table.getColumnCount());
        for(int i = 0; i< table.getColumnCount(); i++) {
            Object value = table.getValueAt(0,i);
            changedData.add(i,value);
        }
        updateDatabase(changedData);
        isEditing(false);
    }

    public void cancelChanges() {
        isEditing(false);
    }

    public void isEditing(boolean editing) {
        saveChangesButton.setVisible(editing);
        cancelChangesButton.setVisible(editing);
        searchByLabel.setVisible(!editing);
        searchByCombo.setVisible(!editing);
        searchByTextField.setVisible(!editing);
        lookUpButton.setVisible(!editing);
        table.setVisible(editing);
    }



    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Look Up")) {
            lookUp();
        }
        if(e.getActionCommand().equals("Save Changes")) {
            saveChanges();
        }
        if(e.getActionCommand().equals("Cancel Changes")) {
            cancelChanges();
        }


    }
    public static void main(String[] args)
    {
        new ViewPatientData();
    }

}
