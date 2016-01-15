# Android-HttpRequestBuilder
HttpRequestBuilder is builder using HttpURLConnection.

## Example

create builder
```java
HTTPRequest.Builder mBuilder = new HTTPRequest.Builder(stringUrl);
```

set optional parameters
```java
builder.setReadTimeoutMillis(10000);
builder.setConnectTimeoutMillis(15000);

builder.putParameter(name0, value0);
builder.putParameter(name1, value1);
builder.putParameter(name2, value2);

builder.setMethod("POST"); // or "GET"
```

build and get request object
```java
HTTPRequest request = builder.build();
```

request<br/>
you MUST NOT use this method in UI thread
```java
HTTPResponse response = request.request();
```

```java
int responseCode = response.getCode();
String responseMessage = response.getResponseMessage();
String errorMessage = response.getErrorMessage();
```

## License
Copyright 2015 Mabi

Licensed under the Apache License, Version 2.0 (the "License");<br/>
you may not use this work except in compliance with the License.<br/>
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software<br/>
distributed under the License is distributed on an "AS IS" BASIS,<br/>
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.<br/>
See the License for the specific language governing permissions and<br/>
limitations under the License.
