import com.google.gson.Gson;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ManagePurchaseUI {

    public JFrame view;

    public JButton btnLoad = new JButton("Load Purchase");
    public JButton btnSave = new JButton("Save Purchase");

    public JTextField txtPurchaseID = new JTextField(20);
    public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtProductID = new JTextField(20);
    public JTextField txtCost = new JTextField( 20 );
    public JTextField txtTax = new JTextField( 20 );
    public JTextField txtDate = new JTextField( 20 );
    public JTextField txtPrice = new JTextField(20);
    public JTextField txtQuantity = new JTextField(20);
    public JTextField txtTotalCost = new JTextField(20);
    PurchaseModel purchase = null;

    public ManagePurchaseUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Update Purchase Information");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnLoad);
        panelButtons.add(btnSave);
        view.getContentPane().add(panelButtons);

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("PurchaseID"));
        line1.add(txtPurchaseID);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("customerID"));
        line2.add(txtCustomerID);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("productID"));
        line3.add(txtProductID);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("price"));
        line4.add(txtPrice);
        view.getContentPane().add(line4);

        JPanel line5 = new JPanel(new FlowLayout());
        line5.add(new JLabel("Quantity"));
        line5.add(txtQuantity);
        view.getContentPane().add(line5);

        JPanel line6 = new JPanel(new FlowLayout());
        line6.add(new JLabel("Cost"));
        line6.add(txtCost);
        view.getContentPane().add(line6);

        JPanel line7 = new JPanel(new FlowLayout());
        line7.add(new JLabel("Tax"));
        line7.add(txtTax);
        view.getContentPane().add(line7);

        JPanel line8 = new JPanel(new FlowLayout());
        line8.add(new JLabel("Total Cost"));
        line8.add(txtTotalCost);
        view.getContentPane().add(line8);

        JPanel line9 = new JPanel(new FlowLayout());
        line9.add(new JLabel("Date"));
        line9.add(txtDate);
        view.getContentPane().add(line9);


        btnLoad.addActionListener(new LoadButtonListerner());

        btnSave.addActionListener(new SaveButtonListener());

    }

    public void run() {
        view.setVisible(true);
    }

    class LoadButtonListerner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Gson gson = new Gson();
            String id = txtPurchaseID.getText();
            int purchaseID;

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!");
                return;
            }

            try {
                purchaseID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "PurchaseID is invalid!");
                return;
            }

            try {
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.GET_PURCHASE;
                msg.data = Integer.toString(purchaseID);
                output.println(gson.toJson(msg));

                msg = gson.fromJson(input.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null, "Purchase does not exists!");
                } else {
                    purchase = new PurchaseModel();
                    purchase = gson.fromJson(msg.data, PurchaseModel.class);
                    txtCustomerID.setText(Integer.toString(purchase.mCustomerID));
                    txtProductID.setText(Integer.toString(purchase.mProductID));
                    txtPrice.setText(Double.toString(purchase.mPrice));
                    txtQuantity.setText(Double.toString(purchase.mQuantity));
                    txtCost.setText(Double.toString(purchase.mCost));
                    txtTax.setText(Double.toString(purchase.mTax));
                    txtTotalCost.setText(Double.toString(purchase.mTotal));
                    txtDate.setText(purchase.mDate);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Gson gson = new Gson();
            String pid = txtPurchaseID.getText();

            if (pid.length() == 0) {
                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!");
                return;
            }

            try {
                purchase.mPurchaseID = Integer.parseInt(pid);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "PurchaseID is invalid!");
                return;
            }


            String prid = txtProductID.getText();
            try {
                purchase.mProductID = Integer.parseInt(prid);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Product is invalid!");
                return;
            }

            String cid = txtCustomerID.getText();
            try {
                purchase.mCustomerID = Integer.parseInt(cid);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Customer is invalid!");
                return;
            }



            String price = txtPrice.getText();
            try {
                purchase.mPrice = Double.parseDouble(price);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Price is invalid!");
                return;
            }

            String quant = txtQuantity.getText();
            try {
                purchase.mQuantity = Double.parseDouble(quant);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Quantity is invalid!");
                return;
            }

            String cost = txtCost.getText();
            try {
                purchase.mCost = Double.parseDouble(cost);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Cost is invalid!");
                return;
            }

            String tax = txtTax.getText();
            try {
                purchase.mTax = Double.parseDouble(tax);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "tax is invalid!");
                return;
            }

            String tot = txtTotalCost.getText();
            try {
                purchase.mTotal = Double.parseDouble(tot);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "total cost is invalid!");
                return;
            }



            String date = txtDate.getText();
            if (date.length() == 0) {
                JOptionPane.showMessageDialog(null, "date cannot be empty!");
                return;
            }
            purchase.mDate = date;

            try {
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.PUT_PURCHASE;
                msg.data = gson.toJson(purchase);
                output.println(gson.toJson(msg));

                msg = gson.fromJson(input.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null, "Purchase is NOT updated successfully!");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Purchase is UPDATED successfully!");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}