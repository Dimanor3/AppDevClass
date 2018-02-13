/*
* Assignment#: InClass05
* File Name: MainActivity.java
* Full Name: Bijan Razavi, Kushal Tiwari
* */

package com.example.dimanor3.inclass05;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    LinkedList<String> data, imageLinks;

    RequestParams params = new RequestParams ();

    TextView searchKeyword;
    String newText = "";

    ImageView imageView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        searchKeyword = (TextView) findViewById (R.id.searchKeywordTextView);
        imageView = (ImageView) findViewById (R.id.imageView);
    }

    public void go (View v) {
        if (isConnected ()) {
            new GetDataKeywordAsync ().execute ("http://dev.theappsdr.com/apis/photos/keywords.php");
        } else {
            Toast.makeText (MainActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show ();
        }
    }

    public void previous (View v) {

    }

    public void next (View v) {

    }

    public void handleData (LinkedList<String> data) {
        this.data = data;
        final CharSequence[] charSequence = data.toArray (new CharSequence[data.size ()]);

        AlertDialog.Builder builder = new AlertDialog.Builder (this);

        builder.setTitle ("Choose a Keyword")
            .setItems (charSequence, new DialogInterface.OnClickListener () {
                @Override
                public void onClick (DialogInterface dialogInterface, int which) {
                    newText = charSequence[which].toString ();

                    searchKeyword.setText (newText);

                    params.addParameter ("keyword", newText);

                    new GetDataParamsUsingGetAsync (params).execute ("http://dev.theappsdr.com/apis/photos/index.php");
                }
            });

        final AlertDialog alertDialog = builder.create ();

        alertDialog.show ();
    }

    private boolean isConnected () {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo ();

        return !(networkInfo == null || !networkInfo.isConnected () ||
				(networkInfo.getType () != ConnectivityManager.TYPE_WIFI
						&& networkInfo.getType () != ConnectivityManager.TYPE_MOBILE));
    }

    private class GetDataPicLinkAsync extends AsyncTask <String, Void, Void> {
		ImageView imageView;

		Bitmap bitmap = null;

		public GetDataPicLinkAsync (ImageView imageView) {
			this.imageView = imageView;
		}

		@Override
		protected Void doInBackground (String... params) {
			HttpURLConnection connection = null;

			// Bitmap image = null;
			bitmap = null;

			try {
				URL url = new URL (params[0]);
				connection = (HttpURLConnection) url.openConnection ();

				connection.connect ();

				if (connection.getResponseCode () == HttpURLConnection.HTTP_OK) {
					bitmap = BitmapFactory.decodeStream (connection.getInputStream ());
				}
			} catch (IOException e) {
				e.printStackTrace ();
			} finally {
				if (connection != null) {
					connection.disconnect ();
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute (Void aVoid) {
			if (bitmap != null && imageView != null) {
				imageView.setImageBitmap (bitmap);
			}
		}
    }

    private class GetDataKeywordAsync extends AsyncTask <String, Void, LinkedList<String>> {
        BufferedReader reader = null;
        
        LinkedList<String> linkedList = new LinkedList<String> ();

        HttpURLConnection connection = null;

        @Override
        protected LinkedList<String> doInBackground (String... params) {
            try {
                URL url = new URL (params[0]);
                connection = (HttpURLConnection) url.openConnection ();

                reader = new BufferedReader (new InputStreamReader (connection.getInputStream ()));

                String line = "";

                while ((line = reader.readLine ()) != null) {
                    linkedList.addAll (Arrays.asList (line.split (";")));
                }
            } catch (IOException e) {
                e.printStackTrace ();
            } finally {
                if (connection != null) {
                    connection.disconnect ();
                }

                if (reader != null) {
                    try {
                        reader.close ();
                    } catch (IOException e) {
                        e.printStackTrace ();
                    }
                }
            }

            return linkedList;
        }

        @Override
        protected void onPostExecute (LinkedList<String> result) {
            if (result != null) {
                handleData (result);
            } else {
                Log.d ("demo", "null result");
            }
        }
    }

	private class GetDataParamsUsingGetAsync extends AsyncTask <String, Void, LinkedList<String>> {
		RequestParams mParams;

		public GetDataParamsUsingGetAsync (RequestParams params) {
			mParams = params;
		}

		@Override
		protected LinkedList<String> doInBackground (String... params) {
			LinkedList<String> result = new LinkedList<> ();

			HttpURLConnection connection = null;

			BufferedReader reader = null;

			try {
				URL url = new URL (mParams.getEncodedUrl (params[0]));
				connection = (HttpURLConnection) url.openConnection ();

				connection.connect ();

				if (connection.getResponseCode () == HttpURLConnection.HTTP_OK) {
					reader = new BufferedReader (new InputStreamReader (connection.getInputStream ()));

					String line = "";

					while ((line = reader.readLine ()) != null) {
						result.addAll (Arrays.asList (line.split ("\r\n")));
					}
				}
			} catch (IOException e) {
				e.printStackTrace ();
			} finally {
				if (connection != null) {
					connection.disconnect ();
				}

				if (reader != null) {
					try {
						reader.close ();
					} catch (IOException e) {
						e.printStackTrace ();
					}
				}
			}

			return result;
		}

		@Override
		protected void onPostExecute (LinkedList<String> result) {
			if (result != null) {
				imageLinks.addAll (result);

				for (String r: imageLinks) {
					Log.d ("demo", r);
				}

				//new GetDataPicLinkAsync (imageView).execute (imageLinks.get (0));
			} else {
				String str = "Null Result";
			}
		}
	}
}
