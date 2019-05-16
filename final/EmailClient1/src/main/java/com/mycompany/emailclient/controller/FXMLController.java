package com.mycompany.emailclient.controller;

import com.mycompany.emailclient.model.EmailMessageBean;
import com.mycompany.emailclient.model.folder.EmailFolderBean;
import com.mycompany.emailclient.model.table.BoldableRowFactory;
import com.mycompany.emailclient.model.table.FormatableInteger;
import com.mycompany.emailclient.services.CreateAndRegisterEmailAccountService;
import com.mycompany.emailclient.services.FolderUpdaterService;
import com.mycompany.emailclient.services.MessageRendererService;
import com.mycompany.emailclient.services.SaveAttachmentsService;
import com.mycompany.emailclient.services.ViewFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class FXMLController extends AbstractController implements Initializable {

    public FXMLController(ModelAccess modelAccess) {
        super(modelAccess);
    }

    @FXML
    private TreeView<String> emailFoldersTreeView;
    private MenuItem showDetails = new MenuItem("show details");
    @FXML
    private TableColumn<EmailMessageBean, String> subjectCol;
    @FXML
    private TableColumn<EmailMessageBean, String> senderCol;
    @FXML
    private TableColumn<EmailMessageBean, FormatableInteger> sizeCol;
    @FXML
    private TableColumn<EmailMessageBean, Date> dateCol;
    @FXML
    private TableView<EmailMessageBean> emailTableView;
    @FXML
    private WebView messageRenderer;
    @FXML
    private Label downAttachLabel;
    @FXML
    private ProgressBar downAttachProgress;

    private MessageRendererService messageRendererService;

    private SaveAttachmentsService saveAttachmentsService;
    
    private AddAccountController addAccountController;

    @FXML
    void button1action(ActionEvent event) {
        Scene scene = ViewFactory.defaultFactory.getComposeMessageScene();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void downAttachBtnAction(ActionEvent event) {
        EmailMessageBean message = emailTableView.getSelectionModel().getSelectedItem();
        System.out.println(message.hasAttachments());
        if (message != null && message.hasAttachments()) {
            saveAttachmentsService.setMessageToDownload(message);
            saveAttachmentsService.restart();

        }

    }
    
     @FXML
    void addAccountBtnAction(){
    	Scene scene = ViewFactory.defaultFactory.getAddAccountScene();
    	Stage stage = new Stage();
    	stage.setScene(scene);
    	stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        downAttachLabel.setVisible(false);
        downAttachProgress.setVisible(false);
        saveAttachmentsService = new SaveAttachmentsService(downAttachProgress, downAttachLabel);
        messageRendererService = new MessageRendererService(messageRenderer.getEngine());
        downAttachProgress.progressProperty().bind(saveAttachmentsService.progressProperty());

        FolderUpdaterService folderUpdaterService = new FolderUpdaterService(getModelAccess().getFoldersList());
        folderUpdaterService.start();

        emailTableView.setRowFactory(e -> new BoldableRowFactory<>());
        ViewFactory viewFactory = ViewFactory.defaultFactory;

        subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("subject"));
        senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("sender"));
        dateCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, Date>("date"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, FormatableInteger>("size"));
        sizeCol.setComparator(new FormatableInteger(0));

        EmailFolderBean<String> root = new EmailFolderBean<>("");
        emailFoldersTreeView.setRoot(root);
        emailFoldersTreeView.setShowRoot(false);

        CreateAndRegisterEmailAccountService createAndRegisterEmailAccountService3
                = new CreateAndRegisterEmailAccountService("testMyClient212@gmail.com",
                        "javaclient",
                        root,
                        getModelAccess());
        createAndRegisterEmailAccountService3.start();

        CreateAndRegisterEmailAccountService createAndRegisterEmailAccountService
                = new CreateAndRegisterEmailAccountService("testMyClient021@gmail.com",
                        "javaclient12345",
                        root,
                        getModelAccess());
        createAndRegisterEmailAccountService.start();
       
        emailTableView.setContextMenu(new ContextMenu(showDetails));

        emailFoldersTreeView.setOnMouseClicked(e -> {
            EmailFolderBean<String> item = (EmailFolderBean<String>) emailFoldersTreeView.getSelectionModel().getSelectedItem();
            if (item != null && !item.isTopElement()) {
                emailTableView.setItems(item.getData());
                getModelAccess().setSelectedFolder(item);
            }

        });
        emailTableView.setOnMouseClicked(e -> {
            EmailMessageBean message = emailTableView.getSelectionModel().getSelectedItem();
            if (message != null) {
                getModelAccess().setSelectedMessage(message);
                messageRendererService.setMessageToRender(message);
                messageRendererService.restart();
                //Platform.runLater(messageRendererService);

            }

        });
        showDetails.setOnAction(e -> {

            Scene scene = viewFactory.getEmailDetailsScene();
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        });
         // ArrayList<ArrayList<CreateAndRegisterEmailAccountService>> location = addAccountController.getLocationList();
      // for(ArrayList<CreateAndRegisterEmailAccountService> user: location){
             //   System.out.println(user);  
     //  }

    }
  
}
