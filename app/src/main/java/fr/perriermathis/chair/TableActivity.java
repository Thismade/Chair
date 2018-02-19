package fr.perriermathis.chair;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        TextView pseudo = (TextView) findViewById(R.id.pseudo);
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String username = pref.getString("pseudo", "Erreur..");
        pseudo.setText(username);

        // Tables Display //

        ListView tables = (ListView) findViewById(R.id.tableList);

        ArrayList<Table> allTables = new ArrayList<>();
        for(int i = 0; i < 15; i++){
            allTables.add(new Table());
        }

        TableAdapter tableAdapter = new TableAdapter(getApplicationContext(), R.layout.table_layout, allTables);
        tables.setAdapter(tableAdapter);
    }
}
