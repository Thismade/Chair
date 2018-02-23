package fr.perriermathis.chair;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableActivity extends AppCompatActivity {

    private static final String ENDPOINT = "http://www.perriermathis.fr/labs/android/php/getAllTables.php";
    private RequestQueue requestQueue;
    private TableAdapter tableAdapter;
    ArrayList<Table> allTables;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        // For Gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        requestQueue = Volley.newRequestQueue(this);

        // All the code

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

        allTables = new ArrayList<>();
        allTables.add(new Table("Table de la solitude", R.drawable.party));

        // Will load from php page all the tables of the database
        /*
        getAllTablesTask allTablesTask = new getAllTablesTask(getApplicationContext(), allTables);
        allTablesTask.execute();
        */
        // Deprecated

        /*for(int i = 0; i < 15; i++){
            allTables.add(new Table());
        }*/



        tableAdapter = new TableAdapter(getApplicationContext(), R.layout.table_layout, allTables);
        tables.setAdapter(tableAdapter);

        fetchPosts();

        tables.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide));

    }

    private void fetchPosts() {
        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("PostActivity", response);
            /*List<Table> posts = Arrays.asList(gson.fromJson(response, Table[].class));

            for(Table table : posts){
                allTables.add(table);
            }
            */

            Table table = gson.fromJson(response, Table.class);
            allTables.add(table);

            tableAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), String.valueOf(allTables.size()), Toast.LENGTH_LONG).show();

        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());
        }
    };
}
