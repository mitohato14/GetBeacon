package com.example.mito.getbeacon_kotlin

import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.util.Log

/**
 * Created by mito on 2017/02/21.
 */

class IbeaconScanCallback : ScanCallback() {
    internal val TAG = "Beacon"

    override fun onScanResult(callbackType: Int, result: ScanResult?) {
        super.onScanResult(callbackType, result)
        // スキャン結果が返ってきます
        // このメソッドかonBatchScanResultsのいずれかが呼び出されます。
        // 通常はこちらが呼び出されます。
        if (result == null) {
            return
        }
        val bDevice = result.device
        Log.d(TAG + "hoge", bDevice.address)
        val byte = result.scanRecord.bytes
        Log.d(TAG, getUUID(byte))
        Log.d(TAG, getMajor(byte))
        Log.d(TAG, getMinor(byte))
    }

    private fun getUUID(scanRecord: ByteArray): String {
        val uuid = IntToHex2(scanRecord[9].toInt() and 0xff) +
                IntToHex2(scanRecord[10].toInt() and 0xff) +
                IntToHex2(scanRecord[11].toInt() and 0xff) +
                IntToHex2(scanRecord[12].toInt() and 0xff) +
                "-" +
                IntToHex2(scanRecord[13].toInt() and 0xff) +
                IntToHex2(scanRecord[14].toInt() and 0xff) +
                "-" +
                IntToHex2(scanRecord[15].toInt() and 0xff) +
                IntToHex2(scanRecord[16].toInt() and 0xff) +
                "-" +
                IntToHex2(scanRecord[17].toInt() and 0xff) +
                IntToHex2(scanRecord[18].toInt() and 0xff) +
                "-" +
                IntToHex2(scanRecord[19].toInt() and 0xff) +
                IntToHex2(scanRecord[20].toInt() and 0xff) +
                IntToHex2(scanRecord[21].toInt() and 0xff) +
                IntToHex2(scanRecord[22].toInt() and 0xff) +
                IntToHex2(scanRecord[23].toInt() and 0xff) +
                IntToHex2(scanRecord[24].toInt() and 0xff)

        return uuid
    }

    private fun getMajor(scanRecord: ByteArray): String {
        val hexMajor = IntToHex2(scanRecord[25].toInt() and 0xff) + IntToHex2(scanRecord[26].toInt() and 0xff)
        return Integer.parseInt(hexMajor, 16).toString()
    }

    private fun getMinor(scanRecord: ByteArray): String {
        val hexMinor = IntToHex2(scanRecord[27].toInt() and 0xff) + IntToHex2(scanRecord[28].toInt() and 0xff)
        return Integer.parseInt(hexMinor, 16).toString()
    }

    private fun IntToHex2(i: Int): String {
        val hex_2 = charArrayOf(Character.forDigit(i shr 4 and 0x0f, 16), Character.forDigit(i and 0x0f, 16))
        val hex_2_str = String(hex_2)
        return hex_2_str.toUpperCase()
    }


    override fun onScanFailed(errorCode: Int) {
        super.onScanFailed(errorCode)
        // エラーが発生するとこちらが呼び出されます
        var errorMessage = when (errorCode) {
            ScanCallback.SCAN_FAILED_ALREADY_STARTED -> "既にBLEスキャンを実行中です"
            ScanCallback.SCAN_FAILED_APPLICATION_REGISTRATION_FAILED -> "BLEスキャンを開始できませんでした"
            ScanCallback.SCAN_FAILED_FEATURE_UNSUPPORTED -> "BLEの検索をサポートしていません。"
            ScanCallback.SCAN_FAILED_INTERNAL_ERROR -> "内部エラーが発生しました"
            else -> ""
        }
        Log.d("ERROR", errorMessage)
    }
}
