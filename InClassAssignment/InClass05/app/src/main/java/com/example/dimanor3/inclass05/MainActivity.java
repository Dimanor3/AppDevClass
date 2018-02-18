/*
* Assignment#: InClass05
* File Name: MainActivity.java
* Full Name: Bijan Razavi, Kushal Tiwari
* */

package com.example.dimanor3.inclass05;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    LinkedList<String> data, imageLinks;

    ImageButton previous, forward;

    int curImg = 0;

    RequestParams params = new RequestParams ();

    TextView searchKeyword, imageOf;
    String newText = "", imgOfTxt = "";

    ImageView imageView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        searchKeyword = (TextView) findViewById (R.id.searchKeywordTextView);
        imageView = (ImageView) findViewById (R.id.imageView);
        imageLinks = new LinkedList<> ();
        previous = (ImageButton) findViewById (R.id.previousButton);
        forward = (ImageButton) findViewById (R.id.forwardButton);
        imageOf = (TextView) findViewById (R.id.imageOf);
    }

    public void go (View v) {
        if (isConnected ()) {
            imageLinks.clear ();

            curImg = 0;

            new GetDataKeywordAsync (MainActivity.this).execute ("http://dev.theappsdr.com/apis/photos/keywords.php");
        } else {
            Toast.makeText (MainActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show ();
        }
    }

    public void previous (View v) {
        if (curImg > 0) {
            curImg--;

            new GetDataPicLinkAsync (imageView, MainActivity.this).execute (imageLinks.get (curImg));
        } else {
            curImg = imageLinks.size () - 1;

            new GetDataPicLinkAsync (imageView, MainActivity.this).execute (imageLinks.get (curImg));
        }

        imgOfTxt = Integer.toString (curImg + 1) + " of " + imageLinks.size ();

        imageOf.setText (imgOfTxt);
    }

    public void next (View v) {
        if (curImg < imageLinks.size () - 1) {
            curImg++;

            new GetDataPicLinkAsync (imageView, MainActivity.this).execute (imageLinks.get (curImg));
        } else {
            curImg = 0;

            new GetDataPicLinkAsync (imageView, MainActivity.this).execute (imageLinks.get (curImg));
        }

        imgOfTxt = Integer.toString (curImg + 1) + " of " + imageLinks.size ();

        imageOf.setText (imgOfTxt);
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

        private ProgressDialog dialog;

        public GetDataPicLinkAsync (ImageView imageView, MainActivity activity) {
			this.imageView = imageView;
            dialog = new ProgressDialog (activity);
        }

        @Override
        protected void onPreExecute () {
            dialog.setMessage ("Loading Photo ...");
            dialog.show ();
        }

		@Override
		protected Void doInBackground (String... params) {
			HttpURLConnection connection = null;

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

            if (dialog.isShowing ()) {
                dialog.dismiss ();
            }
		}
    }

    private class GetDataKeywordAsync extends AsyncTask <String, Void, LinkedList<String>> {
        private ProgressDialog dialog;

        BufferedReader reader = null;
        
        LinkedList<String> linkedList = new LinkedList<String> ();

        HttpURLConnection connection = null;

        public GetDataKeywordAsync (MainActivity activity) {
            dialog = new ProgressDialog (activity);
        }

        @Override
        protected void onPreExecute () {
            dialog.setMessage ("Loading Dictionary ...");
            dialog.show ();
        }

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
                Toast.makeText (MainActivity.this, "No Images Found!", Toast.LENGTH_SHORT).show ();
                Log.d ("demo", "null result");
            }

            if (dialog.isShowing ()) {
                dialog.dismiss ();
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
				for (String r: result) {
					imageLinks.add (r);
                    Log.d ("demo", r);
				}

				String temp = imageLinks.get (0);

                imgOfTxt = Integer.toString (curImg + 1) + " of " + imageLinks.size ();

                imageOf.setText (imgOfTxt);

                new GetDataPicLinkAsync (imageView, MainActivity.this).execute (imageLinks.get (0));

                if (imageLinks == null || imageLinks.size () <= 1) {
                    setClickable (false);
                    Log.d ("demo", "There are 0 or 1 images.");
                } else {
                    setClickable (true);
                }
			} else {
				String str = "Null Result";
			}
		}
	}

    private void setClickable (boolean set) {
        previous.setClickable (set);
        forward.setClickable (set);
    }
}
