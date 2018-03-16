package client.model;

public class Failure {


    private String typeOfFailure;
    private Integer price;


    public Failure() {

    }



    public Failure(String typeOfFailure, Integer price) {

        this.typeOfFailure = typeOfFailure;
        this.price = price;

    }

    public String getTypeOfFailure() {
        return typeOfFailure;
    }

    public Integer getPrice() {
        return price;
    }

    public void setTypeOfFailure(String typeOfFailure) {
        this.typeOfFailure = typeOfFailure;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Вид услуги: " + typeOfFailure +
                "\nЦена: " + price;
    }
}
