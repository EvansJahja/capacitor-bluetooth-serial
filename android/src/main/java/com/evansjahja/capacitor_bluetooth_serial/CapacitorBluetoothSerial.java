package com.evansjahja.capacitor_bluetooth_serial;

import com.getcapacitor.Logger;

public class CapacitorBluetoothSerial {

    public String echo(String value) {
        Logger.info("Echo", value);
        return value;
    }
}
