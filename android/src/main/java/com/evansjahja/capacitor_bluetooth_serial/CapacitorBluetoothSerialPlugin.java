package com.evansjahja.capacitor_bluetooth_serial;

import static com.getcapacitor.PluginMethod.RETURN_CALLBACK;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;

import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@CapacitorPlugin(name = "CapacitorBluetoothSerial",
        permissions =   @Permission(
                alias = "bluetooth",
                strings = {
                        android.Manifest.permission.BLUETOOTH,
                        android.Manifest.permission.BLUETOOTH_ADMIN,
                        android.Manifest.permission.BLUETOOTH_CONNECT // For Android 12+
                }
        )
)
public class CapacitorBluetoothSerialPlugin extends Plugin {
    private static final UUID SERIAL_PORT_PORFILE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // SPP UUID

    private static final String TAG = "CapacitorBluetoothSerialPlugin";
    private static final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    @Nullable PluginCall onDataCallback;
    @Nullable Future<?> readerLoop;
    @Nullable BluetoothSocket bluetoothSocket;

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private BluetoothDevice bluetoothDevice;

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    @PluginMethod()
    public void listDevices(PluginCall call) {
        List<JSObject> devices = bluetoothAdapter.getBondedDevices().stream()
                .filter(CapacitorBluetoothSerialPlugin::hasSerial)
                .map(CapacitorBluetoothSerialPlugin::deviceToJS)
                .collect(Collectors.toList());

        JSObject o = new JSObject();
        o.put("devices", new JSArray(devices));
        call.resolve(o);

    }

    @PluginMethod()
    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    public void connect(PluginCall call) {
        String address = call.getString("address");
        if (address == null) {
            call.reject("missing required parameter: address");
            return;
        }

        Optional<BluetoothDevice> bluetoothDeviceOptional = bluetoothAdapter.getBondedDevices().stream()
                .filter(CapacitorBluetoothSerialPlugin::hasSerial)
                .filter(d-> d.getAddress().equalsIgnoreCase(address))
                .findFirst();


        if (bluetoothDeviceOptional.isPresent()) {
            bluetoothDevice = bluetoothDeviceOptional.get();
        } else {
            call.reject("cannot find device with address " + address);
            return;
        }


        try {
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(SERIAL_PORT_PORFILE_UUID);
            Log.i(TAG, "connecting...");
            assert bluetoothSocket != null;
            bluetoothSocket.connect();
            Log.i(TAG, "connected");

            executor.scheduleWithFixedDelay(this::readAvailableData, 0, 100, TimeUnit.MILLISECONDS);


        } catch (IOException e) {
            call.reject("ioexception in connect", e);
        }
    }

    final int BUF_SIZE = 1024;
    byte[] readBuf = new byte[BUF_SIZE];
    void readAvailableData(){
        if (bluetoothSocket == null || onDataCallback == null) {
            return;
        }
        try {
            var is = bluetoothSocket.getInputStream();
            if (is.available() > 0) {
                JSObject o = new JSObject();
                int readCount =  is.read(readBuf, 0, Math.min(is.available(), BUF_SIZE));
                var a = Arrays.copyOf(readBuf, readCount);

                o.put("data", new JSArray(a));
                onDataCallback.resolve(o);

            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

    }




    @PluginMethod
    public void checkAndRequestBluetoothPermission(PluginCall call) {
        // Check the current state of the "bluetooth" permission alias
        PermissionState bluetoothPermState = getPermissionState("bluetooth");

        Log.i(TAG, "Permission is: " + bluetoothPermState.toString());

        if (bluetoothPermState == PermissionState.GRANTED) {
            // Permission is already granted. Proceed with your logic.
            call.resolve();
            return;
        }

        requestPermissionForAlias("bluetooth", call, "bluetoothPermsCallback");

    }

    @PluginMethod
    public void sendData(PluginCall call) throws JSONException, IOException {
        JSArray arr = call.getArray("data");
        List<Integer> intDataToSend = arr.toList();
        assert bluetoothSocket != null;
        var os = bluetoothSocket.getOutputStream();

        byte[] bytes = new byte[intDataToSend.size()];

        for (int i=0; i< intDataToSend.size(); i++) {
            bytes[i] = (byte) (int) (intDataToSend.get(i));
        }

        Log.i(TAG, "Would like to send " + bytesToHexString(bytes));

        os.write(bytes);


        call.resolve();


    }

    private static String bytesToHexString(byte[] bytes) {

        StringBuilder hexString = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            // Converts the byte to an integer (0-255) and then formats it as a
            // two-digit hexadecimal string, padding with a leading zero if necessary.
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    @PluginMethod(returnType = RETURN_CALLBACK)
    public void watchData(PluginCall call) {
        call.setKeepAlive(true);
        this.onDataCallback = call;
    }


    @PermissionCallback
    private void bluetoothPermsCallback(PluginCall call) {
        // This method is called after the user has responded to the permission dialog.
        PermissionState bluetoothPermState = getPermissionState("bluetooth");

        if (bluetoothPermState == PermissionState.GRANTED) {
            // User granted the permission.
            call.resolve();
        } else {
            call.reject("bluetooth permission rejected");
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    private static JSObject deviceToJS(BluetoothDevice bluetoothDevice) {
        JSObject o = new JSObject();
        o.put("name", bluetoothDevice.getName());
        o.put("address", bluetoothDevice.getAddress());
        return o;
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    private static boolean hasSerial(BluetoothDevice bluetoothDevice) {
        var uuids = bluetoothDevice.getUuids();
        for (var uuid : uuids) {
            if (uuid.getUuid().equals(SERIAL_PORT_PORFILE_UUID)) {
                return true;
            }
        }
        return false;
    }

}
