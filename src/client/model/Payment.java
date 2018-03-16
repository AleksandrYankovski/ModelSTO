package client.model;

public class Payment {


    private Integer price;
    private Integer pay;
    private Boolean status;


    public Payment() {

    }



    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setPay(Integer price, DiscontCard discontCard) {
        if (pay == null)
            pay = new Integer(0);
        this.pay = price - price * discontCard.getAccumulationPercentage() / 100;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }

    public Integer getPay() {
        return pay;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getPrice() {
        return price;
    }


    public Boolean getStatus() {
        return status;
    }

    @Override
    public String toString() {

        String stringStatus;
        System.out.println(status);
        if (status.equals(true))
            stringStatus = "Оплачено";
        else
            stringStatus = "Не оплачено";


        return "Цена : " + price +
                "\nК оплате: " + pay +
                "\nСтатус: " + stringStatus;
    }
}
