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

package com.mabi87.httprequestbuilder;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

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
import java.util.ArrayList;
import java.util.List;

public class HTTPRequestBuilder {
	private static final String TAG = "HTTPRequestBuilder";

	// Components
	private ArrayList<NameValuePair> mParameters;

	// Attributes
	private String mPath;
	private int mReadTimeoutMillis;
	private int mConnectTimeoutMillis;

	public HTTPRequestBuilder() {
		mParameters = new ArrayList<NameValuePair>();

		init();
	}

	public void init() {
		mParameters.clear();
		mPath = "";
		mReadTimeoutMillis = 10000;
		mConnectTimeoutMillis = 15000;
	}

	/**
	 * @param path
	 * 				the string url of web server page.
	 * @param parameters
	 * 				the NameValuePare List of http parameter.
	 * @throws IOException
	 * 				throws from HttpURLConnection method.
	 * @throws HTTPRequestException
	 * 				if responseCode is not 200
	 * @return the string of http page
	 */
	public String post(String path, List<NameValuePair> parameters) throws IOException, HTTPRequestException {
		mPath = path;
		mParameters.addAll(parameters);

		return post();
	}

	/**
	 * @param path
	 * 				the string url of web server page.
	 * @throws IOException
	 * 				throws from HttpURLConnection method.
	 * @throws HTTPRequestException
	 * 				if responseCode is not 200
	 * @return the string of http page
	 */
	public String post(String path) throws IOException, HTTPRequestException {
		mPath = path;

		return post();
	}

	/**
	 * @throws IOException
	 * 				throws from HttpURLConnection method.
	 * @throws HTTPRequestException
	 * 				if responseCode is not 200
	 * @return the string of http page
	 */
	public String post() throws IOException, HTTPRequestException {
		URL url = new URL(mPath);
		HttpURLConnection lConnection = (HttpURLConnection) url.openConnection();

		lConnection.setReadTimeout(mReadTimeoutMillis);
		lConnection.setConnectTimeout(mConnectTimeoutMillis);
		lConnection.setRequestMethod("POST");
		lConnection.setDoInput(true);
		lConnection.setDoOutput(true);

		OutputStream lOutStream = lConnection.getOutputStream();
		BufferedWriter lWriter = new BufferedWriter(new OutputStreamWriter(lOutStream, "UTF-8"));
		lWriter.write(getQuery(mParameters));
		lWriter.flush();
		lWriter.close();
		lOutStream.close();

		lConnection.connect();

		StringBuilder lResponseStringBuilder = new StringBuilder();

		int responseCode = readPage(lConnection, lResponseStringBuilder);
		String responseString = lResponseStringBuilder.toString();

		lConnection.disconnect();

		Log.d(TAG, "HTTPRequestBuilder post : " + mPath + " " + lResponseStringBuilder.toString());

		if(responseCode == HttpURLConnection.HTTP_OK) {
			return responseString;
		} else {
			throw new HTTPRequestException(responseString, responseCode);
		}
	}

	/**
	 * @param path
	 * 				the string url of web server page.
	 * @param parameters
	 * 				the NameValuePare List of http parameter.
	 * @throws IOException
	 * 				throws from HttpURLConnection method.
	 * @throws HTTPRequestException
	 * 				if responseCode is not 200
	 * @return the string of http page
	 */
	public String get(String path, List<NameValuePair> parameters) throws IOException, HTTPRequestException {
		mPath = path;
		mParameters.addAll(parameters);

		return get();
	}

	/**
	 * @param path
	 * 				the string url of web server page.
	 * @throws IOException
	 * 				throws from HttpURLConnection method.
	 * @throws HTTPRequestException
	 * 				if responseCode is not 200
	 * @return the string of http page
	 */
	public String get(String path) throws IOException, HTTPRequestException {
		mPath = path;

		return get();
	}

	/**
	 * @throws IOException
	 * 				throws from HttpURLConnection method.
	 * @throws HTTPRequestException
	 * 				if responseCode is not 200
	 * @return the string of http page
	 */
	public String get() throws IOException, HTTPRequestException {
		URL url = null;

		if(mParameters.size() > 0) {
			url = new URL(mPath + "?" + getQuery(mParameters));
		} else {
			url = new URL(mPath);
		}

		HttpURLConnection lConnection = (HttpURLConnection) url.openConnection();

		lConnection.setReadTimeout(mReadTimeoutMillis);
		lConnection.setConnectTimeout(mConnectTimeoutMillis);
		lConnection.setRequestMethod("GET");
		lConnection.setDoInput(true);

		lConnection.connect();

		StringBuilder lResponseStringBuilder = new StringBuilder();

		int responseCode = readPage(lConnection, lResponseStringBuilder);
		String responseString = lResponseStringBuilder.toString();

		lConnection.disconnect();

		Log.d(TAG, "HTTPRequestBuilder get : " + mPath + " " + lResponseStringBuilder.toString());

		if(responseCode == HttpURLConnection.HTTP_OK) {
			return responseString;
		} else {
			throw new HTTPRequestException(responseString, responseCode);
		}
	}

	/**
	 * @param connection
	 * 				the HttpURLConnection of web server page.
	 * @param responseStringBuilder
	 * 				the response StringBuilder for store connection response
	 * @throws IOException
	 * 				throws from HttpURLConnection method.
	 * @return the Integer value of response code
	 */
	public int readPage(HttpURLConnection connection, StringBuilder responseStringBuilder) throws IOException {
		BufferedReader lBufferedReader = null;
		String lLine = null;
		boolean isSuccess = false;
		int responseCode = connection.getResponseCode();


		isSuccess = responseCode == HttpURLConnection.HTTP_OK;

		if(isSuccess) {
			lBufferedReader= new BufferedReader(new InputStreamReader(connection.getInputStream()));
		} else {
			lBufferedReader= new BufferedReader(new InputStreamReader(connection.getErrorStream()));
		}

		while ((lLine = lBufferedReader.readLine()) != null) {
			responseStringBuilder.append(lLine + '\n');
		}

		lBufferedReader.close();

		return responseCode;
	}

	/**
	 * @param parameter
	 * 				the NameValuePare List of http parameter.
	 * @throws UnsupportedEncodingException
	 * 				throws from URLEncoder.encode()
	 * @return the string of http parameter format
	 */
	private String getQuery(List<NameValuePair> parameter) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (NameValuePair pair : parameter) {
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

	/**
	 * @param parameters
	 * 				the list of parameters.
	 * @return instance
	 */
	public HTTPRequestBuilder putParameter(List<NameValuePair> parameters) {
		mParameters.addAll(parameters);

		return this;
	}

	/**
	 * @param name
	 * 				the key of parameter.
	 * @param value
	 * 				the value of parameter.
	 * @return instance
	 */
	public HTTPRequestBuilder putParameter(String name, String value) {
		mParameters.add(new BasicNameValuePair(name, value));

		return this;
	}

	/**
	 * @param path
	 * 				the string url of web server page.
	 * @return instance
	 */
	public HTTPRequestBuilder setPath(String path) {
		mPath = path;

		return this;
	}

	/**
	 * @param readTimeoutMillis
	 * 				the millisecond in integer.
	 * @return instance
	 */
	public HTTPRequestBuilder setReadTimeoutMillis(int readTimeoutMillis) {
		mReadTimeoutMillis = readTimeoutMillis;

		return this;
	}

	/**
	 * @param connectTimeoutMillis
	 * 				the millisecond in integer.
	 * @return instance
	 */
	public HTTPRequestBuilder setConnectTimeoutMillis(int connectTimeoutMillis) {
		mConnectTimeoutMillis = connectTimeoutMillis;

		return this;
	}

}
