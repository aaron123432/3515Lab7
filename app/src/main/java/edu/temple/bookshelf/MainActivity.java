package com.example.bookshelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.android.volley.Response.*;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookSelectedInterface {

    FragmentManager fm;
    boolean twoPane;
    BookDetailsFragment bookDetailsFragment;

    EditText searchbar;
    Button search;
    String url;
    RequestQueue requestQueue;
    ArrayList<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);
        twoPane = findViewById(R.id.container2) != null;

        fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.container1, BookListFragment.newInstance(books))
                .commit();

        if (twoPane) {
            bookDetailsFragment = new BookDetailsFragment();
            fm.beginTransaction()
                    .replace(R.id.container2, bookDetailsFragment)
                    .commit();

        }
    }

    private ArrayList<Book> getTestBooks() {
        url = "https://kamorris.com/lab/abp/booksearch.php";
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                    for(int i = 0; i < response.length(); i++){
                        try {
                            JSONObject jb = response.getJSONObject(i);
                            int id = jb.getInt("book_id");
                            String title = jb.getString("title");
                            String author = jb.getString("author");
                            String cover_url = jb.getString("cover_url");
                            Book book = new Book(id, title, author,cover_url);
                            books.add(book);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                //Do nothing
                Toast.makeText(MainActivity.this, "Something goes wrong", Toast.LENGTH_LONG);
            }
        });

        requestQueue.add(jsonArrayRequest);
        return books;
    }


    @Override
    public void bookSelected(int index) {

        if (twoPane)
            /*
            Display selected book using previously attached fragment
             */
            bookDetailsFragment.displayBook(getTestBooks().get(index));
        else {
            /*
            Display book using new fragment
             */
            fm.beginTransaction()
                    .replace(R.id.container1, BookDetailsFragment.newInstance(getTestBooks().get(index)))
                    // Transaction is reversible
                    .addToBackStack(null)
                    .commit();
        }
    }
}
