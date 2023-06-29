package com.example.tugas2_pemrogramanmobile;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BaterryStatus extends AppCompatActivity {
    private ProgressBar bar;
    private ImageView status;
    private TextView level;
    private Button butKembali;
    private Button btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.batt);
        bar = (ProgressBar) findViewById(R.id.bar);
        status = (ImageView) findViewById(R.id.status);
        level = (TextView) findViewById(R.id.level);
        registerReceiver(onBattery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        butKembali = (Button) findViewById(R.id.butKembali);
        btnMenu = (Button) findViewById(R.id.btnMenu);

        final String[] option = {"Facebook", "Twitter", "Telpon", "Sms"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, option);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Option");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    Intent intfb = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://facebook.com"));
                    startActivity(intfb);
                } else if (i == 1) {
                    Intent inttw = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://twitter.com"));
                    startActivity(inttw);
                } else {
                    if (i == 2) {
                        Intent inttel = new Intent(Intent.ACTION_CALL,
                                Uri.parse("tel:+628888999"));
                        startActivity(inttel);
                    } else {
                        Intent intsms = new Intent(Intent.ACTION_SENDTO,
                                Uri.parse("smsto:+6288889999"));
                        intsms.putExtra("sms_body", "Halo apa kabar");
                        startActivity(intsms);
                    }
                }
            }
        });
        final AlertDialog a = builder.create();
        btnMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                a.show();
            }
        });

        butKembali.setOnClickListener(arg0 -> {
            Intent intent = new Intent(BaterryStatus.this, MainActivity.class);
            startActivity(intent);
        });
    }

    BroadcastReceiver onBattery = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            int pct =
                    100 * intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 1)
                            / intent.getIntExtra(BatteryManager.EXTRA_SCALE, 1);
            bar.setProgress(pct);
            level.setText(String.valueOf(pct));
            switch (intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    status.setImageResource(R.drawable.charging);
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    int plugged =
                            intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                    if (plugged == BatteryManager.BATTERY_PLUGGED_AC
                            || plugged == BatteryManager.BATTERY_PLUGGED_USB) {
                        status.setImageResource(R.drawable.full);
                    } else {
                        status.setImageResource(R.drawable.unplugged);
                    }
                    break;
                default:
                    status.setImageResource(R.drawable.unplugged);
                    break;
            }
        }
    };
}
