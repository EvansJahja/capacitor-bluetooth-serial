import type { CapacitorBluetoothSerialPlugin, Device } from './definitions';

export class CapacitorBluetoothSerialWeb implements CapacitorBluetoothSerialPlugin {
    // Simulate a connected device
    isConnected: boolean = false;

    onDataCallback: ((data: number[]) => void) | null = null;
    constructor() {
        console.log('CapacitorBluetoothSerialWeb initialized');
        (async () => {
            while (true) {
                if (this.isConnected) {
                    const data = [Math.floor(Math.random() * 256), Math.floor(Math.random() * 256)];
                    this.onDataCallback?.(data);
                }
                // sleep
                await new Promise(resolve => setTimeout(resolve, 1000));
            }
        })();
    }
    sendData(options: { data: number[]; }): Promise<void> {
        console.log("Sending data:", options.data);
        return Promise.resolve();
    }


    registerListener(options: { onData: (data: number[]) => void; }): Promise<void> {
        this.onDataCallback = options.onData;
        return Promise.resolve();
    }

    listDevices(options: void): Promise<{ devices: Device[]; }> {
        return Promise.resolve({ devices: [
            { name: 'Device A', address: '00:11:22:33:44:55' },
            { name: 'Device B', address: '66:77:88:99:AA:BB' },
            { name: 'Device C', address: 'CC:DD:EE:FF:00:11' },
        ] });
    }
    checkAndRequestBluetoothPermission(options: void): Promise<void> {
        // throw new Error('Method not implemented.');
        console.log("Checking and requesting Bluetooth permission (simulated)");
        return Promise.resolve();
    }
    connect(options: { address: string; }): Promise<void> {
        console.log("Connecting to device at address:", options.address);
        this.isConnected = true;
        return Promise.resolve();
    }
}