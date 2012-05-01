package mx.softlite.expotech.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

	/*
	* @return boolean return true if the application can access the internet
	*/
	public static boolean haveInternet(Context ctx){
		NetworkInfo info = ((ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE)).
			    getActiveNetworkInfo();
	        if (info==null || !info.isConnected()) {
	                return false;
	        }
	        if (info.isRoaming()) {
	                // here is the roaming option you can change it if you want to disable internet while roaming, just return false
	                return true;
	        }
	        return true;
	}
	
	/**
	 * Revisamos si un archivo esta guardado
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static boolean isFileSaved(Context context, String fileName){
		boolean flag = false;
		try {
			FileInputStream fin = context.openFileInput(fileName);
			flag = (fin != null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public static void saveFile(Context context, InputStream inputStream, String fileName) throws IOException{
		FileOutputStream output = null;
		try {
			output = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			
			int read;
	        byte[] data = new byte[1024];
	        while ((read = inputStream.read(data)) != -1)
	            output.write(data, 0, read);
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if (output != null)
	            output.close();
	        if (inputStream != null)
	            inputStream.close();
		}
	}
	
	public static InputStream getFileSaved(Context context, String fileName){
		InputStream inputStream = null;
		
		try {
			inputStream = context.openFileInput(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return inputStream;
	}
}
