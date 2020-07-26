package com.romankryvolapov.minecraftmaps;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GideFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GideFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GideFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GideFragment newInstance(String param1, String param2) {
        GideFragment fragment = new GideFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        LayoutInflater ltInflater = getLayoutInflater();
        View view = ltInflater.inflate(R.layout.fragment_gide, null, false);

        ImageView imageView_manual_1 = view.findViewById(R.id.imageView_manual_1);
        ImageView imageView_manual_2 = view.findViewById(R.id.imageView_manual_2);
        ImageView imageView_manual_3 = view.findViewById(R.id.imageView_manual_3);
        ImageView imageView_manual_4 = view.findViewById(R.id.imageView_manual_4);
        ImageView imageView_manual_5 = view.findViewById(R.id.imageView_manual_5);
        ImageView imageView_manual_6 = view.findViewById(R.id.imageView_manual_6);

        imageView_manual_1.setScaleType(ImageView.ScaleType.FIT_XY);

        imageView_manual_2.setImageDrawable(roundedBitmapDrawable(R.drawable.manual_image_2));
        imageView_manual_3.setImageDrawable(roundedBitmapDrawable(R.drawable.manual_image_3));
        imageView_manual_4.setImageDrawable(roundedBitmapDrawable(R.drawable.manual_image_4));
        imageView_manual_5.setImageDrawable(roundedBitmapDrawable(R.drawable.manual_image_5));
        imageView_manual_6.setImageDrawable(roundedBitmapDrawable(R.drawable.manual_image_6));


        return view;
    }

    private RoundedBitmapDrawable roundedBitmapDrawable(int resource){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resource);
        RoundedBitmapDrawable roundedBitmapDrawable = (RoundedBitmapDrawable) RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCornerRadius(100);
        return roundedBitmapDrawable;
    }


}
