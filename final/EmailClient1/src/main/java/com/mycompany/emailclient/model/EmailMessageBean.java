/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.emailclient.model;

import com.mycompany.emailclient.model.table.AbstractTableItem;
import com.mycompany.emailclient.model.table.FormatableInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

public class EmailMessageBean extends AbstractTableItem {
    
    private SimpleStringProperty sender;
    private SimpleStringProperty subject;
    private SimpleObjectProperty<FormatableInteger> size;
    private Message messageReference;
    private SimpleObjectProperty<Date> date;

    //attachments handling
    private List<MimeBodyPart> attachmentsList = new ArrayList<MimeBodyPart>();
    private StringBuffer attachmentsNames = new StringBuffer();
    
    public EmailMessageBean(String Sender, String Subject, int size, boolean isRead,Date date, Message messageReference) {
        super(isRead);
        this.sender = new SimpleStringProperty(Sender);
        this.subject = new SimpleStringProperty(Subject);
        this.size = new SimpleObjectProperty<FormatableInteger>(new FormatableInteger(size));
        this.messageReference = messageReference;
        this.date = new SimpleObjectProperty<Date>(date);
    }
    @Override
    public String toString() {
        return "EmailMessageBean"
                + "sender=" + sender.get() +
                ", subject=" + subject.get() +
                ", size=" + size.get();
                
    }

    public Date getDate() {
        return date.get();
    }
    
    
    public Message getMessageReference() {
        return messageReference;
    }
    
    public String getSender() {
        return sender.get();
    }
    
    public String getSubject() {
        return subject.get();
    }
    
    public FormatableInteger getSize() {
        return size.get();
    }
    
    public List<MimeBodyPart> getAttachmentsList() {
        return attachmentsList;
    }
    
    public String getAttachmentsNames() {
        return attachmentsNames.toString();
    }
    
    public void addAttachment(MimeBodyPart mbp) {
        attachmentsList.add(mbp);
        try {
            attachmentsNames.append(mbp.getFileName() + ";");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
    public boolean hasAttachments() {
        
        System.out.println();
        return attachmentsList.size() > 0;
    }

    //clear methods
    public void clearAttachments() {
        attachmentsList.clear();
        attachmentsNames.setLength(0);
    }
    
    
}
