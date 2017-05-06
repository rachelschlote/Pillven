package com.baseStationPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ResetPassword implements ActionListener{

    private JLabel oldPassword = new JLabel("Please Enter Old Password");
    private JLabel newPassword = new JLabel("Please Enter New Password");
    private JLabel okLabel = new JLabel("                          Save Password");
    private JLabel confPassword = new JLabel("Please Confirm New Password");
    private JTextField oldP = new JTextField(15);
    private JTextField newP= new JTextField(15);
    private JTextField confP= new JTextField(15);
    private JButton ok = new JButton("Ok");

    private JFrame frame;

    public ResetPassword() {

        frame = new JFrame("Reset Password");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 250));
        frame.setLayout(new FlowLayout());
        frame.setResizable(false);

        //buttons
        ok.setActionCommand("Ok");
        ok.addActionListener((ActionListener) this);


        frame.add(oldPassword);
        frame.add(oldP);
        frame.add(newPassword);
        frame.add(newP);
        frame.add(confPassword);
        frame.add(confP);
        frame.add(okLabel);
        frame.add(ok);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        new BaseStation();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Ok")) {
            try {
                if (SignInBO.passwordCorrect("Nurse", oldPassword.getText())) {
                    if (newP.getText().equals(confP.getText())) {
                        //change database
                        frame.dispose();
                        new BaseStation();
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "New Passwords do not match. Please try again.");
                    }

                }
                else {
                    JOptionPane.showMessageDialog(null,
                            "This does not match the old password. Please try again.");
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }


        }
    }
}
