package fr.perriermathis.chair;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shormir on 18/02/2018.
 */

class getAllTablesTask extends AsyncTask<Void, Integer, String> {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    private Map<String, String> values;
    private Context applicationContext;
    private String result;
    private String url;
    private TextView pseudo, rang;

    public getAllTablesTask(Context applicationContext, TextView pseudo, TextView rang){
        this.applicationContext = applicationContext;
        this.url = "http://www.perriermathis.fr/labs/android/php/getAllTables.php";
        this.pseudo = pseudo;
        this.rang = rang;
        this.values = new HashMap<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Nothing //
        //Toast.makeText(getApplicationContext(), "Logging in..", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values){
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(Void... arg0) {
        HttpURLConnection conn;
        try {

            URL url = new URL(this.url);
            conn = (HttpURLConnection) url.openConnection();
            // Setup HttpURLConnection class to send and receive data from php
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("POST");
            // setDoOutput to true as we recieve data from json file
            conn.setDoInput(true);
            conn.setDoOutput(true);

            int response_code = conn.getResponseCode();

            // Check if successful connection made
            if (response_code == HttpURLConnection.HTTP_OK) {
                // Read data sent from server
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                conn.disconnect();


                // On renseigne le rang
                this.values.put("rang", result.toString());

                return "ok";
            } else {
                this.result = "Erreur.. Réessayez";
                return ("unsuccessful");
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.result = e.toString();
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // Empty
        this.rang.setText(this.values.get("rang"));
    }
}
