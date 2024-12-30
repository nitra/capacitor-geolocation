import { SplashScreen } from '@capacitor/splash-screen';
import { GeolocationPlugin } from 'geolocation-capacitor';

window.customElements.define(
  'capacitor-welcome',
  class extends HTMLElement {
    constructor() {
      super();

      SplashScreen.hide();

      const root = this.attachShadow({ mode: 'open' });

      root.innerHTML = `
    <style>
      :host {
        font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
        display: block;
        width: 100%;
        height: 100%;
      }
      h1, h2, h3, h4, h5 {
        text-transform: uppercase;
      }
      .button {
        display: inline-block;
        padding: 16px;
        background-color: #73B5F6;
        color: #fff;
        font-size: 1.1em;
        border: 0;
        border-radius: 3px;
        text-decoration: none;
        cursor: pointer;
      }
      main {
        padding: 15px;
      }
      main hr { height: 1px; background-color: #eee; border: 0; }
      main h1 {
        font-size: 1.4em;
        text-transform: uppercase;
        letter-spacing: 1px;
      }
      main h2 {
        font-size: 1.1em;
      }
      main h3 {
        font-size: 0.9em;
      }
      main p {
        color: #333;
      }
      main pre {
        white-space: pre-line;
      }
    </style>
    <div>
      <capacitor-welcome-titlebar>
        <h1>Capacitor Geolocation Example App</h1>
      </capacitor-welcome-titlebar>
      <main>
        <p>Below are the several features for the capacitor geolocation plugin. You may be prompted to grant location permission to the application.</p>
        <button id="check-permission" class="button">Check permission</button>
        <button id="request-permission" class="button">Request permission</button>
        <br><br>
        <button id="current-location" class="button">Get Current (single) position</button>
        <br><br>
        <button id="watch-location" class="button">Watch position (updates)</button>
      </main>
    </div>
    `;
    }

    connectedCallback() {
      const self = this;

      self.shadowRoot.querySelector('#check-permission').addEventListener('click', async function (e) {
        // TODO fix usage with Synapse
        //const permissionStatus = await window.CapacitorUtils.Synapse.GeolocationPlugin.checkPermissions();
        const permissionStatus = await GeolocationPlugin.checkPermissions();
        alert(`Permissions are:\nlocation = ${permissionStatus.location}`)
      });
      self.shadowRoot.querySelector('#request-permission').addEventListener('click', async function (e) {
        // TODO fix usage with Synapse
        //const permissionStatus = await window.CapacitorUtils.Synapse.GeolocationPlugin.requestPermissions();
        const permissionStatus = await GeolocationPlugin.requestPermissions();
        alert(`Permissions are:\nlocation = ${permissionStatus.location}`)
      });

      self.shadowRoot.querySelector('#current-location').addEventListener('click', async function (e) {
        try {
          // TODO fix usage with Synapse
          /*let currentLocation = await window.CapacitorUtils.Synapse.GeolocationPlugin.getCurrentPosition(
            { enableHighAccuracy: true }
          );*/
          let currentLocation = await GeolocationPlugin.getCurrentPosition(
            { enableHighAccuracy: true }
          );
          const locationString = locationToString(currentLocation, '')
          alert(locationString)
        } catch (exception) {
          alert(`Error getting current position:\n\t code=${exception.code}\n\t message=\"${exception.message}\"`)
        }
      });

      self.shadowRoot.querySelector('#watch-location').addEventListener('click', async function (e) {
        try {
          var watchId = "unknown_watch_id"
          // TODO fix usage with Synapse
          //let watchId = await window.CapacitorUtils.Synapse.GeolocationPlugin.watchPosition(
          watchId = await GeolocationPlugin.watchPosition(
            { enableHighAccuracy: true },
            (position, err) => {
              if (err) {
                alert(`Error getting current position:\n\t code=${err.code}\n\t message=\"${err.message}\"`)
              } else {
                const locationString = locationToString(position, watchId)
                console.log(locationString)
              }
            },
          );
        } catch (exception) {
          alert(`Error getting current position:\n\t code=${exception.code}\n\t message=\"${exception.message}\"`)
        }
      });

      function locationToString(location, watchId) {
        if (location == null || location == undefined) {
          return ""
        }
        var stringRepresentation = 'Position'
        if (watchId) {
          stringRepresentation += ` for watch ${watchId}:\n`
        } else {
          stringRepresentation += ':\n'
        }
        const timeRepresentation = location.timestamp ? new Date(location.timestamp).toISOString() : '-'
        stringRepresentation += `- Time: ${timeRepresentation}\n`
        stringRepresentation += `- Latitute: ${location?.coords.latitude}\n- Longitude: ${location?.coords.longitude}\n`
        if (location?.coords.altitude || location?.coords.heading) {
          stringRepresentation += `- Altitude: ${location?.coords.altitude}\n- Heading: ${location?.coords.heading}\n`
        }
        stringRepresentation += `- Accuracy: ${location?.coords.accuracy}\n`
        if (location?.coords.altitudeAccuracy) {
          stringRepresentation += `- Altitude accuracy: ${location?.coords.altitudeAccuracy}\n`
        }
        return stringRepresentation
      }
    }
  }
);

window.customElements.define(
  'capacitor-welcome-titlebar',
  class extends HTMLElement {
    constructor() {
      super();
      const root = this.attachShadow({ mode: 'open' });
      root.innerHTML = `
    <style>
      :host {
        position: relative;
        display: block;
        padding: 15px 15px 15px 15px;
        text-align: center;
        background-color: #73B5F6;
      }
      ::slotted(h1) {
        margin: 0;
        font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
        font-size: 0.9em;
        font-weight: 600;
        color: #fff;
      }
    </style>
    <slot></slot>
    `;
    }
  }
);
