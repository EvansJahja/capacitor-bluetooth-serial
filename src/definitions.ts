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
  registerListener(options: {onData: (data: [number]) => void}): Promise<void>;

  /**
   * Send data to the connected Bluetooth device
   * @param data An array of bytes to send
   */
  sendData(options: {data: number[]}): Promise<void>;
}

export interface Device {
  name: string;
  address: string;
}