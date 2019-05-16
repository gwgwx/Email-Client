/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.emailclient.controller;

import com.mycompany.emailclient.model.EmailMessageBean;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

/**
 *
 * @author Andreas
 */
public class EmailDetailsController extends AbstractController implements Initializable {

    @FXML
    private WebView webView;
    @FXML
    private Label subjectLabel;
    @FXML
    private Label senderLabel;

    public EmailDetailsController(ModelAccess modelAccess) {
        super(modelAccess);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        EmailMessageBean selectedMessage = getModelAccess().getSelectedMessage();
        subjectLabel.setText("Subject:" + selectedMessage.getSubject());
        senderLabel.setText("Sender:" + selectedMessage.getSender());

        //webView.getEngine().loadContent(selectedMessage.getContent());

    }

}
