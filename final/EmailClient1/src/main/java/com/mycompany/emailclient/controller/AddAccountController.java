/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.emailclient.controller;

import com.mycompany.emailclient.model.EmailConstants;
import com.mycompany.emailclient.services.CreateAndRegisterEmailAccountService;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Andreas
 */
public class AddAccountController extends AbstractController implements Initializable {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField addressField;

    @FXML
    private Label statusLabel;

    private ArrayList<CreateAndRegisterEmailAccountService> addAccount = new ArrayList<CreateAndRegisterEmailAccountService>();
    public ArrayList<ArrayList<CreateAndRegisterEmailAccountService>> locationList = new ArrayList<>();

    @FXML
    void loginBtnAction() {

        statusLabel.setText("");
        CreateAndRegisterEmailAccountService createAndRegisterEmailAccountService
                = new CreateAndRegisterEmailAccountService(
                        addressField.getText(),
                        passwordField.getText(),
                        getModelAccess());
        createAndRegisterEmailAccountService.start();

        statusLabel.setText("logging in....");
        System.out.println("skata0");
        addAccount.add(createAndRegisterEmailAccountService);

        createAndRegisterEmailAccountService.setOnSucceeded(e -> {
            if (createAndRegisterEmailAccountService.getValue() == EmailConstants.LOGIN_STATE_SUCCEDED) {
                System.out.println("skata");
            } else {

                statusLabel.setText("an error occured...");

            }
        });

    }

    public AddAccountController(ModelAccess modelAccess) {
        super(modelAccess);
    }

    public ArrayList<ArrayList<CreateAndRegisterEmailAccountService>> getLocationList() {
        return locationList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
