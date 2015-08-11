/*
 * HttpRequestBuilder
 * https://github.com/mabi87/Android-HttpRequestBuilder
 *
 * Mabi
 * crust87@gmail.com
 * last modify 2015-08-11
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

package com.mabi87.httprequestbuildersample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mabi87.httprequestbuilder.HTTPRequest;
import com.mabi87.httprequestbuilder.HTTPResponse;

import java.io.IOException;

public class MainActivity extends ActionBarActivity {

    // Layout Components
    private EditText mInputUrl;
    private Button mButtonSet;
    private EditText mInputKey;
    private EditText mInputValue;
    private Button mButtonAdd;
    private RadioGroup mRadioGroupMethod;
    private RadioButton mRadioGet;
    private RadioButton mRadioPost;
    private Button mButtonRequest;
    private Button mButtonClean;

    private TextView mTextResult;

    // Components
    private HTTPRequest.Builder mBuilder;
    private HTTPRequest mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadGUI();
        init();
    }

    private void loadGUI() {
        setContentView(R.layout.activity_main);

        mInputUrl = (EditText) findViewById(R.id.inputUrl);
        mButtonSet = (Button) findViewById(R.id.buttonSet);
        mInputKey = (EditText) findViewById(R.id.inputKey);
        mInputValue = (EditText) findViewById(R.id.inputValue);
        mButtonAdd = (Button) findViewById(R.id.buttonAdd);
        mRadioGroupMethod = (RadioGroup) findViewById(R.id.radioGroupMethod);
        mRadioGet = (RadioButton) findViewById(R.id.radioGet);
        mRadioPost = (RadioButton) findViewById(R.id.radioPost);
        mButtonRequest = (Button) findViewById(R.id.buttonRequest);
        mButtonClean = (Button) findViewById(R.id.buttonClean);
        mTextResult = (TextView) findViewById(R.id.textResult);
    }

    public void init() {
        mInputUrl.setText("");
        mInputKey.setText("");
        mInputValue.setText("");
        mRadioGet.setChecked(true);
        mTextResult.setText("");

        mInputUrl.setEnabled(true);
        mButtonSet.setEnabled(true);
        mInputKey.setEnabled(false);
        mInputValue.setEnabled(false);
        mButtonAdd.setEnabled(false);
        mRadioGet.setEnabled(false);
        mRadioPost.setEnabled(false);
        mButtonRequest.setEnabled(false);
        mButtonClean.setEnabled(false);
    }

    public void onButtonSetClicked(View v) {
        String inputString = mInputUrl.getText().toString();

        if(!inputString.equals("")) {
            mInputUrl.setEnabled(false);
            mButtonSet.setEnabled(false);
            mInputKey.setEnabled(true);
            mInputValue.setEnabled(true);
            mButtonAdd.setEnabled(true);
            mRadioGet.setEnabled(true);
            mRadioPost.setEnabled(true);
            mButtonRequest.setEnabled(true);
            mButtonClean.setEnabled(true);

            mBuilder = new HTTPRequest.Builder(inputString);
        } else {
            Toast.makeText(getApplicationContext(), "type url!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onButtonAddClicked(View v) {
        String name = mInputKey.getText().toString();
        String value = mInputValue.getText().toString();
        mInputKey.setText("");
        mInputValue.setText("");
        mBuilder.putParameter(name, value);
    }

    public void onButtonRequestClicked(View v) {
        int radioId = mRadioGroupMethod.getCheckedRadioButtonId();

        switch(radioId) {
            case R.id.radioPost:
                mBuilder.setMethod("POST");
                break;
            case R.id.radioGet:
                mBuilder.setMethod("GET");
                break;
        }

        mRequest = mBuilder.build();

        new AsyncTask<Void, Void, HTTPResponse>() {

            @Override
            protected HTTPResponse doInBackground(Void... params) {
                try {
                   return mRequest.request();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(HTTPResponse httpResponse) {
                if(httpResponse != null) {
                    String responseMessage = httpResponse.getResponseMessage();
                    String errorMessage = httpResponse.getErrorMessage();

                    if(!responseMessage.equals("")) {
                        mTextResult.setText(responseMessage);
                    }

                    if(!errorMessage.equals("")) {
                        mTextResult.setText(errorMessage);
                    }
                }
            }
        }.execute();
    }

    public void onButtonCleanClicked(View v) {
        init();
    }

}
