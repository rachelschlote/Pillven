package com.baseStationPackage;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Vector;

public class Scheduling extends SchedulingBO implements ActionListener{
    private JFrame frame;
    private JPanel panelOne;
    private JPanel panelTwo;
    private JPanel panelMain;
    private JTable table;
    private JLabel med;
    private String selectedMed=null;
    private Vector header;
    private Vector data;
    private JButton[] myButtons = new JButton[6];
    private  int rows = 0;
    private DefaultTableModel dmodel;

    Scheduling() {
        initialize();
    }

    public void initialize() {
        frame = new JFrame("Medication Schedule");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,200);
        frame.setResizable(true);
        frame.setLayout(new BorderLayout());

        panelMain = new JPanel();
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));

        panelOne = new JPanel();
        panelOne.setLayout(new FlowLayout());


        String[] columnNames = {" ","Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        Object data[][]={};
        dmodel=new DefaultTableModel(data,columnNames);
        table = new JTable(dmodel);
        for(int i = 1; i<8;i++){
            TableColumn tc = table.getColumnModel().getColumn(i);
            tc.setCellEditor(table.getDefaultEditor(Boolean.class));
            tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
        }


        Vector medTimes = getMedTimes();
        String medTimeString = (String) ((Vector) medTimes.get(0)).get(0);


        TableColumn comboCol = table.getColumnModel().getColumn(0);

        JComboBox comboBox = new JComboBox();

        for(int i = 1;i<24;i++) {
            for(int j = 0;j<60;j+=15) {
                String A=Integer.toString(i);
                String B=Integer.toString(j);
                if(i<10) {
                    A="0"+A;
                }
                if(j==0) {
                    B="00";
                }
                comboBox.addItem(A+":"+B);

            }
        }


        comboCol.setCellEditor(new DefaultCellEditor(comboBox));


        panelTwo = new JPanel();
        panelTwo.setLayout(new BorderLayout());
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,
                5, true), "Scheduling", TitledBorder.LEFT, TitledBorder.TOP);
        panelTwo.setBorder(border);
        panelTwo.add(table, BorderLayout.CENTER);
        panelTwo.add(table.getTableHeader(), BorderLayout.NORTH);

        med = new JLabel("Medication: ");



        panelOne.add(med);



        Vector medNames = getMedNames();

        for (int i=0; i<6; i++)
        {
            myButtons[i] = new JButton((String) ((Vector) medNames.get(0)).get(i));
            myButtons[i].setSize(100, 40);
            myButtons[i].setActionCommand(Integer.toString(i));
            myButtons[i].addActionListener(this);
            myButtons[i].setBackground(Color.white);
            panelOne.add(myButtons[i]);
        }
        panelMain.add(panelOne);
        panelMain.add(panelTwo);
        frame.add(new JScrollPane(panelMain));
        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        String medTimeString = null;
        String days = "";
        String time = "";
        for(int i=0;i<6;i++){
            myButtons[i].setBackground(Color.white);
            if(e.getActionCommand().equals(Integer.toString(i))) {
                myButtons[i].setBackground(Color.red);
                selectedMed = myButtons[i].getText();
                Vector medTimes = getMedTimes();
                medTimeString = (String) ((Vector) medTimes.get(0)).get(i);
            }
        }
        //getdays
        int position = 0;
        while(position<medTimeString.length()) {
            days = "";
            while(Character.isLetter(medTimeString.charAt(position)) && position<medTimeString.length()) {
                days += medTimeString.charAt(position);
                position++;
            }
            //get corresponding times
            while(!Character.isLetter(medTimeString.charAt(position)) && position<(medTimeString.length()-4)) {
                time = "" + medTimeString.charAt(position) + medTimeString.charAt(position+1)
                        + medTimeString.charAt(position+2) + medTimeString.charAt(position+3)
                        + medTimeString.charAt(position+4);
                position+=5;
                //row for first time
                dmodel.addRow(new Object[]{time, days.contains("U"), days.contains("M"), days.contains("T"),
                        days.contains("W"), days.contains("R"), days.contains("F"), days.contains("S")});
                rows++;
                time = "";
                //add break to get rid of array out of bounds
            }
        }


    }





    public static void main(String[] args)
    {
        new Scheduling();
    }
}
