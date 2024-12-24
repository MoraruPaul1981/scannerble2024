package com.dsy.dsu.BootAndAsync.EventsBus;

import android.content.Intent;

import androidx.annotation.NonNull;

public class MessageEvensBusEndAync {

    public Intent mess;


    public MessageEvensBusEndAync(@NonNull Intent mess) {
        this.mess = mess;
    }
}
