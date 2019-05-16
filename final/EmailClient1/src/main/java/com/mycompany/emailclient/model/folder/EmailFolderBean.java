/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.emailclient.model.folder;

import com.mycompany.emailclient.model.EmailMessageBean;
import com.mycompany.emailclient.services.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javax.mail.Flags.Flag;
import javax.mail.Message;
import javax.mail.MessagingException;

/**
 *
 * @author Andreas
 */
public class EmailFolderBean<T> extends TreeItem<String> {

    private boolean topElement = false;
    private int unreadMessageCount;
    private String name;
    @SuppressWarnings("unused")
    private String completeName;
    private ObservableList<EmailMessageBean> data = FXCollections.observableArrayList();

    // @param value
    public EmailFolderBean(String value) {
        super(value, ViewFactory.defaultFactory.resolveIcon(value));
        this.name = value;
        this.completeName = value;
        data = null;
        topElement = true;
        this.setExpanded(true);
    }

    public EmailFolderBean(String value, String completeName) {
        super(value, ViewFactory.defaultFactory.resolveIcon(value));
        this.name = value;
        this.completeName = completeName;

    }

    private void updateValue() {
        if (unreadMessageCount > 0) {
            this.setValue((String) (name + "(" + unreadMessageCount + ")"));
        } else {
            this.setValue(name);
        }
    }

    public void incrementUnreadMessageCount(int newMessages) {
        unreadMessageCount = unreadMessageCount + newMessages;
        updateValue();
    }

    public void decrementUnreadMessageCount() {
        unreadMessageCount--;
        updateValue();
    }

    public void addEmail(int possition, Message message) throws MessagingException {
        boolean isRead = message.getFlags().contains(Flag.SEEN);
        EmailMessageBean emailMessageBean = new EmailMessageBean(message.getFrom()[0].toString(),
                message.getSubject(),
                message.getSize(),
                isRead,
                message.getSentDate(),
                message);
        if (possition < 0) {
            data.add(emailMessageBean);
        } else {
            data.add(possition, emailMessageBean);
        }
        if (!isRead) {
            incrementUnreadMessageCount(1);
        }

    }

    public boolean isTopElement() {
        return topElement;
    }

    public ObservableList<EmailMessageBean> getData() {
        return data;
    }

}
