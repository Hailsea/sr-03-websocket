package fr.utc.sr03.model;


import java.util.Date;
import java.util.Hashtable;
import java.util.Objects;

public class User {

    //Attributes
    private static int autoIncrement = 0;
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private boolean admin;

    private Hashtable<Integer, Channel> invitations = new Hashtable<Integer, Channel>();
    //Constructor

    public User(String firstName, String lastName, String email, String password, boolean admin){
        autoIncrement++;
        this.id = getAutoIncrement();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }


    //Getters & Setters

    public static int getAutoIncrement() {
        return autoIncrement;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public int getId() {
        return id;
    }
    public Hashtable<Integer, Channel> getInvitations() {
        return invitations;
    }

    public void setInvitations(Hashtable<Integer, Channel> invitations) {
        this.invitations = invitations;
    }

    //Equals & Hash
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id == user.id && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }


    //Methods
    //TO CREATE LATER
}
