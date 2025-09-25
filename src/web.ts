import { WebPlugin } from '@capacitor/core';

import type { CapacitorBluetoothSerialPlugin, Device } from './definitions';

export class CapacitorBluetoothSerialWeb extends WebPlugin implements CapacitorBluetoothSerialPlugin {
  async listDevices(options: void): Promise<{devices: Device[]}> {
    throw new Error('Method not implemented.');
  }
  async checkAndRequestBluetoothPermission(options: void): Promise<void> {
    throw new Error('Method not implemented.');
  }
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
