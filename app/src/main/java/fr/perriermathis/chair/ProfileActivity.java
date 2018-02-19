package fr.perriermathis.chair;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView pseudo = (TextView) findViewById(R.id.pseudo);
        TextView rang = (TextView) findViewById(R.id.rang);
        rang.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out));

        Button retour = (Button) findViewById(R.id.retour);


        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 1. Set the pseudo

        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String username = pref.getString("pseudo", "DEFAULT");

        pseudo.setText(username);

        // 2. Launch the rang loading

        getUserInfoTask getRangUser = new getUserInfoTask(getApplicationContext(), pseudo, rang);
        getRangUser.execute();

        // 3. Launch the lastTables display

        ListView lastTables = (ListView) findViewById(R.id.lastTables);

        ArrayList<Table> participedTables = new ArrayList<>();
            participedTables.add(new Table("And old table", R.drawable.party));
            participedTables.add(new Table("Another nice table", R.drawable.party));
            participedTables.add(new Table());

        TableAdapter adapter = new TableAdapter(getApplicationContext(), R.layout.table_layout, participedTables);

        lastTables.setAdapter(adapter);
    }
}
