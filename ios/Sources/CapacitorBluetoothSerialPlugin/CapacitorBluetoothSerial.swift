import Foundation

@objc public class CapacitorBluetoothSerial: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
