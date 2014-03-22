package com.ai.ailight;

import java.io.IOException;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.widget.TextView;


public class BluetoothUnit {
	
    public BluetoothSocket mSocket = null;
    public BluetoothDevice mDevice = null;
    public BluetoothAdapter mBtAdapter = null;
    public TextView         mStatusLabel = null;

	
    public void Reset(){
    	if (mSocket != null){
    		try {
				mSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		mSocket = null;
    	}
    	mDevice = null;
    }
	
}



