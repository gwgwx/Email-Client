/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.emailclient.services;

import com.mycompany.emailclient.controller.ModelAccess;
import com.mycompany.emailclient.model.EmailAccountBean;
import com.mycompany.emailclient.model.EmailConstants;
import com.mycompany.emailclient.model.folder.EmailFolderBean;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class CreateAndRegisterEmailAccountService extends Service<Integer>{

    private String emailAddress;
    private String password;
    private EmailFolderBean<String> folderRoot;
    private ModelAccess modelAccess;

    public CreateAndRegisterEmailAccountService(String emailAddress, String password,
            EmailFolderBean<String> folderRoot, ModelAccess modelAccess) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.folderRoot = folderRoot;
        this.modelAccess = modelAccess;
    }

    public CreateAndRegisterEmailAccountService(String emailAddress, String password, ModelAccess modelAccess) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.modelAccess = modelAccess;
    }

 
    protected Task<Integer> createTask() {
        return new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                EmailAccountBean emailAccount = new EmailAccountBean(emailAddress, password);
                if (emailAccount.getLoginState() == EmailConstants.LOGIN_STATE_SUCCEDED) {
                    modelAccess.addAccount(emailAccount);
                    EmailFolderBean<String> emailFolderBean = new EmailFolderBean<String>(emailAddress);
                    folderRoot.getChildren().add(emailFolderBean);
                    FetchFoldersService fetchFoldersService = new FetchFoldersService(emailFolderBean, emailAccount, modelAccess);
                    fetchFoldersService.start();

                }
                return emailAccount.getLoginState();
            }
        };
    }
}
