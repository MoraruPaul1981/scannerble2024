package com.dsy.dsu.Hilt.OkhhtpBuilder;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class GetKeyStoreJboss {

    Context context;

    Long version;


    KeyStore readKeyStoreSLL() throws KeyStoreException, IOException {
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());

        // get user password and file input stream
        char[] password = "mypassword".toCharArray();

        java.io.FileInputStream fis = null;
        try {
            fis = new java.io.FileInputStream("sousabtodor2024bks.bks");
            ks.load(fis, password);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return ks;
    }


}
