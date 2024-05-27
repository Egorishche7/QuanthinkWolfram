import 'zone.js/dist/zone';  // Included with Angular CLI.

(window as any).global = window;
(window as any).Buffer = (window as any).Buffer;
(window as any).process = {
  env: { DEBUG: undefined }
};
