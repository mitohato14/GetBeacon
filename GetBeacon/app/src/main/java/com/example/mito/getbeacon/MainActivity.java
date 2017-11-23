package com.example.mito.getbeacon;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    BluetoothLeScanner mBLEScanner;
    IbeaconScanCallback scanCallback;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bluetoothの生成（ここは5.0以前も一緒）
        final BluetoothManager bManager = (BluetoothManager)getSystemService(BLUETOOTH_SERVICE);
        final BluetoothAdapter bAdapter = bManager.getAdapter();

        // Bluetoothサポートチェック
        final boolean isBluetoothSupported = bAdapter != null;
        if (!isBluetoothSupported) {
            // 非サポート時の処理をしてください
            return;
        }

        // Bluetoothオンかチェック
        if (!bAdapter.isEnabled()) {
            // オフ時の処理をしてください
            return;
        }

        // BLE開始
        mBLEScanner = bAdapter.getBluetoothLeScanner();
        scanCallback = new IbeaconScanCallback();


        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    mBLEScanner.startScan(scanCallback);
                }
                else {
                    mBLEScanner.stopScan(scanCallback);
                }
                flag = !flag;
            }
        });
    }
}
