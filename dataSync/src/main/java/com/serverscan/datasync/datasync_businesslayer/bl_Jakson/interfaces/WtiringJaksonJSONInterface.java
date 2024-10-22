package com.serverscan.datasync.datasync_businesslayer.bl_Jakson.interfaces;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverscan.datasync.datasync_businesslayer.bl_Jakson.model.CompleteallmacadressusersEntityDeserial;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.rxjava3.annotations.NonNull;

public interface WtiringJaksonJSONInterface {


        JsonNode converttoJacksonObject(  @NotNull byte[] bytesGetOtJBoss);

    CopyOnWriteArrayList<CompleteallmacadressusersEntityDeserial> readListJacksonObject(@NonNull long version,
                                                                                        @NotNull JsonNode jsonNodeAtomicReferenceGattGet);

    Integer processtheCursorandfillinmodel(@NonNull  CompleteallmacadressusersEntityDeserial  copyOnWriteArrayGetDataOtJbossGet);


}
