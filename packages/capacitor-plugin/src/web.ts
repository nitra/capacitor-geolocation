import { WebPlugin } from '@capacitor/core';

import type { IGeolocationPlugin } from './definitions';

export class GeolocationPluginWeb extends WebPlugin implements IGeolocationPlugin {
  async ping(options: { value: string }): Promise<string> {
    return options.value + '_pong';
  }
}
