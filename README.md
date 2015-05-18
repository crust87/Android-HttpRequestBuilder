# Android-HttpRequestBuilder
HttpRequestBuilder is builder using HttpURLConnection.

### Example
```java
HTTPRequestBuilder builder;
List<NameValuePair> parameters
parameters.add(new BasicNameValuePair("name1", "value1"));
parameters.add(new BasicNameValuePair("name2", "value2"));

builder.post(url, parameters);
```

```java
HTTPRequestBuilder builder;
builder.putParameter("name1", "value1").putParameter("name2", "value2");

builder.post(url);
```

```java
HTTPRequestBuilder builder;
builder.putParameter("name1", "value1")
        .putParameter("name2", "value2").setPath(url).post();
```

with async

```java
new HTTPRequestTask<Void, Void, String>() {
    @Override
    protected String doInBackgroundRequest(Void... params) throws IOException, HTTPRequestException {
        return mBuilder.post();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(s != null) {
            // TODO
        }
    }

    @Override
    protected void onNetworkError(IOException pIOException) {
        // TODO
    }

    @Override
    protected void onServerError(HTTPRequestException pHTTPRequestException) {
        // TODO
    }

}.execute();
```
