package com.example.dimanor3.inclass05;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by Dimanor3 on 2/12/2018.
 */

public class RequestParams {
    private HashMap<String, String> params;

    public RequestParams () {
        params = new HashMap<String, String> ();
    }

    public void addParameter (String key, String value) {
        try {
            params.put (key, URLEncoder.encode (value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace ();
        }
    }
}
