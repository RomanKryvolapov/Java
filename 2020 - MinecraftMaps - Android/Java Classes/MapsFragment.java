package com.romankryvolapov.minecraftmaps;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MapsFragment extends Fragment {

    private FrameLayout fragment_container;
    private ArrayList<Navigation> navigations;
    private LinearLayout linearLayout;

    public MapsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment_container = getActivity().findViewById(R.id.fragment_container);
        MyTask myTask = new MyTask();
        myTask.execute();
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    // Этот клас содержит элементы для отрисовки, они будут добавлены в ArrayList
    private class Navigation {

        private Drawable image;
        private int id;
        private String name;
        private String description;
        private String filename;

        private Navigation(int id, String name, String description, String filename, Drawable image) {
            this.image = image;
            this.id = id;
            this.name = name;

            // если описание длинней 100 символов, оно обрезается
            this.description = description.substring(0, 100) + " ...";
            this.filename = filename;
        }
    }

    // здесь есть дублирование кода с Избранным
    // знаю, что лучше бы объеденить оба кода в один метод, но несколько полей отличаюстя, и не стал так делать, чтобы не запутаться

    private class MyTask extends AsyncTask<Void, Void, Void> {

        Drawable drawable;
        int iterator = 0;

        public MyTask() {
            super();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            // добавление в фоне элементов для отрисовки из json в ArrayList <Navigation>
            // не стал заморачиватсья на счет обработки ошибок, так как здесь или работает, или нет, так как все в памяти, а не в сети
            navigations = new ArrayList<Navigation>();
            try {
                InputStream inputStream = getActivity().getAssets().open("maps.json");
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();
                String jsonString = new String(buffer, "UTF-8");
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject_temp = jsonArray.getJSONObject(i);
                    JSONArray jsonArray2 = jsonObject_temp.getJSONArray("images");
                    JSONObject jsonObject_temp2 = jsonArray2.getJSONObject(0);
                    InputStream inputStream_image = null;
                    try {
                        inputStream_image = getActivity().getAssets().open(jsonObject_temp2.getString("image"));
                        drawable = Drawable.createFromStream(inputStream_image, null);
                    } catch (IOException e) {
                        Log.d(MainActivity.LOG_TAG, e + "");

                        // не проверял на счет утечек памяти, но вроде не должно быть
                    } finally {
                        try {
                            if (inputStream_image != null)
                                inputStream_image.close();
                        } catch (IOException ex) {
                            Log.d(MainActivity.LOG_TAG, ex + "");
                        }
                    }

                    // экземпляры класса добавляются в ArrayList
                    navigations.add(new Navigation(i, jsonObject_temp.getString(getResources().getString(R.string.json_title)), jsonObject_temp.getString(getResources().getString(R.string.json_desc)), jsonObject_temp2.getString("image"), drawable));
                }
            } catch (Exception e) {
                Log.d(MainActivity.LOG_TAG, "Exception " + e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            // после добавления всех элементов в ArraList они загружаются в UI добавлением в LinearLayout
            // лучше бы было использовать recyclerview но будет больше кода и сложней, грузится за пол секунды даже в эмуляторе
            LayoutInflater ltInflater = getLayoutInflater();
            for (int i = 0; i < navigations.size(); i++) {
                iterator = navigations.get(i).id;
                View view = ltInflater.inflate(R.layout.navigation_view, null, false);
                ImageView navigation_imageView = view.findViewById(R.id.navigation_imageView);
                TextView navigation_textView_name = view.findViewById(R.id.navigation_textView_name);
                TextView navigation_textView_description = view.findViewById(R.id.navigation_textView_description);
                ImageView imageView_favorites = view.findViewById(R.id.imageView_favorites);
                ConstraintLayout layout_listiner = view.findViewById(R.id.layout_listiner);
                navigation_textView_name.setText(navigations.get(i).name);
                navigation_textView_description.setText(navigations.get(i).description);
                navigation_imageView.setImageDrawable(navigations.get(i).image);
                navigation_imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                layout_listiner.setOnClickListener(new View.OnClickListener() {
                    int number = iterator;
                    @Override
                    public void onClick(View v) {
                        DetailsFragment.ID = number;
                        Fragment fragment = new DetailsFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
                    }
                });
                if (MainActivity.favorite_or_not(i))
                    imageView_favorites.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.favorite_black));
                else
                    imageView_favorites.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.favorite));

                imageView_favorites.setOnClickListener(new View.OnClickListener() {

                    // здесь есть интересная фишка- number для каждого слушателя различный
                    // в интернете все пишут определять кнопку по id, но этот код также работает, придумал его сам в ходе экспериментов в другом приложении
                    int number = iterator;
                    @Override
                    public void onClick(View v) {
                        if (MainActivity.favorite_or_not(number)) {
                            MainActivity.femove_from_favorites(number, getContext(), navigations.get(number).name);
                            imageView_favorites.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.favorite));
                        } else {
                            MainActivity.add_to_favorites(number, getContext(), navigations.get(number).name);
                            imageView_favorites.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.favorite_black));
                        }
                    }
                });
                linearLayout = null;
                linearLayout = getActivity().findViewById(R.id.linear_layout_list);
                linearLayout.addView(view);
            }

        }
    }

}
