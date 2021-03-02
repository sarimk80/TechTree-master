package mk.bumble.Content;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import mk.bumble.R;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class Content_fragment extends Fragment {


    TextView paragraph;
    TextView code;
    ImageView imageView;

    @BindView(R.id.things)
    TextView things;

    @BindView(R.id.build)
    TextView build;

    @BindView(R.id.code_function)
    TextView code_finction;

    public Content_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_content_fragment, container, false);
        Content_activity content_activity = (Content_activity) getActivity();
        ButterKnife.bind(this,view);

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();




        paragraph = view.findViewById(R.id.paragraph);
        code = view.findViewById(R.id.code);
        imageView = view.findViewById(R.id.image);


        String Description = content_activity.getDescription();
        String Code = content_activity.Send_Code();
        String Image = content_activity.Send_Image();
        String Build=content_activity.Send_Build();
        String Things=content_activity.Send_Things();
        String Code_function=content_activity.Send_Code_Function();

       // Picasso.get().load(Image).into(imageView);

        Log.d("TAG", Image);
        StorageReference gsReference = storage.getReferenceFromUrl(Image);

        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("TAG", uri.toString());
                Glide.with(getApplicationContext()).load(uri.toString()).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", e.toString());
            }
        });


       // Glide.with(getContext()).load(Image).into(imageView);



        Description = Description.replace("\\n", System.getProperty("line.separator"));
        Code = Code.replace("\\n", System.getProperty("line.separator"));
        Code = Code.replace("\\t", "       ");
        Build=Build.replace("\\n", System.getProperty("line.separator"));
        Things=Things.replace("\\n", System.getProperty("line.separator"));
        Code_function=Code_function.replace("\\n", System.getProperty("line.separator"));

        paragraph.setText(Description);
        code.setText(Code);
        things.setText(Things);
        build.setText(Build);
        code_finction.setText(Code_function);

        return view;
    }

}
