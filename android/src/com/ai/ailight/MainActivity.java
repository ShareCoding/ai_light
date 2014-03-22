package com.ai.ailight;

import java.util.Set;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private DeviceListAdapter device_adapter = null;
	private ListView device_list = null;
	private Button   refresh_btn = null;
	private Button   connect_btn = null;
	private Button   switch_btn  = null;
	private TextView connected_status   = null;
	private TextView board = null;
	
	private BroadcastReceiver mReceiver = null;
	
	private BluetoothAdapter mBluetoothAdapter = null;
	
	private static final int kBtEnableReq   = 1;
	//private static final int kBtDiscoverReq = 2;
	
	
	private BluetoothUnit selected_bluetooth = new BluetoothUnit();
	
	
	private View previous = null;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        View v = getLayoutInflater().inflate(R.layout.activity_main, null);
        device_list = (ListView)v.findViewById(R.id.device_list);
        refresh_btn = (Button)v.findViewById(R.id.refresh);
        connect_btn = (Button)v.findViewById(R.id.connect_btn);
        switch_btn = (Button)v.findViewById(R.id.switch_button);
        connected_status = (TextView)v.findViewById(R.id.connected_status);
        board = (TextView)v.findViewById(R.id.board);
        selected_bluetooth.mStatusLabel = connected_status;
        device_adapter = new DeviceListAdapter(this);
        device_list.setAdapter(device_adapter);
        
        device_list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				if (previous != null){
					previous.setBackgroundColor(Color.TRANSPARENT);
				}
				previous = v;
				v.setSelected(true);
				selected_bluetooth.mDevice = (BluetoothDevice)device_adapter.getItem(pos);
				v.setBackgroundColor(Color.LTGRAY);
			}});
        
        
        mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // Add the name and address to an array adapter to show in a ListView
                    device_adapter.addDevice(device);
                    device_adapter.notifyDataSetChanged();
                }
            }
        };
        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
        
        // refresh in order to get bluetooth device list
        refresh_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
				if (mBluetoothAdapter == null) {
				    // Device does not support Bluetooth
					connected_status.setText("Bluetooth not supported");
					return ;
				}
				if (!mBluetoothAdapter.isEnabled()) {
				    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				    startActivityForResult(enableBtIntent, kBtEnableReq);
				}
				device_adapter.clearDevice();
				Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
				// If there are paired devices
				if (pairedDevices.size() > 0) {
				    // Loop through paired devices
				    for (BluetoothDevice device : pairedDevices) {
				        // Add the name and address to an array adapter to show in a ListView
				    	device_adapter.addDevice(device);
				    	device_adapter.notifyDataSetChanged();
				    }
				}
				
				if (mBluetoothAdapter.isDiscovering()){
					mBluetoothAdapter.cancelDiscovery();
				}
				
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND); 
                registerReceiver(mReceiver, filter); 
                mBluetoothAdapter.startDiscovery();
			}});
        
        connect_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				selected_bluetooth.mBtAdapter = mBluetoothAdapter;
				selected_bluetooth.mStatusLabel = connected_status;
				new ConnectBtTask().execute(selected_bluetooth);
			}});
        
        
        
        // to switch light on/off
        switch_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selected_bluetooth.mBtAdapter = mBluetoothAdapter;
				selected_bluetooth.mStatusLabel = board;
				new BtSwitchTask().execute(selected_bluetooth);
			}});
        
        setContentView(v);
    }


    //private void 
    
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == kBtEnableReq){
			if (resultCode == RESULT_OK){  // Bluetooth had been enable
				
			} else {
				connected_status.setText("Bluetooth had been disable");
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
