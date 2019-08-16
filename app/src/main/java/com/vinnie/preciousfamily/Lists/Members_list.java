package com.vinnie.preciousfamily.Lists;

public class Members_list {
    private String firstName;
    private String middleName;
    private String surname;
    private String phone;

    public Members_list(String firstName, String middleName, String surname, String phone) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.surname = surname;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }
}
