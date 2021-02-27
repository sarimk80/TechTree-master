package mk.bumble.Content;

import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mk.bumble.R;

public class Content_activity extends AppCompatActivity {

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */

    /**
     * The {@link ViewPager} that will host the section contents.
     */


    private ViewPager mViewPager;

    @BindView(R.id.heading)
    TextView textView;

    @BindView(R.id.back)
    ImageView imageView;

    String Description;

    String Code;

    String Image, Build, Things, code_function,youtube_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        ButterKnife.bind(this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        SetupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(mViewPager);

        String Title = getIntent().getStringExtra("Title");

        Description = getIntent().getStringExtra("Description");

        Code = getIntent().getStringExtra("Code");

        Image = getIntent().getStringExtra("Image");

        Build = getIntent().getStringExtra("Build");

        Things = getIntent().getStringExtra("Things");

        code_function = getIntent().getStringExtra("Functionality");

        youtube_id=getIntent().getStringExtra("Youtube_id");


        textView.setText(Title);


    }

    public String getDescription() {
        return Description;
    }

    public String Send_Code() {
        return Code;
    }

    public String Send_Image() {
        return Image;
    }

    public String Send_Build() {
        return Build;
    }

    public String Send_Things() {
        return Things;
    }

    public String Send_Code_Function() {
        return code_function;
    }

    public String Send_youtube(){
        return youtube_id;
    }


    private void SetupViewPager(ViewPager viewPager) {

        SectionPageAdapter sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        sectionPageAdapter.AddFragments(new Content_fragment(), "Text Guide");
        sectionPageAdapter.AddFragments(new Youtube_fragment(), "Video Guide");

        viewPager.setAdapter(sectionPageAdapter);


    }

    @OnClick(R.id.back)
    public void Back() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }


}
