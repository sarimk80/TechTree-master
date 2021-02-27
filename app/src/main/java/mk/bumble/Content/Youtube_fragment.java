package mk.bumble.Content;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import mk.bumble.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Youtube_fragment extends Fragment {

    private YouTubePlayer _youTubePlayer;
    String apiKey = "AIzaSyBhCQpmfjGFkAGFs3qjI6CGtIM71XBvagQ";
    String youtube_id;


    public Youtube_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_youtube_fragment, container, false);
        Content_activity content_activity = (Content_activity) getActivity();
        youtube_id=content_activity.Send_youtube();


        YouTubePlayerSupportFragment youTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction=getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment,youTubePlayerSupportFragment).commit();

        youTubePlayerSupportFragment.initialize(apiKey, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    _youTubePlayer=youTubePlayer;
                    _youTubePlayer.setFullscreen(false);
                    _youTubePlayer.pause();
                    //_youTubePlayer.loadVideo(youtube_id);
                    _youTubePlayer.cueVideo(youtube_id);

                    //_youTubePlayer.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

        return view;
    }


}
