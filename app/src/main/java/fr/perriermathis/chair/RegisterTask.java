package fr.perriermathis.chair;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shormir on 18/02/2018.
 */

class RegisterTask extends AsyncTask<Void, Integer, String> {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    private Map<String, String> values = new HashMap<>();
    private Context applicationContext;
    private String result;
    private String url;

    public RegisterTask(Context applicationContext){
        this.applicationContext = applicationContext;
        this.result = "Connexion";
        this.url = "http://www.perriermathis.fr/labs/android/test.php";
    }

    public void setValue(String key, String value){
        this.values.put(key, value);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(getApplicationContext(), "Logging in..", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values){
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(Void... arg0) {
        HttpURLConnection conn;
        try {
            // Enter URL address where your php file resides
            this.url+="?pseudo="+this.values.get("pseudo");
            this.url+="&password="+this.values.get("password");
            this.url+="&betaKey="+this.values.get("betaKey");

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
                this.result = "Reçu: "+result.toString();
                // Pass data to onPostExecute method
                return (result.toString());

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
        Toast.makeText(getApplicationContext(), this.result, Toast.LENGTH_LONG).show();
    }

    public Context getApplicationContext() {
        return applicationContext;
    }
}
