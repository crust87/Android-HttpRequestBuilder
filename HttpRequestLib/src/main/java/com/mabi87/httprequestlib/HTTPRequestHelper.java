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

import android.util.Log;

import org.apache.http.NameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class HTTPRequestHelper {
	private static final String TAG = "HTTPRequestHelper";

	// Attributes
	private int mReadTimeoutMillis;
	private int mConnectTimeoutMillis;

	public HTTPRequestHelper() {
		mReadTimeoutMillis = 10000;
		mConnectTimeoutMillis = 15000;
	}

	/**
	 * @param pReadTimeoutMillis
	 * 				the millisecond in integer.
	 * @return instance
	 */
	public HTTPRequestHelper setReadTimeoutMillis(int pReadTimeoutMillis) {
		mReadTimeoutMillis = pReadTimeoutMillis;

		return this;
	}

	/**
	 * @param pConnectTimeoutMillis
	 * 				the millisecond in integer.
	 * @return instance
	 */
	public HTTPRequestHelper setConnectTimeoutMillis(int pConnectTimeoutMillis) {
		mConnectTimeoutMillis = pConnectTimeoutMillis;

		return this;
	}

	/**
	 * @param pPath
	 * 				the string url of web server page.
	 * @param pNameValuePairs
	 * 				the NameValuePare List of http parameter.
	 * @throws IOException
	 * 				throws from HttpURLConnection method.
	 * @throws HTTPRequestException
	 * 				if responseCode is not 200
	 * @return the string of http page
	 */
	public String post(String pPath, List<NameValuePair> pNameValuePairs) throws IOException, HTTPRequestException {
		URL url = new URL(pPath);
		HttpURLConnection lConnection = (HttpURLConnection) url.openConnection();

		lConnection.setReadTimeout(mReadTimeoutMillis);
		lConnection.setConnectTimeout(mConnectTimeoutMillis);
		lConnection.setRequestMethod("POST");
		lConnection.setDoInput(true);
		lConnection.setDoOutput(true);

		OutputStream lOutStream = lConnection.getOutputStream();
		BufferedWriter lWriter = new BufferedWriter(new OutputStreamWriter(lOutStream, "UTF-8"));
		lWriter.write(getQuery(pNameValuePairs));
		lWriter.flush();
		lWriter.close();
		lOutStream.close();

		lConnection.connect();

		StringBuilder lResponseStringBuilder = new StringBuilder();

		boolean isSuccess = readPage(lConnection, lResponseStringBuilder);

		lConnection.disconnect();

		Log.d(TAG, "HTTPRequestHelper post : " + pPath + " " + lResponseStringBuilder.toString());

		if(isSuccess) {
			return lResponseStringBuilder.toString();
		} else {
			throw new HTTPRequestException(lResponseStringBuilder.toString());
		}
	}

	/**
	 * @param pPath
	 * 				the string url of web server page.
	 * @throws IOException
	 * 				throws from HttpURLConnection method.
	 * @throws HTTPRequestException
	 * 				if responseCode is not 200
	 * @return the string of http page
	 */
	public String get(String pPath) throws IOException, HTTPRequestException {
		URL url = new URL(pPath);
		HttpURLConnection lConnection = (HttpURLConnection) url.openConnection();

		lConnection.setReadTimeout(mReadTimeoutMillis);
		lConnection.setConnectTimeout(mConnectTimeoutMillis);
		lConnection.setRequestMethod("GET");
		lConnection.setDoInput(true);

		lConnection.connect();

		StringBuilder lResponseStringBuilder = new StringBuilder();

		boolean isSuccess = readPage(lConnection, lResponseStringBuilder);

		lConnection.disconnect();

		Log.d(TAG, "HTTPRequestHelper get : " + pPath + " " + lResponseStringBuilder.toString());

		if(isSuccess) {
			return lResponseStringBuilder.toString();
		} else {
			throw new HTTPRequestException(lResponseStringBuilder.toString());
		}
	}

	/**
	 * @param pPath
	 * 				the string url of web server page.
	 * @param pNameValuePairs
	 * 				the NameValuePare List of http parameter.
	 * @throws IOException
	 * 				throws from HttpURLConnection method.
	 * @throws HTTPRequestException
	 * 				if responseCode is not 200
	 * @return the string of http page
	 */
	public String get(String pPath, List<NameValuePair> pNameValuePairs) throws IOException, HTTPRequestException {
		return get(pPath + "?" + getQuery(pNameValuePairs));
	}

	/**
	 * @param pConnection
	 * 				the HttpURLConnection of web server page.
	 * @param pResponseStringBuilder
	 * 				the response StringBuilder for store connection response
	 * @throws IOException
	 * 				throws from HttpURLConnection method.
	 * @return if response cod is 200
	 */
	public boolean readPage(HttpURLConnection pConnection, StringBuilder pResponseStringBuilder) throws IOException {
		BufferedReader lBufferedReader = null;
		String lLine = null;
		boolean isSuccess = false;

		isSuccess = pConnection.getResponseCode() == HttpURLConnection.HTTP_OK;

		if(isSuccess) {
			lBufferedReader= new BufferedReader(new InputStreamReader(pConnection.getInputStream()));
		} else {
			lBufferedReader= new BufferedReader(new InputStreamReader(pConnection.getErrorStream()));
		}

		while ((lLine = lBufferedReader.readLine()) != null) {
			pResponseStringBuilder.append(lLine + '\n');
		}

		lBufferedReader.close();

		return isSuccess;
	}

	/**
	 * @param pNameValuePairs
	 * 				the NameValuePare List of http parameter.
	 * @throws UnsupportedEncodingException
	 * 				throws from URLEncoder.encode()
	 * @return the string of http parameter format
	 */
	private String getQuery(List<NameValuePair> pNameValuePairs) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (NameValuePair pair : pNameValuePairs) {
			if (first) {
				first = false;
			} else {
				result.append("&");
			}

			result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
		}

		return result.toString();
	}

}
