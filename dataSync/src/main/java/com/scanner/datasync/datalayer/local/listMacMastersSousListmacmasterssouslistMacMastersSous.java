package com.scanner.datasync.datalayer.local;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class listMacMastersSousListmacmasterssouslistMacMastersSous implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("macadress")
    private String macadress;


    @JsonProperty("plot")
    private Long plot;


    @JsonProperty("date_update")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date dateUpdate;


    @JsonProperty("user_update")
    private Long userUpdate;


    @JsonProperty("current_table")
    private BigDecimal  currentTable;


    @JsonProperty("uuid")
    private BigDecimal  uuid;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMacadress() {
        return macadress;
    }

    public void setMacadress(String macadress) {
        this.macadress = macadress;
    }

    public Long getPlot() {
        if(plot==null){
            plot=0l;
        }
        return this.plot;
    }

    public void setPlot(Long plot) {this.plot = plot;}

    public Date getDateUpdate() {
        DateFormat dateFormat =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",new Locale("ru","RU"));
        String Дата = dateFormat.format(dateUpdate);
        try {
            this.dateUpdate= dateFormat.parse(Дата);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        DateFormat dateFormat =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",new Locale("ru","RU"));
        String Дата = dateFormat.format(dateUpdate);
        Date datebuffer=null;
        try {
            datebuffer= dateFormat.parse(Дата);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.dateUpdate = datebuffer;
    }

    public Long getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(Long userUpdate) {
        this.userUpdate = userUpdate;
    }

    public BigDecimal  getCurrentTable() {

        return currentTable;
    }

    public void setCurrentTable(BigDecimal  currentTable) {

        this.currentTable = currentTable;
    }

    public BigDecimal  getUuid() {

        return uuid;
    }

    public void setUuid(BigDecimal uuid) {

        this.uuid = uuid;
    }




}
