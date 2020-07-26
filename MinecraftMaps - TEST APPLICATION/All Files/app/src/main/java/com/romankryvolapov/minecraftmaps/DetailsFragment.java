package com.romankryvolapov.minecraftmaps;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class DetailsFragment extends Fragment {

    private TextView textView_title_details;
    private TextView textView_description_details;
    private ImageView imageView_details;
    private Button button_details;
    private FrameLayout fragment_container;
    private View view;
    public static int ID = 0;
    private String title_details;
    private String description_details;
    private String image_name;


    public DetailsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(MainActivity.LOG_TAG, "DetailsFragment onCreateView");
        view = inflater.inflate(R.layout.fragment_details, null);
        textView_title_details = view.findViewById(R.id.textView_title_details);
        textView_description_details = view.findViewById(R.id.textView_description_details);
        imageView_details = view.findViewById(R.id.imageView_details);
        button_details = view.findViewById(R.id.button_details);

        MyTask myTask = new MyTask();
        myTask.execute();

        return view;
    }

    class MyTask extends AsyncTask<Void, Void, Void> {
        public MyTask() {
            super();
        }

        @Override
        protected Void doInBackground(Void... voids) {


            try {
                InputStream inputStream = getActivity().getAssets().open("maps.json");
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();
                String jsonString = new String(buffer, "UTF-8");
                JSONArray jsonArray = new JSONArray(jsonString);
                JSONObject jsonObject_temp = jsonArray.getJSONObject(ID);
                JSONArray jsonArray2 = jsonObject_temp.getJSONArray("images");
                JSONObject jsonObject_temp2 = jsonArray2.getJSONObject(0);
                title_details = jsonObject_temp.getString(getResources().getString(R.string.json_title));
                description_details = jsonObject_temp.getString(getResources().getString(R.string.json_desc));
                image_name = jsonObject_temp2.getString("image");


            } catch (Exception e) {
                Log.d(MainActivity.LOG_TAG, "Exception " + e);
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            textView_title_details.setText(title_details);
            textView_description_details.setText(description_details);
            InputStream inputStream = null;
            try{
                inputStream = getActivity().getAssets().open(image_name);
                Drawable d = Drawable.createFromStream(inputStream, null);
                imageView_details.setImageDrawable(d);
                imageView_details.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            catch (IOException e){
                Log.d(MainActivity.LOG_TAG, e + "");
            }
            finally {
                try{
                    if(inputStream!=null)
                        inputStream.close();
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
            }

        }
    }
}
