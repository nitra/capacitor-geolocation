import { require } from "cordova";
import { PluginError } from "./definitions";
var exec = require('cordova/exec');

function ping(options: { value: string }, success: (output: string) => void, error: (error: PluginError) => void): void {
  exec(success, error, 'GeolocationPlugin', 'ping', [options]);
}

module.exports = {
  ping
};