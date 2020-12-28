package model;

public class Staff {

    private int sid;
    private String sName;

    public Staff(int sid, String sName){
        this.sid = sid;
        this.sName = sName;
    }

    public int getSid() {
        return sid;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }
}
