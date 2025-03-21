package com.example.android_news_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ArrayList<News> news = new ArrayList<News>();
    private NewsAdapter adapter;
    private TextView preparing_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        preparing_text = findViewById(R.id.preparing_text);

        RecyclerView recyclerView = findViewById(R.id.list);








        //Ф-я получения новостей

        //Для примера, лучше в код не вставлять (в .env), но для олимпиады тут будет. Если не работает (запросы кончились), замените на свой ключ из ЛК
        String api_key = "17939698943f355cf439b0df8068eb83";

        String url = "https://gnews.io/api/v4/search?q=business&lang=en&country=us&max=10&apikey=" + api_key;

        new GetNewsFromURL().execute(url);

        NewsAdapter.OnNewsClickListener stateClickListener = new NewsAdapter.OnNewsClickListener() {
            @Override
            public void onNewsClick(News news, int position) {

                BrowserActivity browserActivity = new BrowserActivity(news.getUrl());
                Intent intent = new Intent(MainActivity.this, browserActivity.getClass());
                startActivity(intent);
            }
        };


        adapter = new NewsAdapter(this, news, stateClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));

        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
    }



   private class GetNewsFromURL extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            preparing_text.setText(R.string.preparing_text);
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;


            try {
                // Создаем объект URL, получая строку url из аргументов (первый)
                URL url = new URL(strings[0]);

                // Открываем  соединение
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // Получаем поток и формируем reader
                InputStream inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                // Строка-буфер, куда будем считывать и line (считывающий построчно в цикле)
                StringBuffer buffer = new StringBuffer();
                String line = "";

                while((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }
                return buffer.toString();

            } catch (IOException e) {
                Log.e("MainActivity", String.valueOf(e));
            } finally {
                if (connection != null)
                    connection.disconnect();


                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    Log.e("MainActivity", String.valueOf(e));
                }


            }


            return "";
        }


       @Override
       protected void onPostExecute(String s) {

           if (Objects.equals(s, "")){
               preparing_text.setText(R.string.empty_result);
           } else {
               try {
                   // Преобразуем результат в Json object
                   JSONObject jsonObject = new JSONObject(s);

                   JSONArray articles = jsonObject.getJSONArray("articles");

                   for (int i = 0; i < articles.length(); i++){
                       // Получаем данные из API
                       String title = articles.getJSONObject(i).getString("title");
                       String url = articles.getJSONObject(i).getString("url");
                       String image_url = articles.getJSONObject(i).getString("image");

                       news.add(new News(title, url, image_url));



                   }


                   //Обновить данные в адаптере
                   adapter.notifyDataSetChanged();

                   preparing_text.setText("");
                   //result_info.setText(s);
               } catch (JSONException | IllegalFormatException e) {
                   Log.e("MainActivity", String.valueOf(e));
               }
           }



       }


    }






}