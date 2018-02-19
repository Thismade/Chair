package fr.perriermathis.chair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    Intent register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button loginButton = (Button) findViewById(R.id.login);
        final TextView registerButton = (TextView) findViewById(R.id.goToRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(register);
            }
        });

        final EditText pseudo = (EditText) findViewById(R.id.pseudo);
        final EditText password = (EditText) findViewById(R.id.password);

        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String username = pref.getString("pseudo", "DEFAULT");

        pseudo.setText(username);

        // On met un Listener sur le bouton
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                hideKeyboardFrom(getApplicationContext(), findViewById(android.R.id.content));
                String S_pseudo, S_password, S_key;

                S_pseudo = pseudo.getText().toString();
                S_password = password.getText().toString();

                if(!S_pseudo.isEmpty() && !S_password.isEmpty()){
                    LoginTask login = new LoginTask(getApplicationContext(), loginButton);
                    login.setValue("pseudo", S_pseudo);
                    login.setValue("password", S_password);
                    login.execute();
                    loginButton.setEnabled(false);
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
