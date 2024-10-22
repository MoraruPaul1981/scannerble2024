package com.serverscan.datasync.datasync_businesslayer.bl_Jakson.interfaces;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverscan.datasync.datasync_businesslayer.bl_Jakson.model.CompleteallmacadressusersEntityDeserial;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.rxjava3.annotations.NonNull;

public interface WtiringJaksonJSONInterface {


    CopyOnWriteArrayList<CompleteallmacadressusersEntityDeserial> converttoJacksonObject(  @NotNull byte[] bytesGetOtJBoss);

    Integer readListJacksonObject(  @NotNull CopyOnWriteArrayList<CompleteallmacadressusersEntityDeserial> completeallmacadressusersEntityDeserials);



}
