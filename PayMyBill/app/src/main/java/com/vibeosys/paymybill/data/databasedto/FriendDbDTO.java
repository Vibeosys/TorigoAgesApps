package com.vibeosys.paymybill.data.databasedto;

/**
 * Created by akshay on 15-07-2016.
 */
public class FriendDbDTO {

    private int id;
    private String name;
    private String contact;
    private String email;
    private String image;

    public FriendDbDTO(int id, String name, String contact, String email, String image) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
