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

package com.mabi87.httprequestlib;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

 /*
  * HTTPRequestTask Class
  * Use with HTTPRequestHelper
  * JSONException usually means error on server and IOException usually means error on network in my application.
  * And methods in HTTPRequestHelper throw JSONException and IOException.
  * So when you use method in HTTPRequestHelper, this class is helpful.
  */
public abstract class HTTPRequestTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result>{

    // Working variables
	private boolean hasNetworkError = false;
	private boolean hasServerError = false;

	@Override
	protected Result doInBackground(Params... params) {
		try {
			return doInBackgroundRequest(params);
		} catch (IOException e) {
			hasNetworkError = true;
			return null;
		} catch (JSONException e) {
			hasServerError = true;
			return null;
		}

	}

	@Override
	protected void onPostExecute(Result result) {
		if(hasServerError) {
			onServerError();
		}

		if(hasNetworkError) {
			onNetworkError();
		}
	}

    // this method work when catch json exception
	protected void onServerError() { }

    // this method work when catch io exception
	protected void onNetworkError() { }

	protected abstract Result doInBackgroundRequest(Params... params) throws IOException, JSONException;

}
