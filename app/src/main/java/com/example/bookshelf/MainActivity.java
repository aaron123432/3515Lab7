package com.example.bookshelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import com.android.volley.toolbox.Volley;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements BookListFragment.BookSelectedInterface {
    FragmentManager fm = getSupportFragmentManager();
    RequestQueue requestQueue;
    boolean twoPane;
    BookDetailsFragment bookDetailsFragment;
    BookListFragment bookListFragment;
    Book[] book;
    String input = "";
    VolleyDataBack volleyDataBack;
    int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        volleyDataBack = new VolleyDataBack();
        Configuration mConfiguration = this.getResources().getConfiguration();
        int ori = mConfiguration.orientation;
        if (savedInstanceState != null) {

            if (savedInstanceState.containsKey("DATAB")) {
                this.bookListFragment = ((ArrayList<BookListFragment>) (savedInstanceState.getSerializable("DATAB"))).get(0);
            }
            if (savedInstanceState.containsKey("DETAIL")) {
                this.bookDetailsFragment = ((ArrayList<BookDetailsFragment>) (savedInstanceState.getSerializable("DETAIL"))).get(0);
            }
            if (savedInstanceState.containsKey("INPUT")) {
                this.input = savedInstanceState.getString("INPUT");
            }

        }

        if (ori == mConfiguration.ORIENTATION_LANDSCAPE ||isPad(this)) {
            //Toast.makeText(this, "LAND", Toast.LENGTH_SHORT).show();
            twoPane = true;

        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            //Toast.makeText(this, "PORT", Toast.LENGTH_SHORT).show();
            twoPane = false;

        }

        requestQueue = Volley.newRequestQueue(this);
        if (!(bookDetailsFragment == null && bookListFragment == null)) {
            if (twoPane) {
                getTestBooks(1, input);
                if (bookDetailsFragment != null) {
                    fm.beginTransaction()
                            .replace(R.id.container2, bookDetailsFragment)
                            .addToBackStack(null)
                            .commit();
                }
            } else {

                if (bookDetailsFragment != null) {

                    fm.beginTransaction()
                            .replace(R.id.container2, bookDetailsFragment)
                            .addToBackStack(null)
                            .hide(bookListFragment)
                            .commit();
                } else {
                    getTestBooks(1, input);
                }

            }
        }


        ((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input = ((TextView) findViewById(R.id.editText)).getText().toString();
                getTestBooks(1, input);
                /*
                If we have two containers available, load a single instance
                of BookDetailsFragment to display all selected books
                 */
                if (twoPane) {
                    if(bookDetailsFragment!=null) {
                        //bookDetailsFragment = new BookDetailsFragment();
                        fm.beginTransaction()
                                .replace(R.id.container2, bookDetailsFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                } else {
                    if(bookDetailsFragment!=null) {
                        fm.beginTransaction()
                                .remove(bookDetailsFragment)
                                .commit();
                        bookDetailsFragment= null;
                    }
                }
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (bookDetailsFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(bookDetailsFragment)
                        .commit();
                bookDetailsFragment = null;
               if(!twoPane){
                   getTestBooks(1, input);
               }

                 if (!bookListFragment.isVisible()) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .show(bookListFragment)
                            .commit();

                }
            }


        }

        return true;

    }


    @Override
    public void onPause() {
        super.onPause();
        if (bookListFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(bookListFragment)
                    .commit();

           // bookListFragment=null;

        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        ArrayList<BookListFragment> save3 = new ArrayList<BookListFragment>();
        if (bookListFragment != null) {
            save3.add(bookListFragment);
            outState.putSerializable("DATAB", save3);
        }
        ArrayList<BookDetailsFragment> save = new ArrayList<BookDetailsFragment>();
        if (bookDetailsFragment != null) {
            save.add(bookDetailsFragment);
            outState.putSerializable("DETAIL", save);
        }
        outState.putString("INPUT", input);

    }

    private void getTestBooks(int option, String input) {
        final ArrayList<Book> books = new ArrayList<Book>();


        final String url = "https://kamorris.com/lab/abp/booksearch.php?search=" + input;

        volleyDataBack.get(this, url, option, index, new VolleyDataBack.VolleyCallback() {
            @Override
            public void showBookList(ArrayList<Book> books1) {
                bookListFragment = BookListFragment.newInstance(books1);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container1, bookListFragment)
                        .commit();

            }

            @Override
            public void BookChoose(ArrayList<Book> books1, int index) {
                if (twoPane) {

                    if (bookDetailsFragment != null) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .remove(bookDetailsFragment)
                                .commit();
                    }

                    bookDetailsFragment = BookDetailsFragment.newInstance(books1, index);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container2, bookDetailsFragment)
                            .addToBackStack(null)
                            .commit();
                } else {

                    if (bookDetailsFragment != null) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .remove(bookDetailsFragment)
                                .commit();
                    }

                    bookDetailsFragment = BookDetailsFragment.newInstance(books1, index);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container2, bookDetailsFragment)
                            .addToBackStack(null)
                            .hide(bookListFragment)
                            .commit();
                }
            }

        });


    }


    @Override
    public void bookSelected(int index) {
        this.index = index;
        getTestBooks(2, input);

    }

    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


}



