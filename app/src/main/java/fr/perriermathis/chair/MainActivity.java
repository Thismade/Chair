package fr.perriermathis.chair;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = (Button) findViewById(R.id.login);

        final EditText pseudo = (EditText) findViewById(R.id.pseudo);
        final EditText password = (EditText) findViewById(R.id.password);
        final EditText key = (EditText) findViewById(R.id.beta);

        // On met un Listener sur le bouton
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String S_pseudo, S_password, S_key;

                S_pseudo = pseudo.getText().toString();
                S_password = password.getText().toString();
                S_key = key.getText().toString();

                if(!S_pseudo.isEmpty() && !S_password.isEmpty() && !S_key.isEmpty()){
                    RegisterTask register = new RegisterTask(getApplicationContext());
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
}
