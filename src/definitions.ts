export interface CapacitorBluetoothSerialPlugin {
  /**
   * List available Bluetooth devices
   * @returns A promise that resolves with an array of devices
   */
  listDevices(options: void): Promise<{devices: Device[]}>;

  /**
   * Check and request Bluetooth permissions
   */
  checkAndRequestBluetoothPermission(options: void): Promise<void>;

  /**
   * Connect to a Bluetooth device
   * @param address The address of the device to connect to
   */
  connect(options: {address: string}): Promise<void>;

  /**
   * Register a listener for incoming data
   * @param onData A callback function that receives incoming data as an array of bytes
   */
  watchData(callback: WatchDataCallback): Promise<CallbackID>;

  /**
   * Send data to the connected Bluetooth device
   * @param data An array of bytes to send
   */
  sendData(options: {data: number[]}): Promise<void>;
}

export type CallbackID = string;

export type WatchDataCallback = (message: {"data": [number]} | null, err?: any) => void;

export interface Device {
  name: string;
  address: string;
}