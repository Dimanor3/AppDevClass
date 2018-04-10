package com.example.ryanhaines.inclass10;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageActivity extends AppCompatActivity {

	TextView title;
	ImageButton homeBtn;
	EditText userMessageText;
	ImageButton sendBtn;
	private final OkHttpClient client = new OkHttpClient ();
	ArrayList<MessageResponse> messages = new ArrayList<> ();
	ProgressBar progressBar;
	EditText messageText;
	public int messageID;

	public MessageAdapter messageAdapter;
	final Handler handler = new Handler ();

	int i = 0;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_message);
		SharedPreferences sharedPreferences = getSharedPreferences (
				getString (R.string.preference_file_key), Context.MODE_PRIVATE);
		final String token = sharedPreferences.getString ("token", null);
		messageID = sharedPreferences.getInt ("messageID", 0);
		Log.d ("Test", Integer.toString (messageID));
		progressBar = (ProgressBar) findViewById (R.id.progressBar);
		//progressBar.setVisibility(View.VISIBLE);
		getMessages (messageID, token);

		ListView listview = (ListView) findViewById (R.id.listView2);
		messageAdapter = new MessageAdapter (this, R.layout.message_adapter, messages);
		listview.setAdapter (messageAdapter);
		messageText = (EditText) findViewById (R.id.userMessageText);
		homeBtn = (ImageButton) findViewById (R.id.homeBtn);
		userMessageText = (EditText) findViewById (R.id.userMessageText);
		title = (TextView) findViewById (R.id.threadTitleText);
		sendBtn = (ImageButton) findViewById (R.id.sendBtn);
		String titleStr = getIntent ().getStringExtra ("title");
		final int threadID = getIntent ().getIntExtra ("thread_id", 0);

		title.setText (titleStr);
		setTitle ("ChatRoom");

		homeBtn.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View view) {
				deleteMessageID ();
				Intent intent = new Intent (MessageActivity.this, ThreadsActivity.class);
				startActivity (intent);
				finish ();
			}
		});

		sendBtn.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View view) {
				String message = messageText.getText ().toString ();
				addMessage (token, message, threadID, messageID);
				messageText.setText ("");
				messageAdapter.clear ();
				progressBar.setVisibility (View.VISIBLE);
				Log.d ("test", "Button pressed");
				handler.postDelayed (new Runnable () {
					@Override
					public void run () {
						messageAdapter.notifyDataSetChanged ();
						progressBar.setVisibility (View.INVISIBLE);
					}
				}, 15000);

			}
		});
	}

	@Override
	protected void onResume () {
		super.onResume ();

		if (i == 0) {
			i = 1;

			return;
		}

		deleteMessageID ();
		Intent intent = new Intent (MessageActivity.this, ThreadsActivity.class);
		startActivity (intent);
		finish ();
	}

	@Override
	public void onBackPressed () {
		super.onBackPressed ();

		deleteMessageID ();
		Intent intent = new Intent (MessageActivity.this, ThreadsActivity.class);
		startActivity (intent);
		finish ();
	}

	public void getMessages (final int messageID, final String token) {
		String strMessageID = String.valueOf (messageID);

		getActivity ().runOnUiThread (new Runnable () {
			@Override
			public void run () {
				//messageAdapter.clear();
				progressBar.setVisibility (View.VISIBLE);
			}
		});

		Request request = new Request.Builder ()
				.url ("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/messages/" + strMessageID)
				.header ("Authorization", "Bearer " + token)
				.build ();

		client.newCall (request).enqueue (new Callback () {
			@Override
			public void onFailure (Call call, IOException e) {
				Log.d ("messageActivityDemo", "Failure");
			}

			@Override
			public void onResponse (Call call, Response response) throws IOException {
				Log.d ("messageActivityDemo", "Response Made");
				String str = response.body ().string ();
				Log.d ("messageActivityDemo", str);
				try {
					JSONObject jsonMessage = new JSONObject (str);
					JSONArray messageArray = jsonMessage.getJSONArray ("messages");
					for (int i = 0; i < messageArray.length (); i++) {
						JSONObject message = (JSONObject) messageArray.get (i);
						messages.add (new MessageResponse (
								message.getString ("user_fname"),
								message.getString ("user_lname"),
								message.getString ("message"),
								message.getInt ("id"),
								message.getInt ("user_id"),
								message.getString ("created_at")
						));

					}

					getActivity ().runOnUiThread (new Runnable () {
						@Override
						public void run () {
							if (messages.size () == 0) {
								Toast.makeText (MessageActivity.this, "No Messages", Toast.LENGTH_LONG).show ();
								progressBar.setVisibility (View.INVISIBLE);
							} else {
								messageAdapter.notifyDataSetChanged ();
								progressBar.setVisibility (View.INVISIBLE);
								Log.d ("test", "Hey");
							}
						}
					});
				} catch (JSONException e) {
					e.printStackTrace ();
				}

			}
		});
	}

	public Activity getActivity () {
		return MessageActivity.this;
	}

	public void addMessage (final String token, String message, int thread_id, final int messageID) {
		getActivity ().runOnUiThread (new Runnable () {
			@Override
			public void run () {
				messageAdapter.clear ();
				progressBar.setVisibility (View.VISIBLE);
			}
		});

		RequestBody formBody = new FormBody.Builder ()
				.add ("message", message)
				.add ("thread_id", String.valueOf (thread_id))
				.build ();

		Request request = new Request.Builder ()
				.url ("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/message/add")
				.header ("Authorization", "Bearer " + token)
				.post (formBody)
				.build ();

		client.newCall (request).enqueue (new Callback () {
			@Override
			public void onFailure (Call call, IOException e) {
				Log.d ("addMessage", "Failure");
			}

			@Override
			public void onResponse (Call call, Response response) throws IOException {
				Log.d ("addMessage", "Response Made");
				String str = response.body ().string ();
				Log.d ("addMessage", str);
				getMessages (messageID, token);
			}
		});
	}

	public void deleteMessageID () {
		SharedPreferences sharedPreferences = getSharedPreferences (
				getString (R.string.preference_file_key), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit ();
		editor.putInt ("messageID", 0);
		editor.commit ();
	}

	public void deleteMessage (final String token, int selected_id) {


		getActivity ().runOnUiThread (new Runnable () {
			@Override
			public void run () {
				messageAdapter.clear ();
				progressBar.setVisibility (View.VISIBLE);
			}
		});

		Request request = new Request.Builder ()
				.url ("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/message/delete/" + selected_id)
				.header ("Authorization", "Bearer " + token)
				.get ()
				.build ();

		client.newCall (request).enqueue (new Callback () {
			@Override
			public void onFailure (Call call, IOException e) {
				Log.d ("messageDelete", "Failure");
			}

			@Override
			public void onResponse (Call call, Response response) throws IOException {
				Log.d ("messageDelete", "Response Made");
				String str = response.body ().string ();
				Log.d ("messageDelete", str);
				getMessages (messageID, token);

				getActivity ().runOnUiThread (new Runnable () {
					@Override
					public void run () {
						messageAdapter.notifyDataSetChanged ();
						progressBar.setVisibility (View.INVISIBLE);
					}
				});

			}
		});
	}

}
