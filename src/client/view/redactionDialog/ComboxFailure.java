package client.view.redactionDialog;

import client.model.Failure;
import client.model.Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class ComboxFailure extends ComboBox<Failure> {


    private final ObservableList observableList;


    public ComboxFailure() {

        observableList = FXCollections.observableArrayList();

        fillObservableList();

        this.setItems(observableList);


    }

    //ResourceBundle -
    private void fillObservableList() {
        observableList.addAll(new Failure("Разработка рулевой тяги", 100),
                new Failure("Контрольный осмотр подвески легковых автомобилей", 120),
                new Failure("Ультрозвуковая чистка форсунок системы впрыска бензиновых двигателей (за 1 шт.)", 70),
                new Failure("Диагностика амортизаторов (шок - тестер)", 90),
                new Failure("Проверка тормозов на стенде", 120),
                new Failure("Контрольный осмотр электрооборудования", 110),
                new Failure("Компьютерная диагностика электронных систем управления  автомобиля", 80),
                new Failure("Замена масла на легковых автомобилях", 90),
                new Failure("Снятие и установка защиты двигателя из пластмассы", 100),
                new Failure("Снятие и установка защиты двигателя из пластмассы", 120),
                new Failure("Снятие и установка защиты двигателя из металла", 150),
                new Failure("Регулировка фары (1 шт.)", 100),
                new Failure("Диагностика системы охлаждения двигател", 119),
                new Failure("Регулировка холостого хода, зажигания", 93),
                new Failure("Замер давления топлива на бензиновых автомобилях", 160),
                new Failure("Зарядка аккумулятора", 117)
        );
    }


}
