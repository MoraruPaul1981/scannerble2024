package com.sous.scanner.businesslayer.bl_EvenBus;

import android.os.Bundle;

import com.sous.scanner.presentationlayer.FragmentScannerUser;

import java.util.concurrent.ConcurrentSkipListSet;

public class EventB_Clent {

  public  FragmentScannerUser fragmentScannerUser = new FragmentScannerUser();

  public EventB_Clent(FragmentScannerUser fragmentScannerUser) {
    this.fragmentScannerUser = fragmentScannerUser;
  }


}
