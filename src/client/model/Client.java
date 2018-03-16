package client.model;

import java.awt.image.BufferedImage;

/**
 * Created by Александр on 13.09.2017.
 */
public class Client {


    private String name;
    private String surname;
    private String patronymic;
    private Adres adres;
    private BufferedImage photo;
    private DiscontCard discontCard;

    public Client() {

    }


    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public Adres getAdres() {
        return adres;
    }

    public BufferedImage getPhoto() {
        return photo;
    }

    public void setPhoto(BufferedImage photo) {

        this.photo = photo;
    }

    public void activateDiscontCard() {
        this.discontCard = new DiscontCard();
        discontCard.generateId();
    }

    public void activateDiscontCard(Integer id) {
        this.discontCard = new DiscontCard();
        discontCard.setId(id);
    }


    public DiscontCard getDiscontCard() {
        return discontCard;
    }

    public static class Adres {


        private String country;
        private String city;
        private String street;
        private String house;
        private Integer flat;


        public Adres() {

        }


        public void setCountry(String country) {
            this.country = country;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public void setHouse(String house) {
            this.house = house;
        }

        public void setFlat(Integer flat) {
            this.flat = flat;
        }

        public String getCountry() {
            return country;
        }

        public String getCity() {
            return city;
        }

        public String getStreet() {
            return street;
        }

        public String getHouse() {
            return house;
        }

        public Integer getFlat() {
            return flat;
        }
    }
}
