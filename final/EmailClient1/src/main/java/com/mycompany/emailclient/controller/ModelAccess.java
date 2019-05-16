/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.emailclient.controller;

import com.mycompany.emailclient.model.EmailAccountBean;
import com.mycompany.emailclient.model.EmailMessageBean;
import com.mycompany.emailclient.model.folder.EmailFolderBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.mail.Folder;

/**
 *
 * @author Andreas
 */
public class ModelAccess {
    
    private Map<String, EmailAccountBean> emailAccounts = new HashMap<String, EmailAccountBean>();
    private ObservableList<String> emailAccountsNames = FXCollections.observableArrayList();
    
    public ObservableList<String> getEmailAccountNames(){
        return emailAccountsNames;
    
    }
    
    public EmailAccountBean getEmailAccountByName(String name){
        return emailAccounts.get(name);
    }
    
    public void addAccount(EmailAccountBean account){
          emailAccounts.put(account.getEmailAdress(), account);
          emailAccountsNames.add(account.getEmailAdress());
    }

    private EmailMessageBean selectedMessage;
    private EmailFolderBean<String> selectedFolder;

    public EmailMessageBean getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(EmailMessageBean selectedMessage) {
        this.selectedMessage = selectedMessage;

    }

    public EmailFolderBean<String> getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(EmailFolderBean<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }
    private List<Folder> foldersList = new ArrayList<Folder>();

    public List<Folder> getFoldersList() {
        return foldersList;
    }

    public void setFoldersList(List<Folder> foldersList) {
        this.foldersList = foldersList;
    }

    public void addFolder(Folder folder) {
        foldersList.add(folder);
    }

}
