package guy.capsulab.class23a_and_7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import guy.capsulab.class23a_and_7.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    private MaterialButton main_BTN_start;
    private MaterialButton main_BTN_stop;
    private MaterialButton main_BTN_game;
    private LinearProgressIndicator main_PRG_download;

    private BroadcastReceiver radio_tal = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }

            runOnUiThread(() -> {
                int prg = intent.getIntExtra(ServiceSteps.DOWNLOAD_PROGRESS, 0);
                updateProgress(prg);
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_BTN_start = findViewById(R.id.main_BTN_start);
        main_BTN_stop = findViewById(R.id.main_BTN_stop);
        main_BTN_game = findViewById(R.id.main_BTN_game);
        main_PRG_download = findViewById(R.id.main_PRG_download);


        main_BTN_start.setOnClickListener(v -> start());
        main_BTN_stop.setOnClickListener(v -> stop());
        main_BTN_game.setOnClickListener(v -> game());
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(radio_tal, new IntentFilter(ServiceSteps.FM_99));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(radio_tal);
    }

    private void updateProgress(int prg) {
        main_PRG_download.setProgress(prg + 1);
    }

    private void game() {
        startActivity(new Intent(this, Activity_Game.class));
        finish();
    }

    private void start() {
        main_PRG_download.setMax(10);
        main_PRG_download.setProgress(0);
        Intent intent = new Intent(this, ServiceSteps.class);
        intent.setAction(ServiceSteps.ServiceSteps_ACTION_START);
        startService(intent);
    }

    private void stop() {
        Intent intent = new Intent(this, ServiceSteps.class);
        intent.setAction(ServiceSteps.ServiceSteps_ACTION_STOP);
        startService(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public boolean onSupportNavigateUp() {
        return false;
    }
}