package com.example.bookshelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView view;
    private BookShelf adapter;
    private ArrayList<Books> list;
    private boolean isPhone, isLanscape, isTablet;
    private String key = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Resources res =getResources();
        isPhone = getResources().getBoolean(R.bool.isPhone);
        isLanscape = getResources().getBoolean(R.bool.isLanscape);
        isTablet = getResources().getBoolean(R.bool.isTablet);
        view = (ListView)findViewById(R.id.listView);
        list = new ArrayList<>();
        for(int i = 1; i < 11; i++){
            list.add(new Books("book" + i, "author" + i));
        }

        adapter = new BookShelf(getApplicationContext(), list);
        view.setAdapter(adapter);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isPhone){
                    BlankFragment fragment = new BlankFragment();
                    Books books = list.get(position);
                    String value = books.getBook() + books.getAuthor();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence(key, value);
                    fragment.setArguments(bundle);
                    transaction.add(R.id.fragment, fragment).addToBackStack(null).commit();
                }
            }
        });
    }
}
