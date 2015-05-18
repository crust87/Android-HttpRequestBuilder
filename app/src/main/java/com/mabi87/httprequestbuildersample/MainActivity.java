/*
 * HttpRequestBuilder
 * https://github.com/mabi87/Android-HttpRequestBuilder
 *
 * Mabi
 * crust87@gmail.com
 * last modify 2015-05-18
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

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mabi87.app.R;
import com.mabi87.httprequestbuilder.HTTPRequestBuilder;
import com.mabi87.httprequestbuilder.HTTPRequestException;
import com.mabi87.httprequestbuilder.HTTPRequestTask;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    // Layout Components
    private EditText mInputUrl;
    private EditText mInputKey;
    private EditText mInputValue;
    private RadioGroup mRadioGroupMethod;
    private TextView mTextResult;

    // Components
    private HTTPRequestBuilder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBuilder = new HTTPRequestBuilder();
        mBuilder.init();

        loadGUI();
    }

    private void loadGUI() {
        setContentView(R.layout.activity_main);

        mInputUrl = (EditText) findViewById(R.id.inputUrl);
        mInputKey = (EditText) findViewById(R.id.inputKey);
        mInputValue = (EditText) findViewById(R.id.inputValue);
        mRadioGroupMethod = (RadioGroup) findViewById(R.id.radioGroupMethod);
        mTextResult = (TextView) findViewById(R.id.textResult);
    }

    public void onButtonAddClicked(View v) {
        String key = mInputKey.getText().toString();
        String value = mInputValue.getText().toString();

        mBuilder.putParameter(key, value);
    }

    public void onButtonSendClicked(View v) {
        String url = mInputUrl.getText().toString();
        mBuilder.setPath(url);

        new HTTPRequestTask<Void, Void, String>() {
            @Override
            protected String doInBackgroundRequest(Void... params) throws IOException, HTTPRequestException {
                int radioId = mRadioGroupMethod.getCheckedRadioButtonId();

                switch(radioId) {
                    case R.id.radioPost:
                        return mBuilder.post();
                    case R.id.radioGet:
                        return mBuilder.get();
                    default:
                        return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if(s != null) {
                    mTextResult.setText(s);
                }
            }

            @Override
            protected void onNetworkError(String pExceptionMessage) {
                mTextResult.setText(pExceptionMessage);
            }

            @Override
            protected void onServerError(String pExceptionMessage) {
                mTextResult.setText(pExceptionMessage);
            }

        }.execute();
    }

    public void onButtonCleanClicked(View v) {
        mBuilder.init();
        mTextResult.setText("");
    }

}
