/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.emailclient.services;

import com.mycompany.emailclient.controller.AbstractController;
import com.mycompany.emailclient.controller.AddAccountController;
import com.mycompany.emailclient.controller.ComposeMessageController;
import com.mycompany.emailclient.controller.EmailDetailsController;
import com.mycompany.emailclient.controller.FXMLController;
import com.mycompany.emailclient.controller.ModelAccess;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javax.naming.OperationNotSupportedException;

/**
 *
 * @author Andreas
 */
public class ViewFactory {

    public static ViewFactory defaultFactory = new ViewFactory();
    private static boolean sceneInitialized = false;

    private ModelAccess modelAccess = new ModelAccess();
    private FXMLController fxmlController;
    private EmailDetailsController emailDetailsController;
    private final String EMAIL_DETAILS_FXML = "/fxml/EmailDetails.fxml";
    private final String SCENE_FXML = "/fxml/Scene.fxml";
    private final String COMPOSE_SCREEN_FXML = "/fxml/ComposeMessageLayout.fxml";
    private final String ADD_SCREEN_FXML = "/fxml/AddAccountLayout.fxml";

    private Scene initializeScene(String fxmlPath, AbstractController controller) {
        FXMLLoader loader;
        Parent parent;
        Scene scene;
        try {
            loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setController(controller);
            parent = loader.load();
        } catch (Exception e) {
            return null;
        }
        scene = new Scene(parent);
        return scene;

    }

    public Scene getMainScene() throws OperationNotSupportedException {
        if (!sceneInitialized) {
            fxmlController = new FXMLController(modelAccess);
            sceneInitialized = true;
            return initializeScene(SCENE_FXML, fxmlController);
        } else {
            throw new OperationNotSupportedException("Scene already initialized");
        }
    }

    public Scene getEmailDetailsScene() {
        emailDetailsController = new EmailDetailsController(modelAccess);
        return initializeScene(EMAIL_DETAILS_FXML, emailDetailsController);
    }
    
    public Scene getComposeMessageScene(){
    AbstractController composeController = new ComposeMessageController(modelAccess);
    return initializeScene(COMPOSE_SCREEN_FXML, composeController);
    }
    
    public Scene getAddAccountScene(){
    AbstractController addController = new AddAccountController(modelAccess);
    return initializeScene(ADD_SCREEN_FXML, addController);
    }
    
      public Node resolveIcon(String treeItemValue) {
        String lowerCaseTreeItemValue = treeItemValue.toLowerCase();
        ImageView returnIcon;
        try {
            if (lowerCaseTreeItemValue.contains("inbox")) {
                returnIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/inbox.png")));
            } else if (lowerCaseTreeItemValue.contains("sent")) {
                returnIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/send.png")));
            } else if (lowerCaseTreeItemValue.contains("spam")) {
                returnIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/spam.png")));
            } else if (lowerCaseTreeItemValue.contains("@")) {
                returnIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/email2.png")));
            } else {
                returnIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/folder.png")));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            returnIcon = new ImageView();
        }
        returnIcon.setFitHeight(16);
        returnIcon.setFitWidth(16);
        return returnIcon;

    }
}
