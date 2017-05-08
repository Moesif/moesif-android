# Moesif SDK for Android


 [ ![Download](https://api.bintray.com/packages/moesif/maven/moesif-android/images/download.svg) ](https://bintray.com/moesif/maven/moesif-android/_latestVersion)

moesif-android is a SDK that automatically captures HTTP traffic such as to your internal APIs or external APIs like Stripe.


## Requirements
This lib requires the use of OkHttp 2.2 or better. OkHttp 3.x is also supported.

The two popular Android networking clients (Volley and Retrofit) support using OkHttp so you are probably fine if using one of these two libs.

If using Retrofit, 1.9 or greater is required.
If using Volley, a simple one line change is required to use our MoesifOkHttpXStack.

## Module Structure
__moesif-android:__
The core SDK shared across platforms, imported automatically by the below HTTP stack specific modules.

__moesif-android-okhttp3:__
Supports the latest version (3.x) of OkHttp

__moesif-android-okhttp2:__
If you want to stick with OkHttp 2.x for legacy reasons, import this module instead of moesif-android-okhttp3

## How To Configure:

### 1. Add the dependency to your app/build.gradle
```gradle
# Add the jcenter repository, if not present

repositories {
  jcenter()
}

dependencies {
    compile 'com.moesif.android:moesif-android-okhttp3:1.1.0'
}
```

If your Android project imports Volley as an unmanaged artifact, you may have to exclude volley as a transitive dependency:
Otherwise, you may get duplicate class errors.

```gradle
# Add the jcenter repository, if not present

repositories {
  jcenter()
}

dependencies {
    compile ('com.moesif.android:moesif-android-okhttp3:1.1.0') {
        exclude group: 'com.android.volley'
    }
}
```

### 2. In your AndroidManifest.xml, add your Moesif ApplicationId under the "application" node:

```xml
<application>

    <!-- Your other code -->

    <meta-data
        android:name="com.moesif.android.ApplicationId"
        android:value="your_moesif_application_id" />
</application>
```

### 3. In your Application's onCreate(), initialize the lib

```java
import com.moesif.android.MoesifClient;

public final class MyApplicationClass extends Application {

    @Override
    public void onCreate() {
        MoesifClient.initialize(this);
    }
}
```


### 4. Add the Interceptor

#### 4.a If using Retrofit/OkHttp3 natively:
Add the Moesif MoesifOkHttp3Interceptor when building the Retrofit/OkHttp Client.
If you have existing interceptors, you probably want Moesif's interceptor last in the chain to ensure it captures the HTTP call
just before going over the wire.
[See more Info on using OkHttp Interceptors](https://github.com/square/okhttp/wiki/Interceptors)


```java
import com.moesif.android.okhttp3;

OkHttpClient client = new OkHttpClient.Builder()
    .addInterceptor(new MoesifOkHttp3Interceptor())
    .build();

```

#### 4.a If using Volley:
Pass in a new MoesifOkHttp3Stack object when creating Volley's request queue.
[See more info on setting up volley](https://developer.android.com/training/volley/requestqueue.html)


```java
import com.moesif.android.okhttp3;

    RequestQueue queue = Volley.newRequestQueue(myContext.getApplicationContext(),
                                                new MoesifOkHttp3Stack());

```

## Configuring using OkHttp2:
Follow the previous configuration steps, except a few changes:

### 1. Import moesif-android-okhttp2 instead
```gradle
# Add the jcenter repository, if not present
repositories {
  jcenter()
}

dependencies {
    compile 'com.moesif.android:moesif-android-okhttp2:1.1.0'
}
```

### 2. Use the corresponding OkHttp2 classes instead.

#### 2.a If using Retrofit/OkHttp3 natively:
```java
import com.moesif.android.okhttp2;

OkHttpClient myHttpClient new OkHttpClient();
myHttpClient.networkInterceptors().add(new MoesifOkHttp2Interceptor());

```

#### 2.a Or Volley:
```java
import com.moesif.android.okhttp2;

    RequestQueue queue = Volley.newRequestQueue(myContext.getApplicationContext(),
                                                new MoesifOkHttp2Stack(new OkHttpClient()));

```

## License info for the Android SDK
See [LICENSE File](https://raw.githubusercontent.com/Moesif/moesif-android/master/LICENSE) for details. The MoesifOkHttp3Stack and
MoesifOkHttp2Stack classes, and the entirety of the com.moesif.android.inspector package used by this
software have been licensed from non-Moesif sources and modified
for use in the library. Please see the relevant source files, and the
LICENSE file in the com.moesif.android.inspector package for details.

## Other Integrations

To view more more documenation on integration options, please visit __[the Integration Options Documentation](https://www.moesif.com/docs/getting-started/integration-options/).__
