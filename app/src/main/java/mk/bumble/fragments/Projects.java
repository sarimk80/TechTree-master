package mk.bumble.fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mk.bumble.R;
import mk.bumble.RecyclerView.Adapter;
import mk.bumble.RecyclerView.List_View_Setters;

/**
 * A simple {@link Fragment} subclass.
 */
public class Projects extends Fragment {

    private Adapter adapter1;

    private List<List_View_Setters> list_view_setters;

    RecyclerView recyclerView;

    @BindView(R.id.textview)
    TextView textView;

    @BindView(R.id.loading)
    LottieAnimationView cardView;

    @BindView(R.id.imgLogout)
    ImageView logout;

    @BindView(R.id.network_lost)
    LottieAnimationView lottieAnimationView;

    //RequestQueue requestQueue;

    //String URL_Project = "https://script.googleusercontent.com/macros/echo?user_content_key=2rNaOG5UPvCFq_tzAgbqRyc6N9AKW18w4K340yf755hP-f1H1gz4sXD1qcR_E4leShK8mfgVEweyvQzTAHbVZ91u9hW3bgYcm5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnEvv0NuU4fnWMwos8OeCclCtOcjpagL8omPShBxVgozXBprjfYq2x5uqh-NrHuZ9ov3G9YKhFlrw&lib=MJ3U7ZcToRb8RwKP78SI0y7ZGgXJoIyjV";

    FirebaseFirestore db;

    public Projects() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        recyclerView = view.findViewById(R.id.recycleView);


        //cardView = view.findViewById(R.id.loading);
        //gifView = view.findViewById(R.id.gif1);

        //lottieAnimationView=view.findViewById(R.id.network_lost);

        ButterKnife.bind(this, view);

        //recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Access a Cloud Firestore instance from your Activity
         db = FirebaseFirestore.getInstance();

        list_view_setters = new ArrayList<>();

        textView.setText("Projects");

        cardView.setVisibility(View.VISIBLE);
        logout.setVisibility(View.INVISIBLE);

        //requestQueue = Volley.newRequestQueue(getContext());

        getData();


        return view;

    }


    private void getData() {

        db.collection("projects").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());
                        //Log.d("TAG", document.getData().get("code").toString() );
//                        String name = (String) document.child("name").getValue();
//                        String message = (String) document.child("message").getValue();

                        mk.bumble.models.Projects projects=document.toObject(mk.bumble.models.Projects.class);

                        //Log.d("TAG", projects.getProjectImageUrl());

                        List_View_Setters _list_view_setters = new List_View_Setters(
//
                               projects.getProjectName(),
                                projects.getIntroduction(),
                                projects.getCode(),
                                projects.getImageUrl(),
                                projects.getThingsYouNeed(),
                                projects.getHowToBuild(),
                                projects.getFunctionality(),
                                projects.getYoutubeUrl(),
                                projects.getImageUrl(),
                                projects.getId(),
                                projects.getProjectImageUrl()

                        );

                        list_view_setters.add(_list_view_setters);
                        adapter1 = new Adapter(getContext(), list_view_setters);
                        recyclerView.setAdapter(adapter1);
                        adapter1.notifyDataSetChanged();

                        cardView.setVisibility(View.INVISIBLE);

//                      List_View_Setters _listViewSetter=new List_View_Setters(
//                              document.getData().get("code").toString(),
//                              document.getData().get("code").toString(),
//                              document.getData().get("code").toString(),
//                              document.getData().get("code").toString(),
//                              document.getData().get("code").toString(),
//                              document.getData().get("code").toString(),
//                              document.getData().get("code").toString(),
//                              document.getData().get("code").toString(),
//                              document.getData().get("code").toString()
//
//                      );


//                        list_view_setters.add(_listViewSetter);
//                        adapter1 = new Adapter(getContext(), list_view_setters);
//                        recyclerView.setAdapter(adapter1);
//                        adapter1.notifyDataSetChanged();
//
//                        cardView.setVisibility(View.INVISIBLE);

                    }
                }else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }



            }
        });


//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_Project, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//                    JSONArray jsonArray = response.getJSONArray("user");
//                    for (int i = 0; i <= jsonArray.length(); i++) {
//
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        List_View_Setters _list_view_setters = new List_View_Setters(
//
//                                jsonObject.getString("name"),
//                                jsonObject.getString("description"),
//                                jsonObject.getString("code"),
//                                jsonObject.getString("image"),
//                                jsonObject.getString("things"),
//                                jsonObject.getString("build"),
//                                jsonObject.getString("funtionality"),
//                                jsonObject.getString("youtube"),
//                                jsonObject.getString("cardImage")
//
//                        );
//                        list_view_setters.add(_list_view_setters);
//                        adapter1 = new Adapter(getContext(), list_view_setters);
//                        recyclerView.setAdapter(adapter1);
//                        adapter1.notifyDataSetChanged();
//
//                        cardView.setVisibility(View.INVISIBLE);
//
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    //Toast.makeText(getContext(), "Oops , Something Went Wrong", Toast.LENGTH_LONG).show();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                cardView.setVisibility(View.INVISIBLE);
//
//                lottieAnimationView.setVisibility(View.VISIBLE);
//                Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
//
//
//            }
//        });
//
//        requestQueue.add(jsonObjectRequest);


    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        requestQueue.cancelAll(new RequestQueue.RequestFilter() {
//            @Override
//            public boolean apply(Request<?> request) {
//                return true;
//            }
//        });
//    }

}
