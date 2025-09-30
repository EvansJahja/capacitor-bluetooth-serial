# capacitor-bluetooth-serial

Capacitor plugin for Bluetooth Serial communication

## Install

```bash
npm install capacitor-bluetooth-serial
npx cap sync
```

## API

<docgen-index>

* [`listDevices(...)`](#listdevices)
* [`checkAndRequestBluetoothPermission(...)`](#checkandrequestbluetoothpermission)
* [`connect(...)`](#connect)
* [`watchData(...)`](#watchdata)
* [`sendData(...)`](#senddata)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### listDevices(...)

```typescript
listDevices(options: void) => Promise<{ devices: Device[]; }>
```

List available Bluetooth devices

| Param         | Type              |
| ------------- | ----------------- |
| **`options`** | <code>void</code> |

**Returns:** <code>Promise&lt;{ devices: Device[]; }&gt;</code>

--------------------


### checkAndRequestBluetoothPermission(...)

```typescript
checkAndRequestBluetoothPermission(options: void) => Promise<void>
```

Check and request Bluetooth permissions

| Param         | Type              |
| ------------- | ----------------- |
| **`options`** | <code>void</code> |

--------------------


### connect(...)

```typescript
connect(options: { address: string; }) => Promise<void>
```

Connect to a Bluetooth device

| Param         | Type                              |
| ------------- | --------------------------------- |
| **`options`** | <code>{ address: string; }</code> |

--------------------


### watchData(...)

```typescript
watchData(callback: WatchDataCallback) => Promise<CallbackID>
```

Register a listener for incoming data

| Param          | Type                                                            |
| -------------- | --------------------------------------------------------------- |
| **`callback`** | <code><a href="#watchdatacallback">WatchDataCallback</a></code> |

**Returns:** <code>Promise&lt;string&gt;</code>

--------------------


### sendData(...)

```typescript
sendData(options: { data: number[]; }) => Promise<void>
```

Send data to the connected Bluetooth device

| Param         | Type                             |
| ------------- | -------------------------------- |
| **`options`** | <code>{ data: number[]; }</code> |

--------------------


### Interfaces


#### Device

| Prop          | Type                |
| ------------- | ------------------- |
| **`name`**    | <code>string</code> |
| **`address`** | <code>string</code> |


### Type Aliases


#### WatchDataCallback

<code>(message: { data: [number]; } | null, err?: any): void</code>


#### CallbackID

<code>string</code>

</docgen-api>
