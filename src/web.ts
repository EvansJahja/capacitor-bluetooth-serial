import type { CallbackID, CapacitorBluetoothSerialPlugin, Device, WatchDataCallback } from './definitions';

export class CapacitorBluetoothSerialWeb implements CapacitorBluetoothSerialPlugin {
    private port: any = null;
    private onDataCallback: WatchDataCallback | null = null;
    private reader: any = null;

    async listDevices(options: void): Promise<{ devices: Device[]; }> {
        if (!('serial' in navigator)) {
            throw new Error('WebSerial API not supported in this browser');
        }
        try {
            this.port = await (navigator as any).serial.requestPort();
            return {
                devices: [{
                    name: 'Selected Serial Port',
                    address: 'selected'
                }]
            };
        } catch (error) {
            console.log(error);
            throw new Error('User cancelled port selection or error occurred');
        }
    }

    async checkAndRequestBluetoothPermission(options: void): Promise<void> {
        if (!('serial' in navigator)) {
            throw new Error('WebSerial API not available');
        }
        // WebSerial doesn't require explicit permission request; availability check suffices
        return Promise.resolve();
    }

    async connect(options: { address: string; }): Promise<void> {
        if (options.address !== 'selected' || !this.port) {
            throw new Error('No port selected or invalid address');
        }
        try {
            await this.port.open({ baudRate: 9600 }); // Default baud rate; adjust as needed
            this.startReading();
        } catch (error) {
            throw new Error('Failed to open serial port: ' + error);
        }
    }

    watchData(callback: WatchDataCallback): Promise<CallbackID> {
        this.onDataCallback = callback;
        return Promise.resolve('web-callback-id');
    }

    async sendData(options: { data: number[]; }): Promise<void> {
        if (!this.port || !this.port.writable) {
            throw new Error('No open port to write to');
        }
        const writer = this.port.writable.getWriter();
        try {
            await writer.write(new Uint8Array(options.data));
        } finally {
            writer.releaseLock();
        }
    }

    private async startReading() {
        if (!this.port || !this.port.readable || !this.onDataCallback) return;
        this.reader = this.port.readable.getReader();
        try {
            while (true) {
                const { value, done } = await this.reader.read();
                if (done) break;
                this.onDataCallback({ data: Array.from(value) as [number] });
            }
        } catch (error) {
            console.error('Error reading from serial port:', error);
        } finally {
            if (this.reader) {
                this.reader.releaseLock();
            }
            this.reader = null;
        }
    }
}