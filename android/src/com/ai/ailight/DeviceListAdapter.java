package com.ai.ailight;

import java.util.ArrayList;
import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DeviceListAdapter extends BaseAdapter{

	private List<BluetoothDevice> device_list = new ArrayList<BluetoothDevice>();
	
	private Context mContext;
	
	DeviceListAdapter(Context context){
		mContext = context;
	}
	
	
	public void addDevice(BluetoothDevice bd){
		if (bd == null)
			return;
		device_list.add(bd);
	}
	
	public void clearDevice(){
		device_list.clear();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return device_list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		TextView tv = new TextView(mContext);
		tv.setText("Bluetooth device");
		return tv;
	}
	
	
	
}



