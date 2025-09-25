# capacitor-bluetooth-serial

Bluetooth Serial

## Install

```bash
npm install capacitor-bluetooth-serial
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`listDevices(...)`](#listdevices)
* [`checkAndRequestBluetoothPermission(...)`](#checkandrequestbluetoothpermission)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


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


### Interfaces


#### Device

| Prop          | Type                |
| ------------- | ------------------- |
| **`name`**    | <code>string</code> |
| **`address`** | <code>string</code> |

</docgen-api>
