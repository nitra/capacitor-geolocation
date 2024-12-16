import { ClearWatchOptions, PositionOptions } from "./definitions";

export const PositionOptionsDefault: PositionOptions = {
    enableHighAccuracy: false,
    timeout: 1000,
    maximumAge: 0,
    minimumUpdateInterval: 5000
}

export const ClearWatchOptionsDefault: ClearWatchOptions = {
    id: "-1"
}