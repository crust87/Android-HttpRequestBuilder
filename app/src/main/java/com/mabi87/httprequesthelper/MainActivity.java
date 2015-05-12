package com.mabi87.httprequesthelper;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mabi87.httprequestlib.HTTPRequestHelper;
import com.mabi87.httprequestlib.HTTPRequestTask;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private Button mButtonPost;
    private Button mButtonGet1;
    private Button mButtonGet2;
    private TextView mTextResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadGUI();
        bindEvent();
        init();
    }

    private void loadGUI() {
        setContentView(R.layout.activity_main);

        mButtonPost = (Button) findViewById(R.id.buttonPost);
        mButtonGet1 = (Button) findViewById(R.id.buttonGet1);
        mButtonGet2 = (Button) findViewById(R.id.buttonGet2);
        mTextResult = (TextView) findViewById(R.id.textResult);
    }

    private void bindEvent() {
        mButtonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HTTPRequestTask<Void, Void, JSONObject>() {
                    @Override
                    protected JSONObject doInBackgroundRequest(Void... params) throws IOException, JSONException {
                        ArrayList<NameValuePair> lNameValuePare = new ArrayList<NameValuePair>();
                        return HTTPRequestHelper.post("", lNameValuePare);
                    }
                }.execute();
            }
        });

        mButtonGet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HTTPRequestTask<Void, Void, JSONObject>() {
                    @Override
                    protected JSONObject doInBackgroundRequest(Void... params) throws IOException, JSONException {
                        ArrayList<NameValuePair> lNameValuePare = new ArrayList<NameValuePair>();
                        return HTTPRequestHelper.get("", lNameValuePare);
                    }
                }.execute();
            }
        });

        mButtonGet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HTTPRequestTask<Void, Void, JSONObject>() {
                    @Override
                    protected JSONObject doInBackgroundRequest(Void... params) throws IOException, JSONException {
                        return HTTPRequestHelper.get("");
                    }
                }.execute();
            }
        });
    }

    private void init() {

    }
}
