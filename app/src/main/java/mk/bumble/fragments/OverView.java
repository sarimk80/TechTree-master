package mk.bumble.fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mk.bumble.R;
import mk.bumble.abstracts.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class OverView extends Fragment {


    public OverView() {
        // Required empty public constructor
    }

    @BindView(R.id.raspberry)
    ImageView raspberry;

    @BindView(R.id.kit)
    ImageView kit;

    @BindView(R.id.textview)
    TextView textView;

    Unbinder unbind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_over_view, container, false);
        unbind = ButterKnife.bind(this, view);

        textView.setText("Overview");


        return view;
    }

    @OnClick(R.id.raspberry)
    public void rasp() {


    }


    @OnClick(R.id.kit)
    public void kit() {


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    @OnClick({R.id.imgLogout, R.id.back, R.id.raspberry, R.id.kit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgLogout:
                BaseFragment.logoutClick(getContext(), getActivity());
                break;
            case R.id.back:
                break;
            case R.id.raspberry:
                addDockableFragment(R.id.placeholder, new RaspberryPi());
                break;
            case R.id.kit:
                addDockableFragment(R.id.placeholder, new Kit());
                break;
        }
    }


    public void addDockableFragment(int id, Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }


}
