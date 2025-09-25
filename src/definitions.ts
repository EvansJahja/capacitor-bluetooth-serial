export interface CapacitorBluetoothSerialPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  listDevices(options: void): Promise<{devices: Device[]}>;
  checkAndRequestBluetoothPermission(options: void): Promise<void>;
}

export interface Device {
  name: string;
  address: string;
}