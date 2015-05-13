/*
 * HttpRequestLib
 * https://github.com/mabi87/Android-HttpRequestHelper
 *
 * Mabi
 * crust87@gmail.com
 * last modify 2015-05-12
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mabi87.httprequesthelper;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mabi87.httprequestlib.HTTPRequestException;
import com.mabi87.httprequestlib.HTTPRequestHelper;
import com.mabi87.httprequestlib.HTTPRequestTask;

import org.apache.http.NameValuePair;

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
                new HTTPRequestTask<Void, Void, String>() {
                    @Override
                    protected String doInBackgroundRequest(Void... params) throws IOException, HTTPRequestException {
                        ArrayList<NameValuePair> lNameValuePare = new ArrayList<NameValuePair>();
                        HTTPRequestHelper lHelper = new HTTPRequestHelper();
                        return lHelper.post("", lNameValuePare);
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);

                        if(s != null) {
                            mTextResult.setText(s);
                        }
                    }

                    @Override
                    protected void onNetworkError(IOException pIOException) {
                        mTextResult.setText(pIOException.getMessage());
                    }

                    @Override
                    protected void onServerError(HTTPRequestException pHTTPRequestException) {
                        mTextResult.setText(pHTTPRequestException.getMessage());
                    }
                }.execute();
            }
        });

        mButtonGet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HTTPRequestTask<Void, Void, String>() {
                    @Override
                    protected String doInBackgroundRequest(Void... params) throws IOException, HTTPRequestException {
                        ArrayList<NameValuePair> lNameValuePare = new ArrayList<NameValuePair>();
                        HTTPRequestHelper lHelper = new HTTPRequestHelper();
                        return lHelper.get("", lNameValuePare);
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);

                        if(s != null) {
                            mTextResult.setText(s);
                        }
                    }

                    @Override
                    protected void onNetworkError(IOException pIOException) {
                        mTextResult.setText(pIOException.getMessage());
                    }

                    @Override
                    protected void onServerError(HTTPRequestException pHTTPRequestException) {
                        mTextResult.setText(pHTTPRequestException.getMessage());
                    }
                }.execute();
            }
        });

        mButtonGet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HTTPRequestTask<Void, Void, String>() {
                    @Override
                    protected String doInBackgroundRequest(Void... params) throws IOException, HTTPRequestException {
                        HTTPRequestHelper lHelper = new HTTPRequestHelper();
                        return lHelper.setConnectTimeoutMillis(1000).setReadTimeoutMillis(1000).get("");
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);

                        if(s != null) {
                            mTextResult.setText(s);
                        }
                    }

                    @Override
                    protected void onNetworkError(IOException pIOException) {
                        mTextResult.setText(pIOException.getMessage());
                    }

                    @Override
                    protected void onServerError(HTTPRequestException pHTTPRequestException) {
                        mTextResult.setText(pHTTPRequestException.getMessage());
                    }
                }.execute();
            }
        });
    }

    private void init() {

    }
}
