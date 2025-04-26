package com.example.bluetoothconector;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner spinDetected = null;
    Button btnPair = null;
    EditText txtMsg = null;
    Button btnSend = null;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    List<String> lstDevices = new ArrayList<>();
    ArrayAdapter<String> adapter;
    Button btnFind = null;
    Button btnConnect = null;
    ConnectionThread connectionThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstDevices.add("");
        spinDetected = (Spinner) findViewById(R.id.spinnerDetected);
        btnPair = (Button) findViewById(R.id.buttonPair);
        btnFind = (Button) findViewById(R.id.buttonFind);
        btnConnect = (Button) findViewById(R.id.buttonConnect);
        btnSend = (Button) findViewById(R.id.buttonSend);
        txtMsg = (EditText) findViewById(R.id.editTextMessage);

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }

        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        bluetoothAdapter.startDiscovery();
        Toast.makeText(MainActivity.this, "Scanning for devices...", Toast.LENGTH_SHORT).show();
        registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lstDevices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinDetected.setAdapter(adapter);

        btnPair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinDetected.getSelectedItem().toString().isEmpty())
                    Toast.makeText(MainActivity.this, "Select a device first!", Toast.LENGTH_SHORT).show();

                String device = spinDetected.getSelectedItem().toString();
                String address = device.split("\\|")[1].trim();
                String name = device.split("\\|")[0].trim();
                BluetoothDevice deviceToPair = bluetoothAdapter.getRemoteDevice(address);

                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
                    return;

                boolean paired = deviceToPair.createBond();
                if (paired)
                    Toast.makeText(MainActivity.this, name + " paired successfully!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Error pairing with " + name, Toast.LENGTH_SHORT).show();
            }
        });

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.cancelDiscovery();
                }
                bluetoothAdapter.startDiscovery();
                Toast.makeText(MainActivity.this, "Scanning", Toast.LENGTH_SHORT).show();
            }
        });

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String device = spinDetected.getSelectedItem().toString();
                String address = device.split("\\|")[1].trim();

                BluetoothDevice pairedDevice = bluetoothAdapter.getRemoteDevice(address);

                connectionThread = new ConnectionThread(pairedDevice, MainActivity.this);
                connectionThread.start();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtMsg.getText().length() > 0) {
                    if (connectionThread != null) {
                        String message = txtMsg.getText().toString();
                        connectionThread.sendMessage(message);
                        txtMsg.setText("");
                    }
                }
            }
        });
    }


    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent == null || intent.getAction() == null)
                    return;
                String action = intent.getAction();

                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (device != null) {
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        String name = device.getName() == null ? "Unknown" : device.getName();
                        String deviceInfo = name + " | " + device.getAddress();

                        if (!lstDevices.contains(deviceInfo)) {
                            lstDevices.add(deviceInfo);
                            if (adapter != null)
                                adapter.notifyDataSetChanged();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

}