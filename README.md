# Android-HttpRequestBuilder
HttpRequestBuilder is builder using HttpURLConnection.

## Example
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

## Licence
Copyright 2015 Mabi

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
