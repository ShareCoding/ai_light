package com.ai.ailight;

import java.io.IOException;
import java.util.UUID;

import android.os.AsyncTask;



public class ConnectBtTask extends AsyncTask<BluetoothUnit, Void, BluetoothUnit>{

	private static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
	
	
	private BluetoothUnit connectJob(BluetoothUnit bu){
		UUID uuid = UUID.fromString(SPP_UUID); 
		try {
			bu.mSocket = bu.mDevice.createRfcommSocketToServiceRecord(uuid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		// Cancel discovery because it will slow down the connection
        bu.mBtAdapter.cancelDiscovery();
		try {
			bu.mSocket.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				bu.mSocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			bu.mSocket = null;
		}
		return bu;
	}
	
	private BluetoothUnit disConnect(BluetoothUnit bu){
		if (bu.mSocket != null){
			try {
				bu.mSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bu.mSocket = null;
		}
		return bu;
	}
	
	@Override
	protected BluetoothUnit doInBackground(BluetoothUnit... params) {
		// TODO Auto-generated method stub
		BluetoothUnit bu = params[0];
		if (bu.mDevice == null || bu.mBtAdapter == null)
			return null;   // wrong parameter
		
		if (bu.mSocket == null){
			connectJob(bu);
		} else {
			disConnect(bu);
		}
		return bu;
	}


	@Override
	protected void onPostExecute(BluetoothUnit result) {
        if (result.mSocket != null){
		    result.mStatusLabel.setText(result.mDevice.getName() + "  connected");
        } else {
        	result.mStatusLabel.setText("Disconnected");
        }
	}
	
}

