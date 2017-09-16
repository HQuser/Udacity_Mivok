package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by abdur on 9/14/17.
 */

public class WordAdapter extends ArrayAdapter<Word> {
    private static int backgroundColor;

    public WordAdapter(@NonNull Context context, @NonNull ArrayList<Word> objects, int backgroundColor) {
        super(context, 0, objects);

        WordAdapter.backgroundColor = ContextCompat.getColor(getContext(), backgroundColor);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        LinearLayout text_views = convertView.findViewById(R.id.text_views);
        text_views.setBackgroundColor(backgroundColor);

        Word currentWord = getItem(position);

        TextView defaultLanguageTextView = convertView.findViewById(R.id.default_language);
        defaultLanguageTextView.setText(currentWord.getdefaultTranslation());

        TextView miwokLanguageTextView = convertView.findViewById(R.id.mivok_language);
        miwokLanguageTextView.setText(currentWord.getmiwokTranslation());

        ImageView imageView = convertView.findViewById(R.id.image);

        if (currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getmmageRecourseId());
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }

        return convertView;
    }
}
