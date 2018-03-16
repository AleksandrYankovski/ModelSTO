package client.model;

/**
 * Created by Александр on 22.09.2017.
 */
public class Car {


    private String mark;
    private String model;
    private Integer year;
    private Double bulk;
    private String numberStateRegistration;
    private String color;


    public Car() {

    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setBulk(Double bulk) {
        this.bulk = bulk;
    }

    public void setNumberStateRegistration(String numberStateRegistration) {
        this.numberStateRegistration = numberStateRegistration;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMark() {
        return mark;
    }

    public String getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }

    public Double getBulk() {
        return bulk;
    }

    public String getNumberStateRegistration() {
        return numberStateRegistration;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return
                "Марка : " + mark + '\n' +
                        "Модель : " + model + '\n' +
                        "Год : " + year + '\n' +
                        "Объем : " + bulk + '\n' +
                        "Номер регистрации : " + numberStateRegistration + '\n' +
                        "Цвет : " + color;
    }
}
