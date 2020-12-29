package model;

public class Staff {

    private int sid;
    private String name;

    public Staff(int sid, String sName){
        this.sid = sid;
        this.name = sName;
    }

    public int getSid() {
        return sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String sName) {
        this.name = sName;
    }
}
