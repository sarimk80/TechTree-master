package mk.bumble.RecyclerView;


import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jcminarro.roundkornerlayout.RoundKornerRelativeLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import mk.bumble.Content.Content_activity;
import mk.bumble.R;

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
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();

        final List_View_Setters list_view_setters1 = list_view_setters.get(position);
       // holder.projects.setText(list_view_setters1.getName_project());
        Log.d("TAG", list_view_setters1.getProjectImageUrl());
        StorageReference gsReference = storage.getReferenceFromUrl(list_view_setters1.getProjectImageUrl());
        //Picasso.get().load(list_view_setters1.getProjectImageUrl()).into(holder.imageView);
        //StorageReference spaceRef = storageRef.(list_view_setters1.getProjectImageUrl());
       // Log.d("TAG", spaceRef.getDownloadUrl().toString());
        //Glide.with(holder.imageView.getContext()).load(spaceRef.getDownloadUrl()).into(holder.imageView);
        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("TAG", uri.toString());
                Glide.with(holder.imageView.getContext()).load(uri.toString()).into(holder.imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", e.toString());
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,"Click on card",Toast.LENGTH_LONG).show();
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
