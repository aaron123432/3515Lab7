package edu.temple.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;


import java.util.ArrayList;
import java.util.HashMap;

public class BookCase extends AppCompatActivity implements BookListFragment.ButtonClickedInterface {

    BookListFragment bookListFragment;
    BookDetailsFragment bookDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isPad(this)) {
            setContentView(R.layout.activity_main);

        }else if(isPad(this)){
            setContentView(R.layout.activity_main_tablet);
        }

        Resources res = this.getResources();


        String[] bookTitle = res.getStringArray(R.array.book_title);
        String[] author = res.getStringArray(R.array.author);
        HashMap<String, String>[] hashMap = new HashMap[bookTitle.length];
        ArrayList<HashMap> array = new ArrayList<HashMap>();

        for (int i = 0; i < bookTitle.length; i++) {
            hashMap[i] = new HashMap<String, String>();
        }


        for (int i = 0; i < bookTitle.length; i++) {
            hashMap[i].put(bookTitle[i], author[i]);
            array.add(hashMap[i]);
        }


        if (bookListFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(bookListFragment)
                    .commit();
        }

        bookListFragment = BookListFragment.newInstance(array);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.book_list, bookListFragment)
                .commit();

    }

    @Override
    public void buttonClicked(String book, String author) {


        if(!isPad(this)) {

            if (bookDetailsFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(bookDetailsFragment)
                        .commit();
            }

            Configuration mConfiguration = this.getResources().getConfiguration();
            int ori = mConfiguration.orientation;
            if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
                BookDetailsFragment bookDetailsFragment = BookDetailsFragment.newInstance(book, author, 1);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.book_list2, bookDetailsFragment)
                        .addToBackStack(null)
                        .commit();
                bookListFragment.bookDetailsFragment = bookDetailsFragment;
                bookListFragment.isLand = 1;

                this.bookDetailsFragment = bookDetailsFragment;
            } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
                BookDetailsFragment bookDetailsFragment = BookDetailsFragment.newInstance(book, author, 0);
                bookListFragment.isLand = 0;
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.book_list, bookDetailsFragment)
                        .addToBackStack(null)
                        .commit();

                bookListFragment.bookDetailsFragment = bookDetailsFragment;
                this.bookDetailsFragment = bookDetailsFragment;


            }


        }else if(isPad(this)) {

            if (bookDetailsFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(bookDetailsFragment)
                        .commit();
            }

            BookDetailsFragment bookDetailsFragment = BookDetailsFragment.newInstance(book, author, 1);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.book_list2, bookDetailsFragment)
                    .addToBackStack(null)
                    .commit();
            bookListFragment.bookDetailsFragment = bookDetailsFragment;
            bookListFragment.isLand = 1;

            this.bookDetailsFragment = bookDetailsFragment;

        }


    }
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
