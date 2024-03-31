/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/MDIApplication.java to edit this template
 */
package views;

import javax.swing.JFrame;
import models.User;

/**
 *
 * @author D A I S U K E
 */
public class MainForm extends javax.swing.JFrame {

    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        if(User.rolename.equals("Admin")){
            
        }else if(User.rolename.equals("Stock Manager")){
            USER_jMenu1.setVisible(false);
        }else{
            USER_jMenu1.setVisible(false);
            PRODUCT_jMenu2.setVisible(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        desktopPane1 = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        USER_jMenu1 = new javax.swing.JMenu();
        USERjMenuItem1 = new javax.swing.JMenuItem();
        RoleMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        PRODUCT_jMenu2 = new javax.swing.JMenu();
        PRODUCTjMenuItem1 = new javax.swing.JMenuItem();
        OrderItem = new javax.swing.JMenuItem();
        btnLogout = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        desktopPane.add(desktopPane1);
        desktopPane1.setBounds(0, 0, 0, 0);

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Open");
        fileMenu.add(openMenuItem);

        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setMnemonic('a');
        saveAsMenuItem.setText("Save As ...");
        saveAsMenuItem.setDisplayedMnemonicIndex(5);
        fileMenu.add(saveAsMenuItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        USER_jMenu1.setText("User Management");

        USERjMenuItem1.setText("User Management");
        USERjMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                USERjMenuItem1ActionPerformed(evt);
            }
        });
        USER_jMenu1.add(USERjMenuItem1);

        RoleMenuItem2.setText("Role");
        RoleMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RoleMenuItem2ActionPerformed(evt);
            }
        });
        USER_jMenu1.add(RoleMenuItem2);

        jMenuItem1.setText("User Role");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        USER_jMenu1.add(jMenuItem1);

        menuBar.add(USER_jMenu1);

        PRODUCT_jMenu2.setText("Stock Management");

        PRODUCTjMenuItem1.setText("Product Management");
        PRODUCTjMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PRODUCTjMenuItem1ActionPerformed(evt);
            }
        });
        PRODUCT_jMenu2.add(PRODUCTjMenuItem1);

        OrderItem.setText("Order");
        OrderItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OrderItemActionPerformed(evt);
            }
        });
        PRODUCT_jMenu2.add(OrderItem);

        menuBar.add(PRODUCT_jMenu2);

        btnLogout.setText("Logout");
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
        });
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });
        menuBar.add(btnLogout);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void USERjMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_USERjMenuItem1ActionPerformed
        UserForm userform = new UserForm();
        desktopPane.add(userform);
        userform.setVisible(true);
    }//GEN-LAST:event_USERjMenuItem1ActionPerformed

    private void PRODUCTjMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PRODUCTjMenuItem1ActionPerformed
        ProductForm productform=new ProductForm();
        desktopPane.add(productform);
        productform.setVisible(true);
    }//GEN-LAST:event_PRODUCTjMenuItem1ActionPerformed

    private void RoleMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RoleMenuItem2ActionPerformed
       RoleForm roleform=new RoleForm();
       desktopPane.add(roleform);
       roleform.setVisible(true);
    }//GEN-LAST:event_RoleMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
       RoleUserForm roleuserform=new RoleUserForm();
       desktopPane.add(roleuserform);
       roleuserform.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        this.dispose();
        LogInForm login=new LogInForm();
        login.setVisible(true);
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void OrderItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OrderItemActionPerformed
        OrderForm orderf= new OrderForm();
        desktopPane.add(orderf);
       orderf.setVisible(true);
    }//GEN-LAST:event_OrderItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem OrderItem;
    private javax.swing.JMenu PRODUCT_jMenu2;
    private javax.swing.JMenuItem PRODUCTjMenuItem1;
    private javax.swing.JMenuItem RoleMenuItem2;
    private javax.swing.JMenu USER_jMenu1;
    private javax.swing.JMenuItem USERjMenuItem1;
    private javax.swing.JMenu btnLogout;
    private javax.swing.JDesktopPane desktopPane;
    public static javax.swing.JDesktopPane desktopPane1;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    // End of variables declaration//GEN-END:variables

}
