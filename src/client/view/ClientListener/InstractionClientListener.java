package client.view.ClientListener;

import client.view.InstractionDialog.InstractionDialog;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.io.IOException;

/**
 * Created by Александр on 16.09.2017.
 */
public class InstractionClientListener implements EventHandler {


    public InstractionClientListener() {
    }

    @Override
    public void handle(Event event) {
        try {
            new InstractionDialog();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
