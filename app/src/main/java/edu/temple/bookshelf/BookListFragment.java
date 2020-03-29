package edu.temple.bookshelf;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BookListFragment extends Fragment {

    ButtonClickedInterface parentActivity;
    View layout;
    // ArrayList<HashMap> bookList;
    String[] bookList;
    String[] author;
    BookDetailsFragment bookDetailsFragment;
    int isLand;



    public static BookListFragment newInstance(ArrayList<HashMap> books) {
        BookListFragment fragment = new BookListFragment();

        String[] bookList2 = new String[books.size()];
        String[] author2 = new String[books.size()];

        int i = 0;
        for (HashMap<String, String> hashMap : books) {
            for (HashMap.Entry<String, String> entry : hashMap.entrySet()) {
                bookList2[i] = entry.getKey();
                author2[i] = entry.getValue();

            }
            i++;
        }


        Bundle bundle = new Bundle();
        bundle.putStringArray("list", bookList2);
        bundle.putStringArray("authorList", author2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null) {
            bookList = bundle.getStringArray("list");
            author = bundle.getStringArray("authorList");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ButtonClickedInterface) {
            parentActivity = (ButtonClickedInterface) context;
        } else {
            throw new RuntimeException("Please Implement the ButtonClickedListener Interface!!!!!!!");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_book_list, container, false);

        ListView listView = layout.findViewById(R.id.listview);
        listView.setAdapter(new BookAdapter(bookList, author, R.layout.book_iteam, this.getContext()));

        ((ListView) layout.findViewById(R.id.listview)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (bookDetailsFragment== null || !(bookDetailsFragment.isVisible())) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("book", bookList[position]);
                    bundle2.putString("author", author[position]);
                    parentActivity.buttonClicked(bookList[position], author[position]);

                }else if(isLand==1){
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("book", bookList[position]);
                    bundle2.putString("author", author[position]);
                    parentActivity.buttonClicked(bookList[position], author[position]);
                }
            }

        });

        return layout;
    }


    interface ButtonClickedInterface {
        public void buttonClicked(String book, String author);
    }
}
