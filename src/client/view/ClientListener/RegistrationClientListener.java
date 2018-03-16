package client.view.ClientListener;

import client.controler.ClientControler;
import client.view.registrationDialog.RegistrationDialog;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.io.FileNotFoundException;

/**
 * Created by Александр on 14.09.2017.
 */
public class RegistrationClientListener implements EventHandler {


    private ClientControler clientControler;

    public RegistrationClientListener(ClientControler clientControler) {

        this.clientControler = clientControler;

    }

    @Override
    public void handle(Event event) {
        try {
            new RegistrationDialog(clientControler);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
