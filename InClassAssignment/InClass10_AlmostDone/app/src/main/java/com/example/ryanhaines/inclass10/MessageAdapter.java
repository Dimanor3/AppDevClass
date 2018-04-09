package com.example.ryanhaines.inclass10;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ryanhaines on 4/3/18.
 */

public class MessageAdapter extends ArrayAdapter<MessageResponse> {

	public MessageAdapter (@NonNull Context context, int resource, @NonNull List<MessageResponse> objects) {
		super (context, resource, objects);
	}

	@NonNull
	@Override
	public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		final MessageResponse messageResponse = getItem (position);
		convertView = LayoutInflater.from (getContext ()).inflate (R.layout.message_adapter, parent, false);

		TextView messageText = (TextView) convertView.findViewById (R.id.messageText);
		messageText.setText (messageResponse.message);
		TextView timeSinceText = (TextView) convertView.findViewById (R.id.timeSinceText);

		TextView senderText = (TextView) convertView.findViewById (R.id.senderNameText);
		senderText.setText (messageResponse.user_fname + " " + messageResponse.user_lname);
		String timeStr = messageResponse.getCreated_at ();
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ImageButton imageButton = convertView.findViewById (R.id.trashBtn);

		SharedPreferences preferences = getContext ().getSharedPreferences ("myprefs", 0);
		int userID = preferences.getInt ("ID", 0);
		final String token = preferences.getString ("token", "NOTHING");

		if (userID == messageResponse.getUser_id ()) {
			imageButton.setVisibility (View.VISIBLE);
			Log.d ("help", "button visible");
		} else {
			imageButton.setVisibility (View.INVISIBLE);
			Log.d ("help", "button invisible");
		}
		imageButton.setClickable (true);
		imageButton.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View view) {
				if (getContext () instanceof MessageActivity) {
					((MessageActivity) getContext ()).deleteMessage (token, messageResponse.getId ());
					Log.d ("test", "button pressed");
				}
			}
		});
		SimpleDateFormat format = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss", Locale.US);
		//Log.d("hello","Hello");

		Log.d ("gfdgdf", timeStr);

		try {
			Date date = format.parse (timeStr);
			Date currentDate = new Date ();
			String t = "";
			long diff = date.getTime () - currentDate.getTime ();
			int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
			int hours = (int) (diff / (1000 * 60 * 60));
			int minutes = (int) (diff / (1000 * 60));
			int seconds = (int) (diff / (1000));
			Log.d ("hello", String.valueOf (seconds));
			if (numOfDays > 0) {
				t = String.valueOf (numOfDays) + " days from now";
			} else if (hours > 0) {
				t = String.valueOf (hours) + " hours from now";
			} else if (minutes > 0) {
				t = String.valueOf (minutes) + " minutes from now";
			} else if (seconds > 0) {
				t = String.valueOf (seconds) + " seconds from now";
			}

			timeSinceText.setText (t);
		} catch (ParseException e) {
			e.printStackTrace ();
			Log.d ("jkdjkdf", "I FAILED!");
		}

		timeSinceText.setText (timeStr);

		return convertView;
	}

}
