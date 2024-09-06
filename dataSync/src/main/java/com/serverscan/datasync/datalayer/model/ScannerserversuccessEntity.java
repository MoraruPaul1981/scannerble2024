package com.serverscan.datasync.datalayer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ScannerserversuccessEntity implements Serializable  {
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private int id;
    @JsonProperty("operations")
    private String operations;
    @JsonProperty("completedwork")
    private String completedwork;
    @JsonProperty("namedevice")
    private String namedevice;
    @JsonProperty("macdevice")
    private String macdevice;
    @JsonProperty("gps1")
    private String gps1;
    @JsonProperty("gps2")
    private String gps2;
    @JsonProperty("getstatusrow")
    private Integer getstatusrow;
    @JsonProperty("adress")
    private String adress;
    @JsonProperty("city")
    private String city;

    @JsonProperty("date_update")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss.SSS" )
    private Date dateUpdate;

    @JsonProperty("uuid")
    private BigDecimal uuid;

    @JsonProperty("version")
    private Long version;
    @JsonProperty("sim")
    private Integer sim;
    @JsonProperty("iemi")
    private String iemi;
    @JsonProperty("currentTable")
    private BigDecimal currentTable;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperations() {
        return operations;
    }

    public void setOperations(String operations) {
        this.operations = operations;
    }

    public String getCompletedwork() {
        return completedwork;
    }

    public void setCompletedwork(String completedwork) {
        this.completedwork = completedwork;
    }

    public String getNamedevice() {
        return namedevice;
    }

    public void setNamedevice(String namedevice) {
        this.namedevice = namedevice;
    }

    public String getMacdevice() {
        return macdevice;
    }

    public void setMacdevice(String macdevice) {
        this.macdevice = macdevice;
    }

    public String getGps1() {
        return gps1;
    }

    public void setGps1(String gps1) {
        this.gps1 = gps1;
    }

    public String getGps2() {
        return gps2;
    }

    public void setGps2(String gps2) {
        this.gps2 = gps2;
    }

    public Integer getGetstatusrow() {
        return getstatusrow;
    }

    public void setGetstatusrow(Integer getstatusrow) {
        this.getstatusrow = getstatusrow;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public BigDecimal getUuid() {
        return uuid;
    }

    public void setUuid(BigDecimal uuid) {
        this.uuid = uuid;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Integer getSim() {
        return sim;
    }

    public void setSim(Integer sim) {
        this.sim = sim;
    }


    public String getIemi() {
        return iemi;
    }

    public void setIemi(String iemi) {
        this.iemi = iemi;
    }

    public BigDecimal getCurrentTable() {
        return currentTable;
    }

    public void setCurrentTable(BigDecimal currentTable) {
        this.currentTable = currentTable;
    }


}
