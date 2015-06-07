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
	private IOException mIOException = null;
	private HTTPRequestException mHTTPRequestException = null;

	@Override
	protected final Result doInBackground(Params... params) {
		try {
			return doInBackgroundRequest(params);
		} catch (IOException e) {
			mIOException = e;
			return null;
		} catch (HTTPRequestException e) {
			mHTTPRequestException = e;
			return null;
		}

	}

	 @Override
	 protected void onPostExecute(Result result) {
		 if(mHTTPRequestException != null) {
			 onRequestError(mHTTPRequestException.getMessage(), mHTTPRequestException.getResponseCode());
			 mHTTPRequestException = null;
		 }

		 if(mIOException != null) {
			 onNetworkError(mIOException.getMessage());
			 mIOException = null;
		 }
	 }

	 /**
	  * this method work when catch http request exception
	  * @param exceptionMessage
	  * 				Exception message
	  */
	 protected void onRequestError(String exceptionMessage, int responseCode) { }

	 /**
	  * this method work when catch io exception
	  * @param exceptionMessage
	  * 				Exception message
	  */
	 protected void onNetworkError(String exceptionMessage) { }

	 protected abstract Result doInBackgroundRequest(Params... params) throws IOException, HTTPRequestException;

}
