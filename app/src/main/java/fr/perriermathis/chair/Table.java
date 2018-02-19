package fr.perriermathis.chair;

import java.util.ArrayList;

/**
 * Created by Shormir on 19/02/2018.
 */

public class Table {

    public static final int MAX_MESSAGES = 100;
    private String tableName;
    private ArrayList<String> messages;
    private int image;


    public Table(){
        this.tableName = "The Round Table";
        this.image = R.drawable.ic_launcher_background;
        this.messages = new ArrayList<>();
    }

    public Table(String name, int image){
        this.tableName = name;
        this.messages = new ArrayList<>();
        this.image = image;

    }

    public String getTitle() {
        return  this.tableName;
    }

    public String getLastMessage(){
        if (this.messages.size() > 1)
            return this.messages.get(this.messages.size()-1);
        else{
            return "Soyez le premier à écrire ici!";
        }
    }

    public int getImage(){
        return this.image;
    }
}
