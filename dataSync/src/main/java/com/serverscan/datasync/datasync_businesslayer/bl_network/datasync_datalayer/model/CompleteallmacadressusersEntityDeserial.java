package com.serverscan.datasync.datasync_businesslayer.bl_network.datasync_datalayer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class CompleteallmacadressusersEntityDeserial implements Serializable  {


    private static final long serialVersionUID = 1L;


    @JsonProperty("id")
    private long id;
    @JsonProperty("fio")
    private String fio;
    @JsonProperty("mac")
    private String mac;
    @JsonProperty("date_update")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss.SSS" )
    private Date dateUpdate;
    @JsonProperty("currentTable")
    private BigDecimal currentTable;
    @JsonProperty("uuid")
    private BigDecimal uuid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public BigDecimal getCurrentTable() {
        return currentTable;
    }

    public void setCurrentTable(BigDecimal currentTable) {
        this.currentTable = currentTable;
    }

    public BigDecimal getUuid() {
        return uuid;
    }

    public void setUuid(BigDecimal uuid) {
        this.uuid = uuid;
    }


}
