package client.view;

import client.controler.ClientControler;
import client.view.ClientListener.ReadactionClienListener;
import client.view.ClientListener.RegistrationClientListener;
import client.view.ClientListener.InstractionClientListener;
import client.view.redactionDialog.RedactionClientDialog;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by Александр on 14.09.2017.
 */
public class MainClientDialog extends Stage {


    private ClientControler clientControler;
    private RedactionClientDialog redactionClientDialog;
    private FlowPane root;
    private Scene scene;


    public MainClientDialog(ClientControler clientControler) throws FileNotFoundException, SQLException, ParseException {

        this.clientControler = clientControler;

        redactionClientDialog = new RedactionClientDialog(clientControler);


        root = new FlowPane(10, 10);
        root.setStyle("-fx-background-color: green;");
        root.setAlignment(Pos.CENTER);

        scene = new Scene(root, 1350, 690);

        this.setTitle("Раздел клиентам");

        this.createWelcoming();
        this.createDepartment("Регистрация", new RegistrationClientListener(clientControler));
        this.createDepartment("Редактирование услуги", new ReadactionClienListener(redactionClientDialog));
        this.createDepartment("Инструкция по использованию", new InstractionClientListener());

        this.setScene(scene);
        this.show();
    }

    private void createWelcoming() {

        Label label = new Label("                                    Вас приветствует pаздел по работе с клиентами!                              ");
        label.setFont(new Font("Arial", 30));
        label.setTextFill(Color.YELLOW);
        root.getChildren().add(label);
    }

    private void createDepartment(String name, EventHandler eventHandler) {
        Button button = new Button();
        button.setText(name);
        button.setOnAction(eventHandler);
        button.setPrefSize(300, 300);
        root.getChildren().add(button);
    }


    public RedactionClientDialog getRedactionClientDialog() {
        return redactionClientDialog;
    }


}

