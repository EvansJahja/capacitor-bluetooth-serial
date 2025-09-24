import { WebPlugin } from '@capacitor/core';

import type { CapacitorBluetoothSerialPlugin } from './definitions';

export class CapacitorBluetoothSerialWeb extends WebPlugin implements CapacitorBluetoothSerialPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
