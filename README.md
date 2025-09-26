# capacitor-bluetooth-serial

Bluetooth Serial

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
* [`registerListener(...)`](#registerlistener)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### listDevices(...)

```typescript
listDevices(options: void) => Promise<{ devices: Device[]; }>
```

| Param         | Type              |
| ------------- | ----------------- |
| **`options`** | <code>void</code> |

**Returns:** <code>Promise&lt;{ devices: Device[]; }&gt;</code>

--------------------


### checkAndRequestBluetoothPermission(...)

```typescript
checkAndRequestBluetoothPermission(options: void) => Promise<void>
```

| Param         | Type              |
| ------------- | ----------------- |
| **`options`** | <code>void</code> |

--------------------


### connect(...)

```typescript
connect(options: { address: string; }) => Promise<void>
```

| Param         | Type                              |
| ------------- | --------------------------------- |
| **`options`** | <code>{ address: string; }</code> |

--------------------


### registerListener(...)

```typescript
registerListener(options: { onData: (data: [number]) => void; }) => Promise<void>
```

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code>{ onData: (data: [number]) =&gt; void; }</code> |

--------------------


### Interfaces


#### Device

| Prop          | Type                |
| ------------- | ------------------- |
| **`name`**    | <code>string</code> |
| **`address`** | <code>string</code> |

</docgen-api>
