package com.vinnie.preciousfamily.Lists;

public class Family_list {
    private String famname;
    private int membersno;

    public Family_list(String famname, int membersno) {
        this.famname = famname;
        this.membersno = membersno;
    }

    public String getFamname() {
        return famname;
    }

    public int getMembersno() {
        return membersno;
    }
}
