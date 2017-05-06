package com.baseStationPackage;


import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scheduling extends SchedulingBO implements ActionListener{
    private JFrame frame;
    private JPanel panelOne;
    private JPanel panelTwo;
    private JPanel panelThree;
    private JPanel panelMain;
    private JTable table;
    private JLabel med;
    private String selectedMed="";
    private Vector header;
    private Vector data;
    private JButton[] myButtons = new JButton[7];
    private  int rows = 0;
    private DefaultTableModel dmodel;
    private JButton saveButton = new JButton("Save");
    private JButton cancelButton = new JButton("Cancel");
    private JButton addNewRowButton = new JButton("New Time");
    private JLabel removeLable = new JLabel("Remove Time: ");
    private JComboBox removeRowCombo;
    private JButton removeButton = new JButton("Remove");
    private int currentMedNum;
    private Boolean hasPrevTab = false;
    private Boolean isNew=false;
    private int patNum = 1;
    private int quantIndex = 0;


    Scheduling() {
        initialize();
    }

    public void initialize() {
        frame = new JFrame("Medication Schedule For Patient Number: ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,300);
        frame.setResizable(true);
        frame.setLayout(new BorderLayout());

        panelMain = new JPanel();
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));

        panelOne = new JPanel();
        panelOne.setLayout(new FlowLayout());


        String[] columnNames = {" ", "Quantity","Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        Object data[][]={};
        dmodel=new DefaultTableModel(data,columnNames);
        dmodel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if(isNew) {
                    rePopTimeCombo();
                    isNew = false;
                }
            }
        });
        table = new JTable(dmodel);
        for(int i = 2; i<9;i++){
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

        TableColumn quantCol = table.getColumnModel().getColumn(1);

        JComboBox quantBox = new JComboBox();

        for(int g = 1;g<=5;g++) {
            quantBox.addItem(Integer.toString(g));
        }
        quantCol.setCellEditor(new DefaultCellEditor(quantBox));

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

        for (int i=0; i<7; i++)
        {
            myButtons[i] = new JButton((String) ((Vector) medNames.get(0)).get(i));
            myButtons[i].setSize(100, 40);
            myButtons[i].setActionCommand(Integer.toString(i));
            myButtons[i].addActionListener(this);
            myButtons[i].setBackground(Color.white);
            panelOne.add(myButtons[i]);
        }

        panelThree = new JPanel();
        panelThree.setLayout(new FlowLayout());

        saveButton.setActionCommand("Save");
        saveButton.addActionListener(this);
        cancelButton.setActionCommand("Cancel");
        cancelButton.addActionListener(this);
        addNewRowButton.addActionListener(this);
        addNewRowButton.setActionCommand("Add");
        removeRowCombo = new JComboBox();
        removeButton.addActionListener(this);
        removeButton.setActionCommand("Remove");
        panelThree.add(saveButton);
        panelThree.add(cancelButton);
        panelThree.add(addNewRowButton);
        panelThree.add(removeLable);
        //panelThree.add(removeRowCombo);
        panelThree.add(removeButton);


        panelMain.add(panelOne);
        panelMain.add(panelTwo);
        panelMain.add(panelThree);
        frame.add(new JScrollPane(panelMain));
        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        String medTimeString = null;
        String groupDay = "";
        String groupTime = "";
        String groupWhole = "";
        String savedTimeString = "";
        String savedQuantString = "";
        quantIndex = 0;

        if(e.getActionCommand().matches("\\d{1}")) {
            if(hasPrevTab) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
               int diologAns = JOptionPane.showConfirmDialog(frame,"Do you want to save any changes made?","Message",dialogButton);
               if(diologAns == JOptionPane.YES_OPTION) {
                   saveButton.doClick();
               }
            }
            hasPrevTab = true;
            //panelThree.remove(removeRowCombo);
            Vector timeData = new Vector();
            int count = dmodel.getRowCount();
            if(count!=0) {
                for (int r = 0; r<count;r++) {
                    dmodel.removeRow(0);
                }
            }
            for(int i=0;i<7;i++){
                myButtons[i].setBackground(Color.white);
                if(e.getActionCommand().equals(Integer.toString(i))) {
                    myButtons[i].setBackground(Color.blue);
                    currentMedNum = i;
                    selectedMed = myButtons[i].getText();
                    Vector medTimes = getMedTimes();
                    medTimeString = (String) ((Vector) medTimes.get(0)).get(i);
                }
            }
            String regexDay = "(([UMTWRFS]+))";
            Pattern patternDay =  Pattern.compile(regexDay);
            Matcher matcherDay;

            String regexTime = "(([\\d{2}:\\d{2}]{5}))";
            Pattern patternTime =  Pattern.compile(regexTime);
            Matcher matcherTime;

            String regexWhole = "(([UMTWRFS]+)([\\d{2}:\\d{2}]+))";
            Pattern patternWhole =  Pattern.compile(regexWhole);
            Matcher matcherWhole = patternWhole.matcher(medTimeString);
            while(matcherWhole.find()) {
                groupWhole = matcherWhole.group();
                matcherTime = patternTime.matcher(groupWhole);
                matcherDay = patternDay.matcher(groupWhole);
                while(matcherDay.find()) {
                    groupDay = matcherDay.group();

                    while(matcherTime.find()) {
                        groupTime = matcherTime.group();
                        //timeData.addElement(groupTime.toString());

                        dmodel.addRow(new Object[]{groupTime,getMedQuantBO(patNum,quantIndex,currentMedNum), groupDay.contains("U"), groupDay.contains("M"), groupDay.contains("T"),
                                groupDay.contains("W"), groupDay.contains("R"), groupDay.contains("F"), groupDay.contains("S")});
                    quantIndex++;
                    }
                }
            }

            rePopTimeCombo();


            isNew = true;
        }
        else if(e.getActionCommand().equals("Save")) {

            //duplicate times not allowed
            Vector savedSchedule = dmodel.getDataVector();
            String arrTime[] = new String[savedSchedule.size()];
            for(int i = 0;i< (savedSchedule.size());i++) {
                savedTimeString = (String) ((Vector) savedSchedule.get(i)).get(0);
                arrTime[i] = savedTimeString;
                savedQuantString += (String) ((Vector) savedSchedule.get(i)).get(1);

                for(int k = i+1;k<savedSchedule.size();k++) {
                    String C = (String) ((Vector) savedSchedule.get(k)).get(0);
                    if(savedTimeString.equals(C)) {
                        JOptionPane.showMessageDialog(frame, "You may not enter duplicate time " + C + ". Please try again.");
                    }
                }
            }
            setMedQuantBO(patNum,currentMedNum+1,savedQuantString);
            //duplicate days grouped
            String arrDay[] = new String[savedSchedule.size()];
            for(int j = 0;j< (savedSchedule.size());j++) {
                String savedDayString = "";
               if(dmodel.getValueAt(j,2).equals(true)) {
                    savedDayString += "U";
               }
                if(dmodel.getValueAt(j,3).equals(true)) {
                    savedDayString += "M";
                }
                if(dmodel.getValueAt(j,4).equals(true)) {
                    savedDayString += "T";
                }
                if(dmodel.getValueAt(j,5).equals(true)) {
                    savedDayString += "W";
                }
                if(dmodel.getValueAt(j,6).equals(true)) {
                    savedDayString += "R";
                }
                if(dmodel.getValueAt(j,7).equals(true)) {
                    savedDayString += "F";
                }
                if(dmodel.getValueAt(j,8).equals(true)) {
                    savedDayString += "S";
                }
                arrDay[j] = savedDayString;
            }

            String updateString = "";
            for(int v = 0; v<savedSchedule.size();v++) {
                updateString += arrDay[v] +arrTime[v];
            }


            setMedSched(1,currentMedNum+1,"'" + updateString + "'");
        }
        else if(e.getActionCommand().equals("Cancel")) {
            frame.dispose();
            new Scheduling();
        }
        else if(e.getActionCommand().equals("Add")) {
            dmodel.addRow(new Object[]{"","1", false,false,false,false,false,false,false});
            isNew = true;
        }
        else if(e.getActionCommand().equals("Remove")) {
            Vector savedSchedule = dmodel.getDataVector();
            for(int i = 0;i< (savedSchedule.size());i++) {
                if(removeRowCombo.getSelectedItem().toString().equals((String) ((Vector) savedSchedule.get(i)).get(0))) {
                    dmodel.removeRow(i);
                    removeRowCombo.removeItemAt(i);
                }

            }


        }
    }
    private void rePopTimeCombo() {
        Vector pop = new Vector();
       panelThree.remove(removeRowCombo);
        for(int i = 0;i<dmodel.getRowCount();i++) {
            pop.addElement(dmodel.getValueAt(i,0));
        }
        removeRowCombo = new JComboBox(pop);
        panelThree.add(removeRowCombo);
    }




    public static void main(String[] args)
    {
        new Scheduling();
    }
}
