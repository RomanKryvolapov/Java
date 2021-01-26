package com.romankryvolapov.minecraftmaps;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DetailsFragment extends Fragment {

    private TextView textView_title_details;
    private TextView textView_description_details;
    private ImageView imageView_details;
    private Button button_details;
    private View view;

    // ID передается сюда из просмотра. Так делать не правильно, но мне показалось так проще, чем вкладывать ID в Intent- это всего лишь число
    public static int ID = 0;
    private String title_details;
    private String description_details;
    private String image_name;
    private boolean isInstalled;

    File filePath;
    String path;
    File file;
    String filename;

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

        // этот код проверяет, установлена ли игра
        // код немного рискованный, так как если поменяется имя пакета игры (ну мало ли), все перестанет работать, но другого варианта проверки не нашел
        PackageManager packageManager = getContext().getPackageManager();
        isInstalled = isPackageInstalled("com.mojang.minecraftpe", packageManager);
        Log.d(MainActivity.LOG_TAG, "isPackageInstalled = " + isInstalled);

        MyTask myTask = new MyTask();
        myTask.execute();

        // на счет этого кода не до конца уверен на счет работы в старых версиях- вроде как там были какие то изменения в пути файлов
        filePath = getContext().getExternalFilesDir("");
        path = filePath.getAbsolutePath();
        Log.d(MainActivity.LOG_TAG, "path = " + path);

        // getContext() иcпользовать конечно не хорошо, но не думаю, что он повлияет на производительность.
        // Еще тут должна быть Reactive Java а не AsyncTask

        return view;
    }

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    // здесь я объеденил загрузку в память и установку, так как все это происходит мгновенно, и не нужно заставлять пользователя по несколько раз щелкать на кнопку
    // он все равно не знает, что куда там загружается
    class LoadToMemory extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            file = new File(path, filename);
            Log.d(MainActivity.LOG_TAG, "file.getPath() = " + file.getPath());
            try (OutputStream out = new FileOutputStream(file)) {
                InputStream inputStream = getContext().getAssets().open(filename);
                byte[] buf = new byte[1024];
                int len;
                while ((len = inputStream.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } catch (Exception e) {
                Log.d(MainActivity.LOG_TAG, "Exception!!! = " + e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            button_details.setText(getResources().getString(R.string.download_button_status_4));

            // перед тем, как передать файл в интент, установил программу как контент провайдер в манифесте
            Uri uri = FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".provider", file);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/Minecraft");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        }
    }

    class MyTask extends AsyncTask<Void, Void, Void> {
        public MyTask() {
            super();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // этот код парсит json
            // не стал заморачиватсья на счет обработки ошибок, так как здесь или работает, или нет, так как все в памяти, а не в сети
            // сдел в AsyncTask потому что это теоретически увеличит отзывчивость интерфейса
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
                filename = jsonObject_temp.getString("file");
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

            // после того, как загрузит из json, выведет в UI
            // наверное нужно было поставить на слой заглушку на время загрузки, но загрузка почти мгновенная
            textView_title_details.setText(title_details);
            textView_description_details.setText(description_details);
            InputStream inputStream = null;
            try {
                inputStream = getActivity().getAssets().open(image_name);
                Drawable d = Drawable.createFromStream(inputStream, null);
                imageView_details.setImageDrawable(d);
                imageView_details.setScaleType(ImageView.ScaleType.FIT_XY);
            } catch (IOException e) {
                Log.d(MainActivity.LOG_TAG, e + "");
            } finally {
                try {
                    if (inputStream != null)
                        inputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            // этот код проверяет, есть ли в памяти файл карты, если он есть, значит карту уже устанавливали и на кнопке будет написано, что она установлена
            // возможна ситуация, когда пользователь щелкнул не открывать файл, но не нашел способа, как на 100 процентов быть уверенным, что карта установлена или не установлена
            if (new File(path, filename).exists()) {
                Log.d(MainActivity.LOG_TAG, "temp.exists() = true");
                button_details.setText(getResources().getString(R.string.download_button_status_4));
            } else {
                Log.d(MainActivity.LOG_TAG, "temp.exists() = false");
                button_details.setText(getResources().getString(R.string.download_button_status_3));
            }
            button_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // если игра не устанловлена, то отправит на Play Market
                    if(isInstalled) {
                        LoadToMemory loadToMemory = new LoadToMemory();
                        loadToMemory.execute();
                    } else {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.mojang.minecraftpe")));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mojang.minecraftpe")));
                        }

                    }
                }
            });
        }
    }
}
