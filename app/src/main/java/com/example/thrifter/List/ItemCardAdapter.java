package com.example.thrifter.List;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thrifter.List.ListItem;
import com.example.thrifter.R;
import com.squareup.picasso.Picasso;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.List;

public class ItemCardAdapter extends RecyclerView.Adapter<ItemCardAdapter.ViewHolder> {

    Context context;
    List<ListItem> listItemList;

    /** Hier wird der Adapter definiert um die Karten das dazugehörige Layout zuzordnen und
     * anzupassen. Desweiteren wird dieser auch per Viewholder mit der Recyclerview
     * verknüpft */

    public ItemCardAdapter(Context context, List<ListItem> listItemList){

        this.context = context;
        this.listItemList = listItemList;

    }


    //Hier wird ein Viewholder erstellt mit dem Karten-Layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcard,parent, false);
        return new ViewHolder(v);

    }


    //Hier werden den einzelnen Layoutelementen der Karte die Werte zugeordnet
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem listItem = listItemList.get(position);
        holder.name.setText(listItem.getName());
        holder.beschreibung.setText(listItem.getDescription());
        holder.preis.setText(listItem.getPrice());

        String imageUri = null;
        imageUri=listItem.getImage();
        Picasso.get().load(imageUri).into(holder.imageView);



    }


    @Override
    public int getItemCount() {
        return listItemList.size();
    }


    //Hier wird der Viewholder bzw. das Layout der Karte definiert und initialisiert
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, beschreibung, preis;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.itemimage);
            name = itemView.findViewById(R.id.itemname);
            beschreibung = itemView.findViewById(R.id.itemdesc);
            preis = itemView.findViewById(R.id.pricetag);

        }

    }

}
