package com.example.mito.getbeacon;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.util.Log;

/**
 * Created by mito on 2017/02/21.
 */

class IbeaconScanCallback extends ScanCallback{
    private final String TAG = "Beacon";

    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        super.onScanResult(callbackType, result);
        // スキャン結果が返ってきます
        // このメソッドかonBatchScanResultsのいずれかが呼び出されます。
        // 通常はこちらが呼び出されます。
        if (result == null){
            return;
        }
        BluetoothDevice bDevice = result.getDevice();
//        Log.d(TAG, bDevice.getName());

        ScanRecord record = result.getScanRecord();
        byte[] bytes = record != null ? record.getBytes() : new byte[0];
        Log.d(TAG, bDevice.getAddress());
        Log.d(TAG, bDevice.toString());
        Log.d(TAG, getUUID(bytes));
        Log.d(TAG, getMajor(bytes));
        Log.d(TAG, getMinor(bytes));
    }

    private String getUUID(byte[] scanRecord) {
        String uuid = IntToHex2(scanRecord[9] & 0xff)
                + IntToHex2(scanRecord[10] & 0xff)
                + IntToHex2(scanRecord[11] & 0xff)
                + IntToHex2(scanRecord[12] & 0xff)
                + "-"
                + IntToHex2(scanRecord[13] & 0xff)
                + IntToHex2(scanRecord[14] & 0xff)
                + "-"
                + IntToHex2(scanRecord[15] & 0xff)
                + IntToHex2(scanRecord[16] & 0xff)
                + "-"
                + IntToHex2(scanRecord[17] & 0xff)
                + IntToHex2(scanRecord[18] & 0xff)
                + "-"
                + IntToHex2(scanRecord[19] & 0xff)
                + IntToHex2(scanRecord[20] & 0xff)
                + IntToHex2(scanRecord[21] & 0xff)
                + IntToHex2(scanRecord[22] & 0xff)
                + IntToHex2(scanRecord[23] & 0xff)
                + IntToHex2(scanRecord[24] & 0xff);
        return uuid;
    }

    private String getMajor(byte[] scanRecord) {
        String hexMajor = IntToHex2(scanRecord[25] & 0xff) + IntToHex2(scanRecord[26] & 0xff);
        return String.valueOf(Integer.parseInt(hexMajor, 16));
    }

    private String getMinor(byte[] scanRecord) {
        String hexMinor = IntToHex2(scanRecord[27] & 0xff) + IntToHex2(scanRecord[28] & 0xff);
        return String.valueOf(Integer.parseInt(hexMinor, 16));
    }

    private String IntToHex2(int i) {
        char hex_2[]     = { Character.forDigit((i >> 4) & 0x0f, 16), Character.forDigit(i & 0x0f, 16) };
        String hex_2_str = new String(hex_2);
        return hex_2_str.toUpperCase();
    }


    @Override
    public void onScanFailed(int errorCode) {
        super.onScanFailed(errorCode);
        // エラーが発生するとこちらが呼び出されます
        String errorMessage = "";
        switch (errorCode) {
            case SCAN_FAILED_ALREADY_STARTED:
                errorMessage = "既にBLEスキャンを実行中です";
                break;
            case SCAN_FAILED_APPLICATION_REGISTRATION_FAILED:
                errorMessage = "BLEスキャンを開始できませんでした";
                break;
            case SCAN_FAILED_FEATURE_UNSUPPORTED:
                errorMessage = "BLEの検索をサポートしていません。";
                break;
            case SCAN_FAILED_INTERNAL_ERROR:
                errorMessage = "内部エラーが発生しました";
                break;
        }
        Log.d("ERROR", errorMessage);
    }
}
