package fr.perriermathis.chair;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        TextView pseudo = (TextView) findViewById(R.id.pseudo);
        TextView title = (TextView) findViewById(R.id.title);

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToProfile = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(goToProfile);
            }
        });

        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String username = pref.getString("pseudo", "Erreur..");
        pseudo.setText(username);

        // Tables Display //

        ListView tables = (ListView) findViewById(R.id.tableList);

        ArrayList<Table> allTables = new ArrayList<>();
        allTables.add(new Table("Table des fêtes", R.drawable.party));
        for(int i = 0; i < 15; i++){
            allTables.add(new Table());
        }

        TableAdapter tableAdapter = new TableAdapter(getApplicationContext(), R.layout.table_layout, allTables);
        tables.setAdapter(tableAdapter);
    }
}
