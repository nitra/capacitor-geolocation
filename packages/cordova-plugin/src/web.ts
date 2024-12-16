import { require } from "cordova";
import { ClearWatchOptions, PluginError, Position, PositionOptions } from "./definitions";
import { ClearWatchOptionsDefault, PositionOptionsDefault } from "./defaults";

var exec = require('cordova/exec');

function getCurrentPosition(options: PositionOptions, success: (output: Position) => void, error: (error: PluginError) => void): void {
  options = options || PositionOptionsDefault;

  exec(success, error, 'OSGeolocation', 'getCurrentPosition', [options]);
}

function watchPosition(options: PositionOptions, success: (output: string) => void, error: (error: PluginError) => void): void {
  options = options || PositionOptionsDefault;

  exec(success, error, 'OSGeolocation', 'watchPosition', [options]);
}

function clearWatch(options: ClearWatchOptions, success: (output: string) => void, error: (error: PluginError) => void): void {
  options = options || ClearWatchOptionsDefault;

  exec(success, error, 'OSGeolocation', 'clearWatch', [options]);
}

module.exports = {
  getCurrentPosition,
  watchPosition,
  clearWatch
};