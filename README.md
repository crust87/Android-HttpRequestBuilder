# Android-HttpRequestBuilder
HttpRequestBuilder is builder using HttpURLConnection.

```java
HTTPRequestBuilder builder;
List<NameValuePair> parameters
parameters.add(new BasicNameValuePair("name1", "value1"));
parameters.add(new BasicNameValuePair("name2", "value2"));

builder.post(url, parameters);
```

or

```java
HTTPRequestBuilder builder;
builder.putParameter("name1", "value1").putParameter("name2", "value2");

builder.post(url);
```

or

```java
HTTPRequestBuilder builder;
builder.putParameter("name1", "value1").putParameter("name2", "value2").setPath(url).post();
```
