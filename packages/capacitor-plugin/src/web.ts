import { WebPlugin } from '@capacitor/core';

import type { CallbackID, ClearWatchOptions, GeolocationPluginPermissions, IGeolocationPlugin, PermissionStatus, Position, PositionOptions, WatchPositionCallback } from './definitions';

export class GeolocationPluginWeb extends WebPlugin implements IGeolocationPlugin {
  getCurrentPosition(options?: PositionOptions): Promise<Position> {
    throw new Error('Method not implemented.');
  }
  watchPosition(options: PositionOptions, callback: WatchPositionCallback): Promise<CallbackID> {
    throw new Error('Method not implemented.');
  }
  clearWatch(options: ClearWatchOptions): Promise<void> {
    throw new Error('Method not implemented.');
  }
  checkPermissions(): Promise<PermissionStatus> {
    throw new Error('Method not implemented.');
  }
  requestPermissions(permissions?: GeolocationPluginPermissions): Promise<PermissionStatus> {
    throw new Error('Method not implemented.');
  }
}
