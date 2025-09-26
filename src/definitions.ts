export interface CapacitorBluetoothSerialPlugin {
  listDevices(options: void): Promise<{devices: Device[]}>;
  checkAndRequestBluetoothPermission(options: void): Promise<void>;
  connect(options: {address: string}): Promise<void>;

  registerListener(options: {onData: (data: [number]) => void}): Promise<void>;

  sendData(options: {data: number[]}): Promise<void>;
}

export interface Device {
  name: string;
  address: string;
}