package edu.temple.bookshelf;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookDetailsFragment extends Fragment {


    View layout;
    int isLand;
//    BookDetailsFragment.Ending parentActivity;

    public static BookDetailsFragment newInstance (String colors, String  colorName,int isLand) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("book", colors);
        bundle.putString("author", colorName);
        bundle.putInt("land",isLand);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BookListFragment.ButtonClickedInterface) {

        } else {
            throw new RuntimeException("Please Implement the ButtonClickedListener Interface!!!!!!!");
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isLand = getArguments().getInt("land");

        layout = inflater.inflate(R.layout.book_details_fragment, container, false);
        ((TextView)layout.findViewById(R.id.textView3)).setText(getArguments().getString("book"));
        ((TextView)layout.findViewById(R.id.textView4)).setText(getArguments().getString("author"));

        return layout;
    }

    @Override
    public void onStop() {
        super.onStop();
//        parentActivity.ending();
    }
//    interface Ending {
//        public void ending();
//    }
}
