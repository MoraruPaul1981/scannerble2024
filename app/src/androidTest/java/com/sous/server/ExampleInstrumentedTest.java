package com.sous.server;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    Context appContext;
    @Test
    public void useAppContext() {
        // Context of the app under test.
      appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        
        
        //assertEquals("com.sous.server", appContext.getPackageName());
    }

    @Test
    public void name() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        System.out.println("testМетодЗаписиОтмечаногоСотрудникаВБАзу");
        ServiceGattServer serviceGattServertest =new ServiceGattServer();
       // Integer РезультатЗаписиGATTTEst= serviceGattServertest.МетодЗаписиОтмечаногоСотрудникаВБАзу(appContext);
      //  System.out.println(" re"+РезультатЗаписиGATTTEst);
        ///assertNotNull(РезультатЗаписиGATTTEst);
    }
}