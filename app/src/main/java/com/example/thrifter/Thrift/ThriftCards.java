package com.example.thrifter.Thrift;

public class ThriftCards {

    private String Image, Name, Price, Description;

    /** Hier werden den Karten die dazugehörigen Eigenschaften zugeteilt bzw. ausgelesen */

    public ThriftCards() {


    }

    public ThriftCards(String name, String image, String price, String description) {

        this.Name = name;
        this.Image = image;
        this.Price = price;
        this.Description = description;

    }

    //Getter und Setter Methoden um die Eigenschaften der Thriftcard einzuspeichern und auszulesen
    public String getName(){
        return Name;
    }

    public void setName(String Name){
        this.Name = Name;
    }

    public String getImage(){
        return Image;
    }

    public void setImage(String Image){
        this.Image = Image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
