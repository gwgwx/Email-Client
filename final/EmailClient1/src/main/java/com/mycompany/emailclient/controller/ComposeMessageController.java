/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.emailclient.controller;

import com.mycompany.emailclient.model.EmailConstants;
import com.mycompany.emailclient.services.EmailSenderService;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;

/**
 *
 * @author Andreas
 */
public class ComposeMessageController extends AbstractController implements Initializable {
    
    private List<File> attachments = new ArrayList<File>();
    
    @FXML
    private ChoiceBox<String> senderChoice;
    
    @FXML
    private TextField recipientField;
    
    @FXML
    private Label attachmentsLabel;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    private TextField subjectField;
    
    @FXML
    private HTMLEditor composeArea;
    
    @FXML
    void attachBtnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            attachments.add(selectedFile);
            attachmentsLabel.setText(attachmentsLabel.getText() + selectedFile.getName() + "; ");
            
        }
        
    }
    
    @FXML
    void sendBtnAction(ActionEvent event) {
        errorLabel.setText("");
        EmailSenderService emailSenderService
                = new EmailSenderService(getModelAccess().getEmailAccountByName(senderChoice.getValue()),
                        subjectField.getText(),
                        recipientField.getText(),
                        composeArea.getHtmlText(),
                        attachments);
        emailSenderService.restart();
        emailSenderService.setOnSucceeded(e -> {
            if (emailSenderService.getValue() == EmailConstants.MESSAGE_SENT_OK) {
                errorLabel.setText("message sent successfully");
                
            } else {
                errorLabel.setText("message sending error");
            }
        });
        
    }
    
    public ComposeMessageController(ModelAccess modelAccess) {
        super(modelAccess);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        senderChoice.setItems(getModelAccess().getEmailAccountNames());
        senderChoice.setValue(getModelAccess().getEmailAccountNames().get(0));
        
    }
    
}
