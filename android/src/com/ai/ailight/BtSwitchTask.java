package com.ai.ailight;

import java.io.IOException;
import java.io.InputStream;
//import java.io.OutputStream;

import android.os.AsyncTask;



public class BtSwitchTask extends AsyncTask<BluetoothUnit, Void, BluetoothUnit>{

	String msg;
	

	@Override
	protected void onPostExecute(BluetoothUnit result) {
         result.mStatusLabel.setText(msg);
	}


	@Override
	protected BluetoothUnit doInBackground(BluetoothUnit... params) {
		BluetoothUnit bu = params[0];
		if (bu.mDevice == null || bu.mBtAdapter == null || bu.mSocket == null)
			return null;   // wrong parameter
	 
	        InputStream inStream = null;
	        //OutputStream outStream = null;

	  
	        try {
			    inStream = bu.mSocket.getInputStream();
		        //outStream = bu.mSocket.getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        byte[] buffer = new byte[1024];  // buffer store for the stream
//	        int bytes; // bytes returned from read()
//	 
//	        // Keep listening to the InputStream until an exception occurs
//	        while (true) {
//	            try {
//	                // Read from the InputStream
//	                bytes = inStream.read(buffer);
//	            } catch (IOException e) {
//	                break;
//	            }
//	        }

	        try {
				inStream.read(buffer);
				msg = new String(buffer, "UTF-8");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return bu;
	}

}

