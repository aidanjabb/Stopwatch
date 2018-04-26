package edu.illlinois.cs.cs125.cs125finalproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Stopwatch extends AppCompatActivity {

    /**
     * Default logging tag for messages from the main activity.
     */
    private static final String TAG = "Stopwatch:Main";
    long miliSecond;
    long start;
    long timeBuff;
    long update;
    int second;
    int minutes;
    int miliseconds;
    Handler handler;
    Button Start_Stop;
    Button Lap;
    Button Reset;
    TextView showTime;
    ListView list;
    String[] stringArray = new String[] {  };
    List<String> arrayList ;
    ArrayAdapter<String> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        handler = new Handler();
        Start_Stop = findViewById(R.id.Start_Stop);
        Lap = findViewById(R.id.Lap);
        Reset = findViewById(R.id.Reset);
        showTime = findViewById(R.id.textView);
        list = findViewById(R.id.listview);
        arrayList = new ArrayList<String>(Arrays.asList(stringArray));
        adapter = new ArrayAdapter<String>(Stopwatch.this,
                android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(adapter);

       Start_Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Start/Stop button clicked");
                if (Start_Stop.getText() == "Start") {
                    Start_Stop.setText("Stop");
                    start = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    Reset.setEnabled(false);
                    Lap.setEnabled(true);
                } else {
                    Start_Stop.setText("Start");
                    timeBuff += miliSecond;
                    handler.removeCallbacks(runnable);
                    Reset.setEnabled(true);
                    Lap.setEnabled(false);
                }
            }

        });

        Lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Lap button clicked");
                arrayList.add(showTime.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Reset button clicked");
                miliSecond = 0L ;
                start = 0L ;
                timeBuff = 0L ;
                update = 0L ;
                second = 0 ;
                minutes = 0 ;
                miliseconds = 0 ;
                showTime.setText("00:00:000");
                arrayList.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            miliSecond = SystemClock.uptimeMillis() - start;
            update = timeBuff + miliSecond;
            second = (int) (update / 1000);
            minutes = second / 60;
            second = second % 60;
            miliseconds = (int) (update % 1000);
            showTime.setText("" + minutes + ":"
                    + String.format("%02d", second) + ":"
                    + String.format("%03d", miliseconds));
            handler.postDelayed(this, 0);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stopwatch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
