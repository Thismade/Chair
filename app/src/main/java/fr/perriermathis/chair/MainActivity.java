package fr.perriermathis.chair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Intent login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button loginButton = (Button) findViewById(R.id.login);

        final EditText pseudo = (EditText) findViewById(R.id.pseudo);
        final EditText password = (EditText) findViewById(R.id.password);
        final EditText key = (EditText) findViewById(R.id.beta);

        final TextView registerButton = (TextView) findViewById(R.id.goToLogin);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login);
            }
        });

        // On met un Listener sur le bouton
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                hideKeyboardFrom(getApplicationContext(), findViewById(android.R.id.content));
                String S_pseudo, S_password, S_key;

                S_pseudo = pseudo.getText().toString();
                S_password = password.getText().toString();
                S_key = key.getText().toString();

                if(!S_pseudo.isEmpty() && !S_password.isEmpty() && !S_key.isEmpty()){
                    RegisterTask register = new RegisterTask(getApplicationContext(), loginButton);
                    register.setValue("pseudo", S_pseudo);
                    register.setValue("password", S_password);
                    register.setValue("betaKey", S_key);
                    register.execute();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Remplissez tous les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
