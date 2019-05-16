/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.emailclient.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Andreas
 */
public class Test {
    public static void main(String[] args) {
        final EmailAccountBean emailAccountBean = new EmailAccountBean("testMyClient021@gmail.com","javaclient12345");
        
        ObservableList<EmailMessageBean> data = FXCollections.observableArrayList();
        
    }
    
}
