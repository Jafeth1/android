package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private ProgressBar progressBar;
    private Bitmap currentCatImage = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);

        new CatImages().execute();
    }

    private class CatImages extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            while (true) {
                try {
                    JSONObject jsonObject = getJSONFromUrl("https://cataas.com/cat?json=true");
                    String id = jsonObject.getString("id");
                    String url = jsonObject.getString("url");

                    File file = new File(getFilesDir(), id + ".jpg");

                    if (file.exists()) {
                        currentCatImage = BitmapFactory.decodeFile(file.getAbsolutePath());
                    } else {
                        currentCatImage = downloadImage(url, file);
                    }

                    publishProgress(-1);

                    for (int i = 0; i < 100; i++) {
                        try {
                            publishProgress(i);
                            Thread.sleep(30);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values[0] == -1 && currentCatImage != null) {
                imageView.setImageBitmap(currentCatImage);
            } else {
                progressBar.setProgress(values[0]);
            }
        }

        private JSONObject getJSONFromUrl(String urlString) {
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();

                int read;
                StringBuilder builder = new StringBuilder();
                while ((read = inputStream.read()) != -1) {
                    builder.append((char) read);
                }

                connection.disconnect();
                inputStream.close();

                return new JSONObject(builder.toString());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private Bitmap downloadImage(String urlString, File file) {
            try {
                URL url = new URL("https://cataas.com" + urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();

                Bitmap image = BitmapFactory.decodeStream(inputStream);

                FileOutputStream outputStream = new FileOutputStream(file);
                image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

                outputStream.flush();
                outputStream.close();
                inputStream.close();
                connection.disconnect();

                return image;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}