package aero.developer.beaconExplorer.httpRequests;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import aero.developer.beaconExplorer.objects.RequestFail;
import aero.developer.beaconExplorer.utils.AppStatics;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HttpRequest {
	public static void trustEveryone(String url) {
		if (url != null && url.toLowerCase().contains("https")) {
			Log.e("HTTPREQUREST", "HTTPS OPENNING");
			try {
				SchemeRegistry schemeRegistry = new SchemeRegistry();
				schemeRegistry.register(new Scheme("https", SSLSocketFactory
						.getSocketFactory(), 443));
				HttpsURLConnection
						.setDefaultHostnameVerifier(new HostnameVerifier() {
							public boolean verify(String hostname,
									SSLSession session) {
								return true;
							}
						});
				SSLContext context = SSLContext.getInstance("TLS");
				context.init(null,
						new X509TrustManager[] { new X509TrustManager() {
							public void checkClientTrusted(
									X509Certificate[] chain, String authType)
									throws CertificateException {

							}

							public void checkServerTrusted(
									X509Certificate[] chain, String authType)
									throws CertificateException {
							}

							public X509Certificate[] getAcceptedIssuers() {
								return new X509Certificate[0];
							}
						} }, new SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(context
						.getSocketFactory());
			} catch (Exception e) { // should never happen
				// DisplayToastNoConnection();
				e.printStackTrace();
			}
		} else {
			Log.e("HTTPREQUREST", "HTTPS SKIPPING");
		}
	}

	public HttpRequest() {

	}

	public boolean isEmulator() {
		return Build.MANUFACTURER.equals("unknown");
	}

	public InputStream getImage(String url) {
		String ret = null;
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			return is;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * HttpGet request
	 * 
	 * @param sUrl
	 * @return
	 */
	// @SuppressWarnings("unchecked")
	public HttpData get(String sUrl,Context context) {// synchronized
		if (sUrl != null)
			sUrl = sUrl.replaceAll(" ", "%20");

		boolean connected = isConnected();
		HttpData ret = new HttpData();
		int responseCode = 0;
		System.out.println("isConnected: " + connected);

		System.out.println(sUrl);
		if (connected) {
			try {
				int tries = 0;

				while (tries < 3) {

					ret = new HttpData();
					String str;
					StringBuffer buff = new StringBuffer();
					BufferedReader in = null;
					Map<String, List<String>> headers;
					try {

						URL url = new URL(sUrl);
						if (sUrl.contains("https")) {
							trustEveryone(sUrl);
							HttpsURLConnection con = (HttpsURLConnection) url
									.openConnection();

							con.setRequestProperty(AppStatics.api_KEY_NAME,AppStatics.api_KEY_VALUE);
							con.setConnectTimeout(AppStatics.api_TIME_OUT);
							
							con.setRequestProperty("Content-Type","application/json");
							con.setRequestProperty("Accept","application/json");
							con.setRequestProperty("X-ApplicationId","aero.developer.beacons.BeaconTrac.zz.BeaconTrac");
							con.setRequestProperty("X-DeviceTypeVersion",android.os.Build.MODEL+"-"+Build.VERSION.RELEASE);
							
							if(context!=null){

								try {
									PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
									String version = pInfo.versionName;
									int code= pInfo.versionCode;
									con.setRequestProperty("X-ApplicationVersion",version+"-"+code);

	
								} catch (NameNotFoundException e) {
									e.printStackTrace();
								}
							}
							// START If you want to remove htaccess
							// authentication from this project comment this
							// section

							headers = con.getHeaderFields();
							responseCode = con.getResponseCode();
							if (responseCode == HttpStatus.SC_OK) {

								if (headers != null) {
									List<String> contentEncodingHeaders = headers
											.get("Content-Encoding");

									boolean gzipped = false;
									if (contentEncodingHeaders != null) {
										for (int g = 0; g < contentEncodingHeaders
												.size(); g++) {
											if (contentEncodingHeaders.get(g) != null
													&& contentEncodingHeaders
															.get(g)
															.equalsIgnoreCase(
																	"gzip")) {

												gzipped = true;
												InputStream is = con
														.getInputStream();

												GZIPInputStream instream = new GZIPInputStream(
														is);
												in = new BufferedReader(
														new InputStreamReader(
																instream));
											}
										}
									}

									if (gzipped == false) {
										in = new BufferedReader(
												new InputStreamReader(
														con.getInputStream()));
									}
								}

								if (in != null) {
									while ((str = in.readLine()) != null) {
										buff.append(str);
									}

									ret.content = buff.toString();
								}
							}
						} else {
							HttpURLConnection con = (HttpURLConnection) url
									.openConnection();

							con.setRequestProperty(AppStatics.api_KEY_NAME,
									AppStatics.api_KEY_VALUE);
							con.setConnectTimeout(AppStatics.api_TIME_OUT);

							headers = con.getHeaderFields();
							responseCode = con.getResponseCode();
							if (responseCode == HttpStatus.SC_OK) {

								headers = con.getHeaderFields();
								in = new BufferedReader(new InputStreamReader(
										con.getInputStream()));
								if (in != null) {
									while ((str = in.readLine()) != null) {
										buff.append(str);
									}
									ret.content = buff.toString();
								}

							}

						}
						if (responseCode == HttpStatus.SC_OK && headers != null
								&& ret != null) {
							Set<Entry<String, List<String>>> hKeys = headers
									.entrySet();
							for (Iterator<Entry<String, List<String>>> i = hKeys
									.iterator(); i.hasNext();) {
								Entry<String, List<String>> m = i.next();
								if (ret.headers != null && m.getKey() != null) {
									ret.headers.put(m.getKey(), m.getValue()
											.toString());

									if (m.getKey().equals("set-cookie"))
										ret.cookies.put(m.getKey(), m
												.getValue().toString());
								}
							}
						}
					} catch (HttpResponseException e) {
						// ConnectionErrorString = e.getMessage();
						e.printStackTrace();
					} catch (HttpHostConnectException e) {
						// ConnectionErrorString = e.getMessage();
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						// ConnectionErrorString = e.getMessage();
						e.printStackTrace();
					} catch (IOException e) {
						// ConnectionErrorString = e.getMessage();
						e.printStackTrace();
					}

					if (responseCode == HttpStatus.SC_OK && ret != null
							&& ret.content != null
							&& !ret.content.trim().equalsIgnoreCase("")) {
						return ret;
					}

					tries++;
				}
			} catch (OutOfMemoryError e) {
				// ConnectionErrorString = e.getMessage();
				e.printStackTrace();
			}
		}

		if (responseCode != HttpStatus.SC_OK || connected == false) {
			// String ConnectionErrorString = responseCode;
			String t = responseCode + "";
			RequestFail rf = new RequestFail();
			if (connected == false || responseCode == 599
					|| responseCode == 598 || responseCode == 504) {
				rf = new RequestFail(-2, "No_Internet",
						"Internet Connection Problem", false, true);
			} else if (responseCode == 503) {
				rf = new RequestFail(-3, "SERVICE_NOT_AVAILABLE", t, false,
						true);
			} else {
				rf = new RequestFail(responseCode, "RESPONSE_CODE_NOT_OK", t,
						false, true);
			}
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			String res = gson.toJson(rf);
			System.out.println("Internet Request Made UpResponse: " + res);
			ret.content = res;
			return ret;
		}

		return null;
	}

	/**
	 * HTTP post request
	 * 
	 * @param sUrl
	 * @param ht
	 * @return
	 * @throws Exception
	 */
	public static HttpData post(String sUrl, Hashtable<String, String> ht)
			throws Exception {
		if (sUrl != null)
			sUrl = sUrl.replaceAll(" ", "%20");
		String key;
		StringBuffer data = new StringBuffer();
		Enumeration<String> keys = ht.keys();
		while (keys.hasMoreElements()) {
			key = keys.nextElement();
			data.append(URLEncoder.encode(key, "UTF-8"));
			data.append("=");
			data.append(URLEncoder.encode(ht.get(key), "UTF-8"));
			data.append("&amp;");
		}
		return HttpRequest.postHttpStringData(sUrl, data.toString());
	}

	/**
	 * HTTP post request
	 * 
	 * @param sUrl
	 * @param data
	 * @return
	 */
	public static HttpData postHttpStringData(String sUrl, String data) {
		if (sUrl != null)
			sUrl = sUrl.replaceAll(" ", "%20");
		StringBuffer ret = new StringBuffer();
		HttpData dat = new HttpData();
		String header;
		OutputStreamWriter wr = null;
		BufferedReader rd = null;
		int responseCode = 0;
		boolean connected = isConnected();
		try {
			// Send data
			URL url = new URL(sUrl);
			if (url != null)
				Log.e("URL", "url: " + url.toString());
			trustEveryone(sUrl);
			URLConnection conn = url.openConnection();

			conn.setRequestProperty(AppStatics.api_KEY_NAME,
					AppStatics.api_KEY_VALUE);
			conn.setConnectTimeout(AppStatics.api_TIME_OUT);

			conn.setDoOutput(true);

			if (conn != null) {
				wr = new OutputStreamWriter(conn.getOutputStream());
				if (data != null && data.length() > 0)
					wr.write(data);
				wr.flush();

				rd = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				String line;
				while ((line = rd.readLine()) != null) {
					ret.append(line);
				}

			}
		} catch (FileNotFoundException e) {
			if (e != null) {
				e.printStackTrace();
				Log.e("ERROR",
						"ERROR IN CODE - FileNotFoundException: "
								+ e.getMessage());
			}

			if (responseCode != HttpStatus.SC_OK || connected == false) {
				// String ConnectionErrorString = responseCode;
				String t = responseCode + "";
				RequestFail rf = new RequestFail();
				if (connected == false || responseCode == 599
						|| responseCode == 598 || responseCode == 504) {
					rf = new RequestFail(-2, "No_Internet",
							"Internet Connection Problem", false, true);
				} else if (responseCode == 503) {
					rf = new RequestFail(-3, "SERVICE_NOT_AVAILABLE", t, false,
							true);
				} else {
					rf = new RequestFail(-3, "SERVICE_NOT_AVAILABLE", t, false,
							true);
				}
				Gson gson = new GsonBuilder().disableHtmlEscaping().create();
				String res = gson.toJson(rf);
				System.out.println("Internet Request Made UpResponse: " + res);
				dat.content = res;
				return dat;
			}

		} catch (Exception e) {
			// DisplayToastNoConnection();
			if (e != null) {
				e.printStackTrace();
				Log.e("ERROR", "ERROR IN CODE: " + e.getMessage());
			}
		} finally {
			try {
				if (wr != null)
					wr.close();
				if (rd != null)
					rd.close();
			} catch (IOException e) {
				if (e != null) {
					e.printStackTrace();
				}
			}

		}

		dat.content = (ret != null) ? ret.toString() : "";
		return dat;
	}

	/**
	 * Post request (upload files)
	 * 
	 * @param sUrl
	 * @param files
	 * @return HttpData
	 */
	public static HttpData post(String sUrl, ArrayList<File> files) {
		if (sUrl != null)
			sUrl = sUrl.replaceAll(" ", "%20");
		Hashtable<String, String> ht = new Hashtable<String, String>();
		return HttpRequest.post(sUrl, ht, files, true);
	}

	public static HttpData post(String sUrl, File file) {
		if (sUrl != null)
			sUrl = sUrl.replaceAll(" ", "%20");
		Hashtable<String, String> ht = new Hashtable<String, String>();
		return HttpRequest.post(sUrl, ht, file);
	}

	/**
	 * Post request (upload files)
	 * 
	 * @param sUrl
	 * @param params
	 *            Form data
	 * @param files
	 * @return
	 */
	public static HttpData post(String sUrl, Hashtable<String, String> params,
			ArrayList<File> files, boolean noFiles) {
		if (sUrl != null)
			sUrl = sUrl.replaceAll(" ", "%20");
		HttpData ret = new HttpData();
		try {
			String boundary = "*****************************************";
			String newLine = "\r\n";
			int bytesAvailable;
			int bufferSize;
			int maxBufferSize = 4096;
			int bytesRead;

			URL url = new URL(sUrl);
			trustEveryone(sUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestProperty(AppStatics.api_KEY_NAME,
					AppStatics.api_KEY_VALUE);

			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setRequestMethod("POST");
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			DataOutputStream dos = new DataOutputStream(con.getOutputStream());

			// dos.writeChars(params);

			if (noFiles == false) {
				// upload files
				for (int i = 0; files != null && i < files.size(); i++) {
					FileInputStream fis = new FileInputStream(files.get(i));
					dos.writeBytes("--" + boundary + newLine);
					dos.writeBytes("Content-Disposition: form-data; "
							+ "name=file_" + i + "\";filename=\""
							+ files.get(i).getPath() + "\"" + newLine + newLine);
					bytesAvailable = fis.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					byte[] buffer = new byte[bufferSize];
					bytesRead = fis.read(buffer, 0, bufferSize);
					while (bytesRead > 0) {
						dos.write(buffer, 0, bufferSize);
						bytesAvailable = fis.available();
						bufferSize = Math.min(bytesAvailable, maxBufferSize);
						bytesRead = fis.read(buffer, 0, bufferSize);
					}
					dos.writeBytes(newLine);
					dos.writeBytes("--" + boundary + "--" + newLine);
					fis.close();
				}
			}
			// Now write the data
			if (params == null) {
				params = new Hashtable<String, String>();
			}
			Enumeration<String> keys = params.keys();
			String key, val;
			while (keys.hasMoreElements()) {
				key = keys.nextElement().toString();
				val = params.get(key);
				dos.writeBytes("--" + boundary + newLine);
				dos.writeBytes("Content-Disposition: form-data;name=\"" + key
						+ "\"" + newLine + newLine + val);
				dos.writeBytes(newLine);
				dos.writeBytes("--" + boundary + "--" + newLine);

			}
			dos.flush();

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				ret.content += line + "\r\n";
			}
			// get headers
			Map<String, List<String>> headers = con.getHeaderFields();
			Set<Entry<String, List<String>>> hKeys = headers.entrySet();
			for (Iterator<Entry<String, List<String>>> i = hKeys.iterator(); i
					.hasNext();) {
				Entry<String, List<String>> m = i.next();

				ret.headers.put(m.getKey(), m.getValue().toString());
				if (m.getKey().equals("set-cookie"))
					ret.cookies.put(m.getKey(), m.getValue().toString());
			}
			dos.close();
			rd.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * Post request (upload files)
	 * 
	 * @param sUrl
	 * @param params
	 *            Form data
	 * @param files
	 * @return
	 */
	public static HttpData post(String sUrl, Hashtable<String, String> params,
			File file) {
		if (sUrl != null)
			sUrl = sUrl.replaceAll(" ", "%20");
		String attachmentName = "photo";
		String attachmentFileName = "bitmap.bmp";
		String crlf = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****************************************";
		int maxBufferSize = 4096;
		int bytesAvailable;
		int bufferSize;
		int bytesRead;

		try {
			HttpURLConnection httpUrlConnection = null;
			URL url = new URL(sUrl);
			trustEveryone(sUrl);
			httpUrlConnection = (HttpURLConnection) url.openConnection();

			httpUrlConnection.setRequestProperty(AppStatics.api_KEY_NAME,
					AppStatics.api_KEY_VALUE);
			httpUrlConnection.setConnectTimeout(AppStatics.api_TIME_OUT);

			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setDoOutput(true);

			httpUrlConnection.setRequestMethod("POST");
			httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
			httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
			httpUrlConnection.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);

			DataOutputStream request = new DataOutputStream(
					httpUrlConnection.getOutputStream());
			request.writeBytes(twoHyphens + boundary + crlf);
			request.writeBytes("Content-Disposition: form-data; name=\""
					+ attachmentName + "\";filename=\"" + file.getPath() + "\""
					+ crlf);
			request.writeBytes(crlf);

			FileInputStream fis = new FileInputStream(file);
			request.writeBytes("--" + boundary + crlf);
			request.writeBytes("Content-Disposition: form-data; "
					+ "name=photo" + "\";filename=\"" + file.getPath() + "\""
					+ crlf + crlf);
			bytesAvailable = fis.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			byte[] buffer = new byte[bufferSize];
			bytesRead = fis.read(buffer, 0, bufferSize);
			while (bytesRead > 0) {
				request.write(buffer, 0, bufferSize);
				bytesAvailable = fis.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fis.read(buffer, 0, bufferSize);
			}
			request.writeBytes(crlf);
			request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);

			fis.close();
			request.flush();

			// Now write the data

			Enumeration<String> keys = params.keys();
			String key, val;
			while (keys.hasMoreElements()) {
				key = keys.nextElement().toString();
				val = params.get(key);
				request.writeBytes("--" + boundary + crlf);
				request.writeBytes("Content-Disposition: form-data;name=\""
						+ key + "\"" + crlf + crlf + val);
				request.writeBytes(crlf);
				request.writeBytes("--" + boundary + "--" + crlf);

			}
			request.flush();

			HttpData ret = new HttpData();
			InputStream responseStream = new BufferedInputStream(
					httpUrlConnection.getInputStream());

			BufferedReader responseStreamReader = new BufferedReader(
					new InputStreamReader(responseStream));
			String line = "";
			StringBuilder stringBuilder = new StringBuilder();
			while ((line = responseStreamReader.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}
			responseStreamReader.close();

			String response = stringBuilder.toString();
			ret.content = response;

			request.close();
			return ret;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String post(String sUrl, List<NameValuePair> nameValuePairs) {
		if (sUrl != null)
			sUrl = sUrl.replaceAll(" ", "%20");
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(sUrl);
		String Response = null;
		trustEveryone(sUrl);
		try {
			MultipartEntity entity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			for (int index = 0; index < nameValuePairs.size(); index++) {
				if (nameValuePairs.get(index).getName()
						.equalsIgnoreCase("photo")) {
					// If the key equals to "image", we use FileBody to transfer
					// the data
					entity.addPart(nameValuePairs.get(index).getName(),
							new FileBody(new File(nameValuePairs.get(index)
									.getValue())));
				} else {
					// Normal string data
					entity.addPart(
							nameValuePairs.get(index).getName(),
							new StringBody(nameValuePairs.get(index).getValue()));
				}
			}

			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost, localContext);

			Response = EntityUtils.toString(response.getEntity());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response;
	}

	public static HttpData postHttps(String sUrl,
			List<NameValuePair> nameValuePairs) {
		if (sUrl != null)
			sUrl = sUrl.replaceAll(" ", "%20");
		HttpData httpData = new HttpData();
		trustEveryone(sUrl);
		HttpClient httpClient = HttpUtils.getNewHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(sUrl);
		String Response = null;
		try {
			MultipartEntity entity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);

			for (int index = 0; index < nameValuePairs.size(); index++) {
				if (nameValuePairs.get(index).getName()
						.equalsIgnoreCase("photo")) {
					// If the key equals to "image", we use FileBody to transfer
					// the data
					entity.addPart(nameValuePairs.get(index).getName(),
							new FileBody(new File(nameValuePairs.get(index)
									.getValue())));
				} else {
					// Normal string data
					entity.addPart(
							nameValuePairs.get(index).getName(),
							new StringBody(nameValuePairs.get(index).getValue()));
				}
			}

			httpPost.setEntity(entity);

			trustEveryone(sUrl);
			HttpResponse response = httpClient.execute(httpPost, localContext);
			Response = EntityUtils.toString(response.getEntity());

		} catch (IOException e) {
			e.printStackTrace();
		}
		httpData.content = Response;
		return httpData;
	}

	/**
	 * Checks if the phone has network connection and if connected to a hotspot
	 * that has internet.
	 * 
	 * @param context
	 *            the context
	 * @return <code>true</code> if the phone is connected
	 */
	public static boolean isInternetAvailable(Context con) {
		boolean connected = isConnected();
		if (connected && con != null) {
			// connected = isConnected(con);
		}
		return connected;
	}

	public static boolean isConnected() {
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL("http://clients3.google.com/generate_204");
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setInstanceFollowRedirects(false);
			urlConnection.setConnectTimeout(AppStatics.api_TIME_OUT);
			urlConnection.setReadTimeout(10000);
			urlConnection.setUseCaches(false);
			urlConnection.getInputStream();
			return urlConnection.getResponseCode() == 204;
		} catch (IOException e) {
			System.out
					.println("Walled garden check - probably not a portal: exception "
							+ e);
			return false;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();

		}
	}

	/**
	 * Issue a POST request to the server.
	 * 
	 * @param endpoint
	 *            POST address.
	 * @param params
	 *            request parameters.
	 * 
	 * @throws IOException
	 *             propagated from POST.
	 */

	public HttpData putRequest(String endpoint, Map<String, String> params,
			String data) {
		if (endpoint != null)
			endpoint = endpoint.replaceAll(" ", "%20");
		HttpData httpData = new HttpData();
		trustEveryone(endpoint);
		URL url;
		try {
			System.out.println("putRequest: endpoint:" + endpoint);
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("invalid url: " + endpoint);
		}
		StringBuilder bodyBuilder = new StringBuilder();
		String body = "";
		if (params != null) {
			Iterator<Entry<String, String>> iterator = params.entrySet()
					.iterator();
			// constructs the POST body using the parameters
			while (iterator.hasNext()) {
				Entry<String, String> param = iterator.next();
				bodyBuilder.append(param.getKey()).append('=')
						.append(param.getValue());
				if (iterator.hasNext()) {
					bodyBuilder.append('&');
				}
			}
			body = bodyBuilder.toString();
		} else if (data != null) {
			bodyBuilder.append(data);
			body = bodyBuilder.toString();
		}
		System.out.println("putRequest: body:" + body);
		System.out.println("putRequest: url:" + url);
		byte[] bytes = body.getBytes();
		// HttpURLConnection conn = null;
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			// conn = (HttpURLConnection) url.openConnection();

			conn.setRequestProperty(AppStatics.api_KEY_NAME,
					AppStatics.api_KEY_VALUE);
			conn.setConnectTimeout(AppStatics.api_TIME_OUT);

			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setFixedLengthStreamingMode(bytes.length);
			conn.setRequestMethod("PUT");

			if (conn != null && data != null && data.length() > 0) {
				OutputStreamWriter wr;
				wr = new OutputStreamWriter(conn.getOutputStream());
				if (data != null && data.length() > 0)
					wr.write(data);
				wr.flush();
			}
			// handle the response
			int status = conn.getResponseCode();
			if (status != 200) {
				return null;
			}
			System.out.println("Put Status: " + status);
			InputStreamReader isr = new InputStreamReader(conn.getInputStream());
			String str = "";
			BufferedReader in = new BufferedReader(isr);
			StringBuffer buff = new StringBuffer();
			while ((str = in.readLine()) != null) {
				buff.append(str);
			}
			httpData.content = buff.toString();
		} catch (IOException e) {
			if (e != null)
				e.printStackTrace();
		} catch (Exception e) {
			if (e != null)
				e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		return httpData;

	}

}