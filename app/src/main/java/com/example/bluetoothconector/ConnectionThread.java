package com.example.bluetoothconector;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.UUID;
public class ConnectionThread extends Thread{

    private BluetoothDevice device = null;
    private BluetoothSocket socket = null;
    private static final UUID MYUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private Activity activity = null;

    public ConnectionThread(BluetoothDevice device, Activity activity){
        this.device = device;
        this.activity = activity;
        BluetoothSocket temp = null;
        try {
            if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
                return;

            temp = device.createInsecureRfcommSocketToServiceRecord(MYUUID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        socket = temp;
    }

    @Override
    public void run(){
        // Verificar permissões antes de tentar se conectar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.BLUETOOTH_SCAN, android.Manifest.permission.BLUETOOTH_CONNECT},
                        1);
                return;
            }
        }

        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();

        try {
            socket.connect();
            Log.d("ConnectionThread", "Conectado");
        } catch (IOException connectException) {
            connectException.printStackTrace();
            try {
                socket.close();
            } catch (IOException closeException) {
                closeException.printStackTrace();
            }
        }
    }
    public void sendMessage(String message) {
        if (socket != null && socket.isConnected()) {
            try {
                socket.getOutputStream().write(message.getBytes());
                Log.d("ConnectionThread", "Mensagem enviada: " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
