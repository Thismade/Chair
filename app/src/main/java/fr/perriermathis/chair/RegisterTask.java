package fr.perriermathis.chair;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Button;
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

class RegisterTask extends AsyncTask<Void, Integer, String> {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    private Map<String, String> values = new HashMap<>();
    private Context applicationContext;
    private String result;
    private String url;
    private Button loginButton;

    public RegisterTask(Context applicationContext, Button loginButton){
        this.applicationContext = applicationContext;
        this.result = "Connexion";
        this.url = "http://www.perriermathis.fr/labs/android/php/register.php";
        this.loginButton = loginButton;
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

    public byte[] getHash(String password) {
        MessageDigest digest=null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        digest.reset();
        return digest.digest(password.getBytes());
    }

    static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length*2) + "X", new BigInteger(1, data));
    }

    @Override
    protected String doInBackground(Void... arg0) {
        HttpURLConnection conn;
        try {
            // Enter URL address where your php file resides
            this.url+="?pseudo="+this.values.get("pseudo");
            this.url+="&password="+bin2hex(getHash(this.values.get("password")));
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
                this.result = result.toString();
                // Pass data to onPostExecute method

                // End
                return this.values.get("pseudo");

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
        // Save the data on the phone to login
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.applicationContext);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("pseudo", this.values.get("pseudo"));
        edit.putString("betaKey", this.values.get("betaKey"));
        edit.apply();

        loginButton.setEnabled(true);


        String attemptedResult = bin2hex(getHash(this.values.get("pseudo")))+bin2hex(getHash(this.values.get("password")));
        if(this.result.equals(attemptedResult)) {
            Intent i = new Intent(this.applicationContext, TableActivity.class);
            this.applicationContext.startActivity(i);
        }
        else{
            Toast.makeText(getApplicationContext(), this.result, Toast.LENGTH_LONG).show();
        }
    }

    public Context getApplicationContext() {
        return applicationContext;
    }
}
