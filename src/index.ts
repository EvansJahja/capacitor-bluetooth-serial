import { registerPlugin } from '@capacitor/core';

import type { CapacitorBluetoothSerialPlugin } from './definitions';

const CapacitorBluetoothSerial = registerPlugin<CapacitorBluetoothSerialPlugin>('CapacitorBluetoothSerial', {
  web: () => import('./web').then((m) => new m.CapacitorBluetoothSerialWeb()),
});

export * from './definitions';
export { CapacitorBluetoothSerial };
