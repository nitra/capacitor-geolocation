export interface IGeolocationPlugin {
  ping(options: { value: string }): Promise<string>;
}
