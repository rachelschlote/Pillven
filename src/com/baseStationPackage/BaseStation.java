package com.baseStationPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BaseStation implements ActionListener {

    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem viewPatientData;
    private JMenuItem viewMedSched;
    private JMenu options;
    private JMenuItem resetPassword;
    private JMenuItem addNewUser;
    private JFrame frame;

    public BaseStation() {

        frame = new JFrame("Home");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 250));
        frame.setLayout(new FlowLayout());
        frame.setResizable(false);

        //buttons
        menuBar= new JMenuBar();

        file = new JMenu("File");
        file.setActionCommand("File");
        file.addActionListener(this);

        viewPatientData = new JMenuItem("View Patient Data");
        viewPatientData.setActionCommand("View Patient Data");
        viewPatientData.addActionListener(this);

        viewMedSched = new JMenuItem("View Medication Schedule");
        viewMedSched.setActionCommand("View Medication Schedule");
        viewMedSched.addActionListener(this);

        options = new JMenu("Options");
        options.setActionCommand("Options");
        options.addActionListener(this);

        resetPassword = new JMenuItem("Reset Password");
        resetPassword.setActionCommand("Reset Password");
        resetPassword.addActionListener(this);

        menuBar.add(file);
        menuBar.add(options);
        file.add(viewPatientData);
        file.add(viewMedSched);
        options.add(resetPassword);


        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        new BaseStation();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("View Patient Data")) {
            frame.dispose();
            new ViewPatientData();
        }
        if(e.getActionCommand().equals("View Medication Schedule")) {
            frame.dispose();
            new Scheduling();
        }
        if(e.getActionCommand().equals("Reset Password")) {
            frame.dispose();
            new ResetPassword();
        }

    }
}
