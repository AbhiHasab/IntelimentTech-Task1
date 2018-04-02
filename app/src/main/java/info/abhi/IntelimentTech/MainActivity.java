package info.abhi.IntelimentTech;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import info.abhi.IntelimentTech.adapter.ItemAdapter;
import info.abhi.IntelimentTech.adapter.ViewPagerAdapter;
import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<String> alName;
    private ViewPager viewPager;
    private static int currentPage = 0;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final ArrayList<Integer> XMENArray = new ArrayList<>();
    private final int[] images = {R.drawable.ourcustomer, R.drawable.ourthinking, R.drawable.ourcraft, R.drawable.innovation};
    private ViewPagerAdapter myCustomPagerAdapter;
    private CircleIndicator indicator;
    private LinearLayout colorBackground;
    private Button redBtn;
    private Button blueBtn;
    private Button greenBtn;
    private TextView position;
    private String qty;
    private int layoutId = R.layout.activity_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            layoutId = savedInstanceState.getInt("layoutId", R.layout.activity_main);
        }
        setContentView(layoutId);
        alName = new ArrayList<>(Arrays.asList("Item 1", "Item 2", "Item 3", "Item 4", "Item 5"));

        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ItemAdapter(MainActivity.this, alName);
        mRecyclerView.setAdapter(mAdapter);

        // ImageSlider
        viewPager = (ViewPager) findViewById(R.id.pager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);

        redBtn = (Button) findViewById(R.id.redBtn);
        blueBtn = (Button) findViewById(R.id.blueBtn);
        greenBtn = (Button) findViewById(R.id.greenBtn);
        position = (TextView) findViewById(R.id.position);

        redBtn.setOnClickListener(this);
        blueBtn.setOnClickListener(this);
        greenBtn.setOnClickListener(this);

        colorBackground = (LinearLayout) findViewById(R.id.colorBackground);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        init();
    }

    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
             qty = intent.getStringExtra("quantity");
            try {
                position.setText(qty);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void init() {
        for (int image : images) {
            XMENArray.add(image);
        }
        myCustomPagerAdapter = new ViewPagerAdapter(getApplicationContext(), images);
        viewPager.setAdapter(myCustomPagerAdapter);
        indicator.setViewPager(viewPager);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 4000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.redBtn:
                colorBackground.setBackgroundColor(Color.parseColor("#FF0000"));
                break;

            case R.id.blueBtn:
                colorBackground.setBackgroundColor(Color.parseColor("#0000FF"));
                break;

            case R.id.greenBtn:
                colorBackground.setBackgroundColor(Color.parseColor("#008000"));
                break;

            default:
                break;
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("qty",qty);
        outState.putInt("layoutId", layoutId);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        String qty = savedInstanceState.getString("qty");
        position.setText(qty);
        super.onRestoreInstanceState(savedInstanceState);
    }
}