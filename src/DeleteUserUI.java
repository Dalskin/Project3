import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Calendar;

public class DeleteUserUI {
    public JFrame view;

    public JButton btnDelete = new JButton("Delete");
    public JButton btnCancel = new JButton("Cancel");

    public JTextField txtUserType = new JTextField(10);
    public JTextField txtUsername = new JTextField(10);
    public JTextField txtPassword = new JTextField(10);
    public JTextField txtFullname = new JTextField(10);

    public JLabel labFullname = new JLabel("Full Name: ");
    public JLabel labUserType = new JLabel("User Type: ");

    public JLabel labUsername = new JLabel("Username: ");
    public JLabel labPassword = new JLabel("Password: ");



    UserModel user;

    public DeleteUserUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Delete User");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel(new FlowLayout());
        line.add(new JLabel("User Type "));
        line.add(txtUserType);
        line.add(labUserType);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Username "));
        line.add(txtUsername);
        line.add(labUsername);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Password "));
        line.add(txtPassword);
        line.add(labPassword);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Full name "));
        line.add(txtFullname);
        line.add(labFullname);
        view.getContentPane().add(line);


        line = new JPanel(new FlowLayout());
        line.add(btnDelete);
        line.add(btnCancel);
        view.getContentPane().add(line);

    }
    public void run() {
        user = new UserModel();
        view.setVisible(true);
    }

}





