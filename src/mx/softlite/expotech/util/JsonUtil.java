package mx.softlite.expotech.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class JsonUtil {

	public static JSONObject getJsonfromURL(Context context, String url, String fileName){
		InputStream data = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		try {
			if(Utils.isFileSaved(context, fileName)){
				data = Utils.getFileSaved(context, fileName);
			}
			else{
				URI uri = new URI(url);
				HttpGet method = new HttpGet(uri);
				HttpResponse response = httpClient.execute(method);
				Utils.saveFile(context, response.getEntity().getContent(), fileName);
				data = Utils.getFileSaved(context, fileName);
			}						
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject jArray = null;
		try {
			String strJson = convertStreamToString(data);
			jArray = new JSONObject(strJson);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jArray;
	}
	
	private static String convertStreamToString(InputStream is)
            throws IOException {
        /*
         * To convert the InputStream to String we use the
         * Reader.read(char[] buffer) method. We iterate until the
         * Reader return -1 which means there's no more data to
         * read. We use the StringWriter class to produce the string.
         */
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {        
            return "";
        }
    }
}
