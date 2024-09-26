package com.sous.server.businesslayer.Locations;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.GnssAntennaInfo;
import android.location.GnssMeasurementsEvent;
import android.location.GnssNavigationMessage;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.sous.server.businesslayer.ContentProvoders.ContentProviderServer;
import com.sous.server.businesslayer.Errors.SubClassErrors;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/*---------- Listener class to get coordinates ------------- */
public class GattLocationListener implements LocationListener {
    private  Context context;
    private String TAG;
  private  SharedPreferences sharedPreferencesGatt;

    private  Long version;
    private  LocationManager locationManager;
  private AtomicReference<Location> atomicReferenceLocal=new AtomicReference<>();
    public GattLocationListener(Context context, SharedPreferences sharedPreferencesGatt ,
                                @NotNull Long version,
                                @NonNull LocationManager locationManager) {
        this.context = context;
        this.sharedPreferencesGatt = sharedPreferencesGatt;
        this.version = version;
        this.locationManager = locationManager;
        TAG = getClass().getName().toString();
        Log.i(TAG, "MyLocationListener GPS "+context);
    }

    @SuppressLint("NewApi")
    @Override
    public void onLocationChanged(Location loc) {
        try{
            atomicReferenceLocal.set(loc);


            getregisterGnssStatusCallback(locationManager);





            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    "  atomicReferenceLocal" +atomicReferenceLocal.get());


        } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
        ContentValues valuesЗаписываемОшибки = new ContentValues();
        valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
        valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
        valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
        valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
        final Object ТекущаяВерсияПрограммы = 0;
        Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
        valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
    }

        }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }
    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
        Log.i(TAG, "MyLocationListener GPS requestCode "+requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
        Log.i(TAG, "MyLocationListener GPS extras "+extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
        Log.i(TAG, "MyLocationListener GPS provider "+provider);
    }




    @RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint("MissingPermission,NewApi")
    private void getregisterGnssStatusCallback(@NonNull LocationManager    locationManager) {
        try{
            // TODO: 02.09.2024
            List<String> getAllProviders=         locationManager.getAllProviders();
            if (getAllProviders!=null) {
                if (getAllProviders.size()>0) {
                    // TODO: 03.09.2024
                    getingLocationsGps();
                }
            }

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  + "\n" +
                    "getAllProviders  "+getAllProviders);


        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки = new ContentValues();
            valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }
    @SuppressLint("NewApi")
    private  synchronized   List<Address> getingLocationsGps() throws IOException {
        List<Address> addresses = null;
        try{

            Location getlocation=     atomicReferenceLocal.get();

            while (!getlocation.isComplete());

            String longitude = "Longitude: " + getlocation.getLongitude();
            /*------- To get city name from coordinates -------- */
            Log.i(TAG, "MyLocationListener GPS longitude "+longitude);
            String cityName = null;
            Geocoder gcd = new Geocoder(context, new Locale("ru","RU"));
            Log.i(TAG, "MyLocationListener GPS gcd "+gcd);


            try {
                addresses = gcd.getFromLocation(getlocation.getLatitude(), getlocation.getLongitude(), 1);
            } catch (IOException e) {
                ///throw new RuntimeException(e);
            }

            Log.i(TAG, "MyLocationListener GPS addresses "+addresses);

            if (addresses!=null) {
                if (addresses.size() > 0) {
                    System.out.println(addresses.get(0).getLocality());
                    cityName = addresses.get(0).getLocality();
                    Log.i(TAG, "MyLocationListener GPS cityName "+cityName);

                    SharedPreferences.Editor editor = sharedPreferencesGatt.edit();
                    editor.clear();
                    addresses.forEach(new Consumer<Address>() {
                        @Override
                        public void accept(Address address) {
                            editor.putString("getAdminArea",address.getAdminArea());
                            editor.putString("getCountryName",address.getCountryName());
                            editor.putString("getLocality",address.getLocality());
                            editor.putString("getSubAdminArea",address.getSubAdminArea());
                            editor.putString("getLatitude", String.valueOf(address.getLatitude()));
                            editor.putString("getLongitude", String.valueOf(address.getLongitude()));
                            editor.putString("getLocale", String.valueOf(address.getLocale()));
                            editor.putString("getThoroughfare", address.getThoroughfare());
                            editor.putString("getSubThoroughfare", address.getSubThoroughfare());

                            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                                    "  address" +address);

                        }
                    });

                    editor.apply();



                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                            "  addresses " +addresses);

                }
            }
            Log.i(TAG, "MyLocationListener GPS addresses "+addresses);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки = new ContentValues();
            valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = 0;
            Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return  addresses;


    }



    // TODO: 03.09.2024  END CLASS
}