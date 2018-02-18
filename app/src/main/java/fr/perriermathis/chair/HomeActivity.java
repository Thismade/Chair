package fr.perriermathis.chair;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Handler;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private  Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent;
        setContentView(R.layout.activity_home);
        launchAnotherActivity(checkFirstUse());
    }

    protected void onResume(){
        super.onResume();
        launchAnotherActivity(checkFirstUse());
    }

    private Intent checkFirstUse(){
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String username = pref.getString("pseudo", "DEFAULT");
        Toast.makeText(getApplicationContext(), "Profil trouvé:  "+username, Toast.LENGTH_LONG).show();
        if(username.isEmpty() || username == "DEFAULT"){
            // register
            return new Intent(HomeActivity.this, MainActivity.class);
        }
        else{
            // login
            return new Intent(HomeActivity.this, LoginActivity.class);
        }
    }

    private void launchAnotherActivity(final Intent intent){
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }, 3000);
    }
}
