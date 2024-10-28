package com.sous.server.businesslayer.BI_presentationlayer.bl_FragmentServerbleRecyclerViewSimpleScan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.sous.server.R;
import com.sous.server.businesslayer.BI_presentationlayer.bl_FragmentServerbleRecyclerViewSimpleScan.interfaces.BinesslogicFragmentGattServerInterface;
import com.sous.server.businesslayer.ContentProvoders.ContentProviderServer;
import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.datalayer.binesslogic.WtitingAndreadDataForScanGatt;
import com.sous.server.datalayer.binesslogic.bl_writeandreadScanCatt.BunissecclogicCursorLister;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

public class BinesslogicFragmentGattServerInterfaceRecyclerViewGroupBy extends BinesslogicFragmentGattServerInterfaceRecyclerView
        implements BinesslogicFragmentGattServerInterface {


    public BinesslogicFragmentGattServerInterfaceRecyclerViewGroupBy(@NonNull FragmentManager fragmentManager, @NotNull RecyclerView recyclerViewServer, 
                                                                     @NotNull Long version, @NotNull MaterialCardView maincardView_server_ble_fragment, 
                                                                     @NotNull RelativeLayout relativeLayout_server_ble, @NotNull TabLayout tabLayout_server_ble,
                                                                     @NotNull MaterialCardView card_server_ble_inner, @NotNull ProgressBar progressbar_server_ble,
                                                                     @NotNull Animation animation, @NotNull Context context, @NotNull Activity activity, 
                                                                     @NotNull Message messageGattServer, @NotNull BottomNavigationView bottomnavigationview_server_scan) {
        // TODO: 25.10.2024 Наследование от ксалического вида Gatt Server  данныый клапсс будет производить ГРупмровку вида RecyreView 
        super(fragmentManager, recyclerViewServer, version, maincardView_server_ble_fragment, relativeLayout_server_ble,
                tabLayout_server_ble, card_server_ble_inner, progressbar_server_ble, animation, context, activity,
                messageGattServer, bottomnavigationview_server_scan);
    }
}
