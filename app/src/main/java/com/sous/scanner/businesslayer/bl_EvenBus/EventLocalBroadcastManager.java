package com.sous.scanner.businesslayer.bl_EvenBus;

import com.sous.scanner.presentationlayer.FragmentScannerUser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class EventLocalBroadcastManager {
    // TODO: 31.07.2024
    public  String getAction=    new String();
    public  String getAddress=     new String();
    public   String getName=  new String();
    public  String getBremy=   new String();

    public EventLocalBroadcastManager(String getAction, String getAddress, String getName, String getBremy) {
        this.getAction = getAction;
        this.getAddress = getAddress;
        this.getName = getName;
        this.getBremy = getBremy;
    }
    // TODO: 07.08.2024




}
