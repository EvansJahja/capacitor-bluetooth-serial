import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(CapacitorBluetoothSerialPlugin)
public class CapacitorBluetoothSerialPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "CapacitorBluetoothSerialPlugin"
    public let jsName = "CapacitorBluetoothSerial"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "echo", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = CapacitorBluetoothSerial()

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
}
