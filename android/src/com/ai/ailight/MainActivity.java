package com.ai.ailight;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private DeviceListAdapter device_adapter = null;
	private ListView device_list = null;
	private Button   refresh_btn = null;
	private Button   switch_btn  = null;
	private TextView connected_status   = null;
	
	private static final int kBtEnableReq = 1;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        View v = getLayoutInflater().inflate(R.layout.activity_main, null);
        device_list = (ListView)v.findViewById(R.id.device_list);
        refresh_btn = (Button)v.findViewById(R.id.refresh);
        switch_btn = (Button)v.findViewById(R.id.switch_button);
        connected_status = (TextView)v.findViewById(R.id.connected_status);
        
        device_adapter = new DeviceListAdapter(this);
        device_list.setAdapter(device_adapter);
        
        // refresh in order to get bluetooth device list
        refresh_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
				if (mBluetoothAdapter == null) {
				    // Device does not support Bluetooth
					connected_status.setText("Bluetooth not supported");
					return ;
				}
				if (!mBluetoothAdapter.isEnabled()) {
				    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				    startActivityForResult(enableBtIntent, kBtEnableReq);
				}
				
			}});
        
        // to switch light on/off
        switch_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}});
        
        setContentView(v);
    }


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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
