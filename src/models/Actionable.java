/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package models;

import javax.swing.JTable;
/**
 *
 * @author D A I S U K E
 */
public interface Actionable {
    void save();
    void update(JTable table);
    void delete(JTable table);
    void search(JTable table);
    void loadData(JTable table);
    
}
