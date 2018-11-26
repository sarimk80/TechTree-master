package mk.techtree.Content;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import mk.techtree.R;

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

        paragraph = view.findViewById(R.id.paragraph);
        code = view.findViewById(R.id.code);
        imageView = view.findViewById(R.id.image);


        String Description = content_activity.getDescription();
        String Code = content_activity.Send_Code();
        String Image = content_activity.Send_Image();
        String Build=content_activity.Send_Build();
        String Things=content_activity.Send_Things();
        String Code_function=content_activity.Send_Code_Function();

        Picasso.get().load(Image).into(imageView);


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
