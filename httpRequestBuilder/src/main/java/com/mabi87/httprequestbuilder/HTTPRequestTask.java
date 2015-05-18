/*
 * HttpRequestBuilder
 * https://github.com/mabi87/Android-HttpRequestBuilder
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

package com.mabi87.httprequestbuilder;

import android.os.AsyncTask;

import java.io.IOException;


public abstract class HTTPRequestTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result>{

	// Working variablesprivate boolean hasNetworkError = false;
	private String mIOExceptionMessage = null;
	private String mHTTPRequestExceptionMessage = null;

	@Override
	protected final Result doInBackground(Params... params) {
		try {
			return doInBackgroundRequest(params);
		} catch (IOException e) {
			mIOExceptionMessage = e.getMessage();
			return null;
		} catch (HTTPRequestException e) {
			mHTTPRequestExceptionMessage = e.getMessage();
			return null;
		}

	}

	 @Override
	 protected void onPostExecute(Result result) {
		 if(mHTTPRequestExceptionMessage != null) {
			 onServerError(mHTTPRequestExceptionMessage);
			 mHTTPRequestExceptionMessage = null;
		 }

		 if(mIOExceptionMessage != null) {
			 onNetworkError(mIOExceptionMessage);
			 mIOExceptionMessage = null;
		 }
	 }

	 /**
	  * this method work when catch http request exception
	  * @param pExceptionMessage
	  * 				Exception message
	  */
	 protected void onServerError(String pExceptionMessage) { }

	 /**
	  * this method work when catch io exception
	  * @param pExceptionMessage
	  * 				Exception message
	  */
	 protected void onNetworkError(String pExceptionMessage) { }

	 protected abstract Result doInBackgroundRequest(Params... params) throws IOException, HTTPRequestException;

}
