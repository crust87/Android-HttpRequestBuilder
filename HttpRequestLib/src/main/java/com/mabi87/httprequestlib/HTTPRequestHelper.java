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
import org.json.JSONException;
import org.json.JSONObject;

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
	
	public static JSONObject post(String pPath, List<NameValuePair> pNameValuePairs) throws IOException, JSONException {
		URL url = new URL(pPath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		JSONObject json = new JSONObject();

		conn.setReadTimeout(10000);
		conn.setConnectTimeout(15000);
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);

		OutputStream os = conn.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		writer.write(getQuery(pNameValuePairs));
		writer.flush();
		writer.close();
		os.close();

		conn.connect();

		StringBuilder responseStringBuilder = new StringBuilder();
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String stringLine = null;
			
			while ((stringLine = bufferedReader.readLine()) != null) {
				responseStringBuilder.append(stringLine + '\n');
			}
			
			bufferedReader.close();
		} else {
			BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(conn.getErrorStream()));
			String stringLine = null;
			
			while ((stringLine = bufferedReader.readLine()) != null) {
				responseStringBuilder.append(stringLine + '\n');
			}
			
			bufferedReader.close();
		}
		
		conn.disconnect();

		Log.d(TAG, "HTTPRequestHelper post : " + pPath + " " + responseStringBuilder.toString());
		json = new JSONObject(responseStringBuilder.toString());
		return json;
	}

	public static JSONObject get(String pPath, List<NameValuePair> pNameValuePairs) throws IOException, JSONException {
		return get(pPath + "?" + getQuery(pNameValuePairs));
	}

	public static JSONObject get(String pPath) throws IOException, JSONException {
		URL url = new URL(pPath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		JSONObject json = new JSONObject();

		conn.setReadTimeout(10000);
		conn.setConnectTimeout(15000);
		conn.setRequestMethod("GET");
		conn.setDoInput(true);

		conn.connect();

		StringBuilder responseStringBuilder = new StringBuilder();
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String stringLine = null;

			while ((stringLine = bufferedReader.readLine()) != null) {
				responseStringBuilder.append(stringLine + '\n');
			}

			bufferedReader.close();
		} else {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			String stringLine = null;

			while ((stringLine = bufferedReader.readLine()) != null) {
				responseStringBuilder.append(stringLine + '\n');
			}

			bufferedReader.close();
		}

		conn.disconnect();

		Log.d(TAG, "HTTPRequestHelper get : " + pPath + " " + responseStringBuilder.toString());
		json = new JSONObject(responseStringBuilder.toString());

		return json;
	}

	private static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (NameValuePair pair : params) {
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
