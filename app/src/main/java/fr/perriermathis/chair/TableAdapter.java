package fr.perriermathis.chair;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shormir on 19/02/2018.
 */

public class TableAdapter extends ArrayAdapter<Table>{

    private Context context;
    private int ressource;
    private ArrayList<Table> tables = null;

    public TableAdapter(Context context, int resource, ArrayList<Table> tables) {
        super(context, resource);
        this.tables = tables;
        this.context = context;
        this.ressource = resource;
    }

    @Override
    public int getCount() {
        return this.tables.size();
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Table table = this.tables.get(position);
        // Instancier une seule fois la vue xml
        if(convertView == null){
            // Create the view on the convertView
            convertView = layoutInflater.inflate(R.layout.table_layout  , parent, false);
        }

        TextView tableTitle = (TextView) convertView.findViewById(R.id.tableTitle);
        TextView tableLastMessage = (TextView) convertView.findViewById(R.id.tableLastMessage);
        ImageView tableImage = (ImageView) convertView.findViewById(R.id.tableImage);

        tableTitle.setText(table.getTitle());
        tableLastMessage.setText(table.getLastMessage());
        tableImage.setImageResource(table.getImage());

        return convertView;
    }
}
