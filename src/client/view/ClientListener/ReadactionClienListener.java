package client.view.ClientListener;

import client.view.redactionDialog.RedactionClientDialog;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by Александр on 14.09.2017.
 */
public class ReadactionClienListener implements EventHandler {


    private RedactionClientDialog redactionClientDialog;

    public ReadactionClienListener(RedactionClientDialog redactionClientDialog) {

        this.redactionClientDialog = redactionClientDialog;
    }

    @Override
    public void handle(Event event) {
        try {
            redactionClientDialog.inputIdCLient();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
