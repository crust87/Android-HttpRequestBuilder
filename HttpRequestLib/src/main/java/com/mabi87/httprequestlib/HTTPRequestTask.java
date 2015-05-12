package com.mabi87.httprequestlib;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

public abstract class HTTPRequestTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result>{

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

	protected void onServerError() { }
	protected void onNetworkError() { }

	protected abstract Result doInBackgroundRequest(Params... params) throws IOException, JSONException;

}
