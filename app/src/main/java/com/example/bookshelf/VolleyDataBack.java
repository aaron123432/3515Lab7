package com.example.bookshelf;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public  class VolleyDataBack {
     static Book[] book;

    public static void get(Context context, String url, final int option, final int index, final VolleyCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final ArrayList<Book> books2 = new ArrayList<Book>();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    book = new Book[response.length()];
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject js = response.getJSONObject(i);
                        int id = js.getInt("book_id");
                        String title = js.getString("title");
                        String author = js.getString("author");
                        String cover_url = js.getString("cover_url");
                        book[i] = new Book(id, title, author, cover_url);
                        books2.add(book[i]);
                    }
                    if(option==1) {
                        callback.showBookList(books2);
                    }else if(option ==2){

                        callback.BookChoose(books2, index);
                    }
                } catch (JSONException e) {


                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("recyclerveiw", "onErrorResponse:" + volleyError);
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    public interface VolleyCallback {
        void showBookList(ArrayList<Book> books1);
        void BookChoose(ArrayList<Book> books1, int index);
    }
}
