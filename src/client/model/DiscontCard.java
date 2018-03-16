package client.model;

public class DiscontCard {


    private Integer accumulationPercentage;
    private Integer id;
    private static Integer psevdoId = 0;


    public DiscontCard() {
        accumulationPercentage = new Integer(0);
    }


    public void setAccumulationPercentage(Integer accumulationPercentage) {
        this.accumulationPercentage = accumulationPercentage;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void generateId() {
        psevdoId++;
    }

    public Integer getAccumulationPercentage() {
        return accumulationPercentage;
    }

    public Integer getId() {
        if (id == null) {
            return psevdoId;
        } else
            return id;
    }

}
