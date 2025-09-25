export interface CapacitorBluetoothSerialPlugin {
  listDevices(options: void): Promise<{devices: Device[]}>;
  checkAndRequestBluetoothPermission(options: void): Promise<void>;
}

export interface Device {
  name: string;
  address: string;
}