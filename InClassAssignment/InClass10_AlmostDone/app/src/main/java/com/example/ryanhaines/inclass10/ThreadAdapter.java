package com.example.ryanhaines.inclass10;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanhaines on 4/3/18.
 */

public class ThreadAdapter extends ArrayAdapter<ThreadResponse>{
    private ThreadAdapter threadAdapter = null;

    public ThreadAdapter(@NonNull Context context, int resource, @NonNull List<ThreadResponse> objects) {
        super(context, resource, objects);
        this.threadAdapter = this;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ThreadResponse threadResponse = getItem(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.thread_adapter, parent, false);

        TextView textViewTitle = (TextView) convertView.findViewById(R.id.titleView);
        textViewTitle.setText(threadResponse.title);
        ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.deleteBtn);
        imageButton.setVisibility(View.VISIBLE);
        SharedPreferences preferences = getContext().getSharedPreferences("myprefs", 0);
        int userID = preferences.getInt("ID", 0);
        final String token = preferences.getString("token", "NOTHING");

        if(userID == threadResponse.getUser_id()){
            imageButton.setVisibility(View.VISIBLE);
        }else{
            imageButton.setVisibility(View.INVISIBLE);
        }


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getContext() instanceof ThreadsActivity){
                    ((ThreadsActivity)getContext()).deleteThreadList(token, threadResponse.getId());
                }
            }
        });

        return convertView;
    }
}
