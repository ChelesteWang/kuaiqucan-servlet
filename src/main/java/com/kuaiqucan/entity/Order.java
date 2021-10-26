package com.kuaiqucan.entity;

public class Order {
    private String oid;
    private String oname;
    private String otype;
    private String obrand;
    private String ocount;
    private String oprice;

    public Order() {
    }

    public Order(String oid, String oname, String otype, String obrand, String ocount, String oprice) {
        this.oid = oid;
        this.oname = oname;
        this.otype = otype;
        this.obrand = obrand;
        this.ocount = ocount;
        this.oprice = oprice;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public String getOtype() {
        return otype;
    }

    public void setOtype(String otype) {
        this.otype = otype;
    }

    public String getObrand() {
        return obrand;
    }

    public void setObrand(String obrand) {
        this.obrand = obrand;
    }

    public String getOcount() {
        return ocount;
    }

    public void setOcount(String ocount) {
        this.ocount = ocount;
    }

    public String getOprice() {
        return oprice;
    }

    public void setOprice(String oprice) {
        this.oprice = oprice;
    }
}
