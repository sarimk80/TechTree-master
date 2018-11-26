package mk.techtree.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcminarro.roundkornerlayout.RoundKornerRelativeLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import mk.techtree.Content.Content_activity;
import mk.techtree.R;
import spencerstudios.com.bungeelib.Bungee;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    List<List_View_Setters> list_view_setters;
    LayoutInflater layoutInflater;

    public Adapter(Context context, List<List_View_Setters> list_view_setters) {
        this.context = context;
        this.list_view_setters = list_view_setters;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.card, parent, false);

        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, final int position) {

        final List_View_Setters list_view_setters1 = list_view_setters.get(position);
        holder.projects.setText(list_view_setters1.getName_project());
        //Picasso.get().load(list_view_setters1.getCard_image()).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), Content_activity.class);
                intent.putExtra("Title",list_view_setters1.getName_project());
                intent.putExtra("Description",list_view_setters1.getDescription());
                intent.putExtra("Code",list_view_setters1.getCode());
                intent.putExtra("Image",list_view_setters1.getImage());
                intent.putExtra("Things",list_view_setters1.getThings());
                intent.putExtra("Build",list_view_setters1.getBuild());
                intent.putExtra("Functionality",list_view_setters1.getFuntionality());
                intent.putExtra("Youtube_id",list_view_setters1.getYoutube_id());
                view.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list_view_setters.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView projects;
        RoundKornerRelativeLayout cardView;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            projects = itemView.findViewById(R.id.project);
            cardView = itemView.findViewById(R.id._card);
            imageView=itemView.findViewById(R.id.card_image);

        }
    }
}
