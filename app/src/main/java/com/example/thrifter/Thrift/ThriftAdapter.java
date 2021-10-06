package com.example.thrifter.Thrift;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thrifter.R;
import com.example.thrifter.Thrift.ThriftCards;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ThriftAdapter extends RecyclerView.Adapter<ThriftAdapter.ViewHolder> {

    Context context;
    List<ThriftCards> thriftItemList;

    /** Hier wird der Adapter definiert um die Karten das dazugehörige Layout zuzordnen und
     * anzupassen. Desweiteren wird dieser auch per Viewholder mit der Recyclerview
     * verknüpft */

    public ThriftAdapter(Context context, List<ThriftCards> thriftItemList) {

        this.context = context;
        this.thriftItemList = thriftItemList;

    }


    //Hier wird ein Viewholder erstellt mit dem Karten-Layout
    @NonNull
    @Override
    public ThriftAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent, false);
        return new ViewHolder(v);

    }


    //Hier werden den einzelnen Layoutelementen der Karte die Werte zugeordnet
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThriftCards thriftCards = thriftItemList.get(position);
        holder.name.setText(thriftCards.getName());
        holder.beschreibung.setText(thriftCards.getDescription());
        holder.preis.setText(thriftCards.getPrice());

        String imageUri = null;
        imageUri=thriftCards.getImage();
        Picasso.get().load(imageUri).into(holder.imageView);

    }


    @Override
    public int getItemCount() { return thriftItemList.size(); }


    //Hier wird der Viewholder bzw. das Layout der Karte definiert und initialisiert
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, beschreibung, preis;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.itemImageInThrift);
            name = itemView.findViewById(R.id.itemNameInThrift);
            beschreibung = itemView.findViewById(R.id.itemDescInThrift);
            preis = itemView.findViewById(R.id.priceTagInThrift);

        }

    }

}
