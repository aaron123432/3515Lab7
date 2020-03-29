package edu.temple.bookshelf;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.zip.Inflater;

import static java.security.AccessController.getContext;


public class BookAdapter extends BaseAdapter {

    String[] bookList;
    String[] author;
    private final int resourceId;
    Context context;
    BookAdapter(String[] bookList, String[] author, int resourceId, Context context) {
        this.bookList = bookList;
        this.author = author;
        this.resourceId = resourceId;
        this.context = context;
    }
    @Override
    public int getCount() {
        return bookList.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = LayoutInflater.from(context).inflate(resourceId, null);
        TextView bookName = view.findViewById(R.id.textView);
        TextView authorName =  view.findViewById(R.id.textView2);
        bookName.setText(bookList[position]);
        authorName.setText(author[position]);

        return view;
    }

}
