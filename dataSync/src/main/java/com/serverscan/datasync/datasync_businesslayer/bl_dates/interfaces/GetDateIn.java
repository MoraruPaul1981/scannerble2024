package com.serverscan.datasync.datasync_businesslayer.bl_dates.interfaces;

import org.jetbrains.annotations.NotNull;

public interface GetDateIn {

    String getDates(  @NotNull Long version  );
}
