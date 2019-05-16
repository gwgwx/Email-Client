/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.emailclient.services;

import com.mycompany.emailclient.model.EmailMessageBean;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;

/**
 *
 * @author Andreas
 */
public class MessageRendererService extends Service<Void> {

    private EmailMessageBean messageToRender;
    private WebEngine messageRendererEngine;
    private StringBuffer sb = new StringBuffer();

    public MessageRendererService(WebEngine messageRendererEngine) {
        this.messageRendererEngine = messageRendererEngine;
    }

    public void setMessageToRender(EmailMessageBean messageToRender) {
        this.messageToRender = messageToRender;
        this.setOnSucceeded(e -> {
            showMessage();
        });
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                renderMessage();

                return null;
            }

        };
    }

    private void renderMessage() {
        sb.setLength(0);
        messageToRender.clearAttachments();        
        Message message = messageToRender.getMessageReference();
        try {
            String messageType = message.getContentType();
            if (messageType.contains("TEXT/HTML")
                    || messageType.contains("TEXT/PLAIN")
                    || messageType.contains("text")) {
                sb.append(message.getContent().toString());

            } else if (messageType.contains("multipart")) {
                Multipart mp = (Multipart) message.getContent();
                for (int i = mp.getCount() - 1; i >= 0; i--) {
                    BodyPart bp = mp.getBodyPart(i);
                    String contentType = bp.getContentType();
                    if (contentType.contains("TEXT/HTML")
                            || contentType.contains("TEXT/PLAIN")
                            || contentType.contains("mixed")
                            || contentType.contains("text")) {
                        if (sb.length() == 0) {
                            sb.append(bp.getContent().toString());

                            //here attachments are handled
                        }

                    } else if (contentType.toLowerCase().contains("application")
                            || contentType.toLowerCase().contains("image")
                            || contentType.toLowerCase().contains("audio")) {
                        MimeBodyPart mbp = (MimeBodyPart) bp;
                        messageToRender.addAttachment(mbp);

                    } else if (bp.getContentType().contains("multipart")) {
                        Multipart mp2 = (Multipart) bp.getContent();
                        for (int j = mp2.getCount() - 1; j >= 0; j--) {
                            BodyPart bp2 = mp2.getBodyPart(i);
                            if ((bp2.getContentType().contains("TEXT/HTML")
                                    || bp2.getContentType().contains("TEXT/PLAIN"))) {
                                sb.append(bp2.getContent().toString());
                            }

                        }
                    }

                }

            }

        } catch (Exception e) {
            System.out.println("Exception while visualizing message: ");
            e.printStackTrace();

        }

    }

    //called when the message is loaded
    private void showMessage() {
        messageRendererEngine.loadContent("");
        messageRendererEngine.loadContent(sb.toString());
    }
}
