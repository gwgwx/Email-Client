/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.emailclient.model.table;

import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author Andreas
 */
public class AbstractTableItem {

    private final SimpleBooleanProperty read = new SimpleBooleanProperty();

    public AbstractTableItem(boolean isRead) {
        this.setRead(isRead);
    }

    public SimpleBooleanProperty getReadProperty() {
        return read;
    }

    public void setRead(boolean isRead) {
        read.set(isRead);
    }

    public boolean isRead() {
        return read.get();
    }
}
