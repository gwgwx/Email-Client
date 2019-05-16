/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.emailclient.services;

import com.mycompany.emailclient.model.EmailMessageBean;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javax.mail.internet.MimeBodyPart;

/**
 *
 * @author Andreas
 */
public class SaveAttachmentsService extends Service<Void> {

    //here we save the attachments
    private String LOCATION_OF_DOWNLOADS = System.getProperty("user.home") + "/Downloads/";

    private EmailMessageBean messageToDownload;
    private ProgressBar progress;
    private Label label;

    public SaveAttachmentsService(ProgressBar progress, Label label) {
        //showVisuals(false);
        this.progress = progress;
        this.label = label;

        this.setOnRunning(e -> {
            showVisuals(true);
        });

        this.setOnSucceeded(e -> {
            showVisuals(false);
        });
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                try {
                    for (MimeBodyPart mbp : messageToDownload.getAttachmentsList()) {
                        updateProgress(messageToDownload.getAttachmentsList().indexOf(mbp),
                         messageToDownload.getAttachmentsList().size());
                        mbp.saveFile(LOCATION_OF_DOWNLOADS + mbp.getFileName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        };
    }

    public void setMessageToDownload(EmailMessageBean messageToDownload) {
        this.messageToDownload = messageToDownload;
    }

    private void showVisuals(boolean show) {

        progress.setVisible(show);
        label.setVisible(show);

    }
}
