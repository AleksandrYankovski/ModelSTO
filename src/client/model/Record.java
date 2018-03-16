package client.model;


import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Александр on 15.09.2017.
 */
public class Record {


    private Car carModel;
    private LocalDate date;
    private LocalTime time;
    private Failure failure;
    private Integer idClient;
    private Integer id;
    private Payment payment;


    public Record() {

        carModel = new Car();
        failure = new Failure();
        payment = new Payment();

    }


    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setFailure(Failure failure) {
        this.failure = failure;
    }


    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getIdClient() {
        return idClient;
    }

    public Failure getFailure() {
        return failure;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Car getCarModel() {
        return carModel;
    }

    public Payment getPayment() {
        return payment;
    }

    public Integer getId() {
        return id;
    }
}
