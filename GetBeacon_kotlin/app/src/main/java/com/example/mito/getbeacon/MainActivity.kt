package com.example.mito.getbeacon_kotlin

import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.example.mito.getbeacon.R

class MainActivity : AppCompatActivity() {
    var mBLEScanner: BluetoothLeScanner? = null
    var scanCallback: IbeaconScanCallback? = null
    var flag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bluetoothの生成（ここは5.0以前も一緒）
        val bManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bAdapter = bManager.adapter

        // Bluetoothサポートチェック
        val isBluetoothSupported = bAdapter != null
        if (!isBluetoothSupported) {
            // 非サポート時の処理をしてください
            return
        }

        // Bluetoothオンかチェック
        if (!bAdapter!!.isEnabled) {
            // オフ時の処理をしてください
            return
        }

        // BLE開始
        mBLEScanner = bAdapter.bluetoothLeScanner
        scanCallback = IbeaconScanCallback()


        val btn = findViewById(R.id.button) as Button
        btn.setOnClickListener {
            if (flag) {
                mBLEScanner!!.startScan(scanCallback)
            } else {
                mBLEScanner!!.stopScan(scanCallback)
            }
            flag = !flag
        }
    }
}
