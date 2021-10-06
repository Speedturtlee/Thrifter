package com.example.thrifter.List;

public class ListItem {

    String Name, Description, Price, Image;

    /** Hier werden den Karten die dazugehörigen Eigenschaften zugeteilt bzw. ausgelesen */

    public ListItem() {


    }

    public ListItem(String name, String description, String price, String image) {

        this.Name =name;
        this.Description = description;
        this.Price = price;
        this.Image = image;

    }

    //Getter und Setter Methoden um die Eigenschaften der Thriftcard einzuspeichern und auszulesen
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
