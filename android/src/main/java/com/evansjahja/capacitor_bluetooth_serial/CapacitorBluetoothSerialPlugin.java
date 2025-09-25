package com.evansjahja.capacitor_bluetooth_serial;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

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

import java.util.List;
import java.util.UUID;
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


    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", value);
        call.resolve(ret);
    }

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

}
