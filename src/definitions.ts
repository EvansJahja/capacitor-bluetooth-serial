export interface CapacitorBluetoothSerialPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
