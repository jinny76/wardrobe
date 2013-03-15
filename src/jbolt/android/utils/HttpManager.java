package jbolt.android.utils;

import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import jbolt.android.base.exception.DeviceRuntimeException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class HttpManager {

    private static final String USER_AGENT = "Mozilla/4.5";
    private static final String TAG = HttpManager.class.getName();

    private static SchemeRegistry registry;

    static {
        try {
            X509HostnameVerifier hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
            registry = new SchemeRegistry();
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
            socketFactory.setHostnameVerifier(hostnameVerifier);
            registry.register(new Scheme("https", socketFactory, 8443));
            HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
        } catch (KeyStoreException e) {
            Log.e(TAG, e.getMessage(), e);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e.getMessage(), e);
        } catch (CertificateException e) {
            Log.e(TAG, e.getMessage(), e);
        } catch (KeyManagementException e) {
            Log.e(TAG, e.getMessage(), e);
        } catch (UnrecoverableKeyException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public static void getRequestAsync(final String url, final Map<String, String> params, final Handler handler) {
        Thread thread = new Thread(
                new Runnable() {
                    public void run() {
                        Message message = new Message();
                        try {
                            HttpClient httpclient = new DefaultHttpClient();
                            if (url.toLowerCase().startsWith("https://")) {
                                SingleClientConnManager mgr = new SingleClientConnManager(httpclient.getParams(), registry);
                                httpclient = new DefaultHttpClient(mgr, httpclient.getParams());
                            }
                            message.obj = getRequest(url, params, httpclient);
                            handler.sendMessage(message);
                        } catch (Exception e) {
                            message.obj = e;
                            handler.sendMessage(message);
                        }
                    }
                });
        thread.start();
    }

    public static void getDrawableAsync(final String url, final Map<String, String> params, final Handler handler) {
        Thread thread = new Thread(
                new Runnable() {
                    public void run() {
                        Message message = new Message();
                        try {
                            HttpClient httpclient = new DefaultHttpClient();
                            if (url.toLowerCase().startsWith("https://")) {
                                SingleClientConnManager mgr = new SingleClientConnManager(httpclient.getParams(), registry);
                                httpclient = new DefaultHttpClient(mgr, httpclient.getParams());
                            }
                            message.obj = getImage(url, params, httpclient);
                            handler.sendMessage(message);
                        } catch (Exception e) {
                            message.obj = e;
                            handler.sendMessage(message);
                        }
                    }
                });
        thread.start();
    }

    public static String getRequestSync(final String url, final Map<String, String> params) throws DeviceRuntimeException {
        HttpClient httpclient = new DefaultHttpClient();
        if (url.toLowerCase().startsWith("https://")) {
            SingleClientConnManager mgr = new SingleClientConnManager(httpclient.getParams(), registry);
            httpclient = new DefaultHttpClient(mgr, httpclient.getParams());
        }
        return getRequest(url, params, httpclient);
    }

    public static String getRequest(String url, Map<String, String> params, HttpClient client)
            throws DeviceRuntimeException {
        String result = null;
        int statusCode;
        HttpPost post = new HttpPost(url);
        putParams(params, post);
        try {
            post.setHeader("User-Agent", USER_AGENT);
            HttpResponse httpResponse = client.execute(post);
            statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                result = retrieveInputStream(httpResponse.getEntity());
            } else {
                throw new DeviceRuntimeException("Network error!");
            }
        } catch (IOException e) {
            throw new DeviceRuntimeException("Network error!");
        } finally {
            post.abort();
        }
        return result;
    }

    public static Drawable getImage(String url, Map<String, String> params, HttpClient client) throws DeviceRuntimeException {
        Drawable result = null;
        int statusCode;
        HttpPost post = new HttpPost(url);
        putParams(params, post);

        try {
            post.setHeader("User-Agent", USER_AGENT);
            HttpResponse httpResponse = client.execute(post);
            statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                result = Drawable.createFromStream(httpResponse.getEntity().getContent(), "src");
            } else {
                throw new DeviceRuntimeException("Network error!");
            }
        } catch (IOException e) {
            throw new DeviceRuntimeException("Network error!");
        } finally {
            post.abort();
        }
        return result;
    }

    private static void putParams(Map<String, String> params, HttpPost post) {
        if (params != null) {
            ArrayList<NameValuePair> paramsValue = new ArrayList<NameValuePair>();
            for (String paramName : params.keySet()) {
                paramsValue.add(new BasicNameValuePair(paramName, params.get(paramName)));
            }
            try {
                HttpEntity entity = new UrlEncodedFormEntity(paramsValue, "UTF-8");
                post.setEntity(entity);
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
    }

    protected static String retrieveInputStream(HttpEntity httpEntity) throws IOException {
        int length = (int) httpEntity.getContentLength();
        if (length < 0) length = 10000;
        StringBuilder content = new StringBuilder(length);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(httpEntity.getContent(), HTTP.UTF_8);
            char buffer[] = new char[length];
            int count;
            while ((count = inputStreamReader.read(buffer, 0, length - 1)) > 0) {
                content.append(buffer, 0, count);
            }
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage(), e);
        } catch (IllegalStateException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return content.toString();
    }

    public static void downloadAsync(
            final String url, final Map<String, String> params, final String fileName, final Handler handler) {
        Thread thread = new Thread(
                new Runnable() {
                    public void run() {
                        Message message = new Message();
                        try {
                            message.obj = downloadFromUrl(url, params, fileName);
                            handler.sendMessage(message);
                        } catch (Exception e) {
                            message.obj = e;
                            handler.sendMessage(message);
                        }
                    }
                });
        thread.start();
    }

    public static File downloadFromUrl(String url, Map<String, String> params, String fileName)
            throws DeviceRuntimeException {
        File result = null;
        int statusCode;
        HttpClient httpclient = new DefaultHttpClient();
        if (url.toLowerCase().startsWith("https://")) {
            SingleClientConnManager mgr = new SingleClientConnManager(httpclient.getParams(), registry);
            httpclient = new DefaultHttpClient(mgr, httpclient.getParams());
        }
        HttpPost post = new HttpPost(url);
        putParams(params, post);

        try {
            post.setHeader("User-Agent", USER_AGENT);
            HttpResponse httpResponse = httpclient.execute(post);
            statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                result = download(httpResponse.getEntity(), fileName);
            } else {
                throw new DeviceRuntimeException("Network error!");
            }
        } catch (IOException e) {
            throw new DeviceRuntimeException("Network error!", e);
        } finally {
            post.abort();
        }
        return result;
    }

    protected static File download(HttpEntity httpEntity, String fileName) throws IOException {
        File file = null;
        try {
            InputStream is = httpEntity.getContent();
            FileOutputStream fileOutputStream = null;
            if (is != null) {
                file = new File(Environment.getExternalStorageDirectory(), fileName);
                fileOutputStream = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int ch;
                while ((ch = is.read(buf)) != -1) {
                    fileOutputStream.write(buf, 0, ch);
                }
                fileOutputStream.flush();
            }

            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (is != null) {
                is.close();
            }
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage(), e);
        } catch (IllegalStateException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return file;
    }

    public static void uploadAsync(
            final String url, final Map<String, String> params, final Map files, final Handler handler) {
        Thread thread = new Thread(
                new Runnable() {
                    public void run() {
                        Message message = new Message();
                        try {
                            message.obj = uploadFiles(url, params, files);
                            handler.sendMessage(message);
                        } catch (Exception e) {
                            message.obj = e;
                            handler.sendMessage(message);
                        }
                    }
                });
        thread.start();
    }

    public static String uploadFiles(
            String url, Map<String, String> params,
            Map<String, File> files) throws DeviceRuntimeException {
        try {
            String BOUNDARY = java.util.UUID.randomUUID().toString();
            String PREFIX = "--", LINEND = "\r\n";
            String MULTIPART_FROM_DATA = "multipart/form-data";
            String CHARSET = "UTF-8";

            URL uri = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            conn.setReadTimeout(5 * 1000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINEND);
                sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
                sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
                sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
                sb.append(LINEND);
                sb.append(entry.getValue());
                sb.append(LINEND);
            }

            DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
            outStream.write(sb.toString().getBytes());
            if (files != null) {
                int i = 0;
                for (Map.Entry<String, File> file : files.entrySet()) {
                    StringBuilder sb1 = new StringBuilder();
                    sb1.append(PREFIX);
                    sb1.append(BOUNDARY);
                    sb1.append(LINEND);
                    sb1.append(
                            "Content-Disposition: form-data; name=\"file" + (i++) + "\"; filename=\"" + file.getKey() + "\""
                                    + LINEND);
                    sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
                    sb1.append(LINEND);
                    outStream.write(sb1.toString().getBytes("UTF-8"));

                    InputStream is = new FileInputStream(file.getValue());
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = is.read(buffer)) != -1) {
                        outStream.write(buffer, 0, len);
                    }

                    is.close();
                    outStream.write(LINEND.getBytes());
                }
            }

            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
            outStream.write(end_data);
            outStream.flush();

            int responseCode = conn.getResponseCode();
            String result = null;
            if (responseCode == HttpStatus.SC_OK) {
                StringBuilder content = new StringBuilder();
                try {
                    InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream(), HTTP.UTF_8);
                    char buffer[] = new char[1000];
                    int count;
                    while ((count = inputStreamReader.read(buffer, 0, 999)) > 0) {
                        content.append(buffer, 0, count);
                    }
                } catch (UnsupportedEncodingException e) {
                    Log.e(TAG, e.getMessage(), e);
                } catch (IllegalStateException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
                result = content.toString();
            }
            return result;
        } catch (IOException e) {
            throw new DeviceRuntimeException(e.getMessage(), e);
        }
    }

}
