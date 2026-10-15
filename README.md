# PokéSurvivor Android — Port by JavCombita

Port Android del juego original de JPTER78: https://github.com/JPTER78/pokesurvivor

## Interfaz integrada

La app abre directamente el juego incluido en el APK. No añade un selector de modo, pantallas de inicio nativas ni menús Android propios: el acceso, registro, ajustes, pausa y demás opciones utilizan los menús HTML/CSS originales de PokéSurvivor.

## Juego offline y sincronización automática

- El juego y sus recursos se empaquetan dentro del APK; no necesita descargar la web para arrancar.
- El guardado local se conserva en el dispositivo.
- Para sincronizar, debes crear una cuenta o iniciar sesión desde el menú original del juego. Sin iniciar sesión, el progreso permanece local y no se sube a la nube. Con Firebase configurado, tras iniciar sesión una vez con conexión, la sesión y la copia local permiten seguir jugando sin Internet.
- Los cambios hechos sin conexión quedan en la copia local. Al recuperar la conexión, el juego reintenta la sincronización automáticamente y fusiona los datos con la nube según la lógica de guardado del juego.
- Para crear la cuenta por primera vez, iniciar sesión por primera vez o recuperar una cuenta sin sesión/copia local se necesita Internet.
- Si no configuras Firebase, la app sigue funcionando offline y las cuentas/partidas sólo se guardan en el dispositivo; no habrá sincronización en la nube.

## Activar la sincronización en la nube

Por seguridad y para no utilizar la base de datos del autor original, esta compilación no usa las credenciales Firebase de JPTER78. Configura un proyecto Firebase propio. Las cuentas creadas en ese proyecto serán para esta compilación y **no se sincronizan con las cuentas de la web oficial** `pokesurvivor.com` salvo que el autor autorice expresamente la integración con su backend.

1. Crea un proyecto en https://console.firebase.google.com.
2. En **Authentication → Sign-in method**, activa **Email/Password**.
3. Crea **Cloud Firestore** en modo producción y publica las reglas de seguridad del juego original: https://github.com/JPTER78/pokesurvivor/blob/main/firestore.rules. Revisa las reglas antes de publicarlas en tu propio proyecto.
4. Registra una aplicación web en la configuración del proyecto Firebase y copia su configuración.
5. En **Authentication → Settings → Authorized domains**, añade `appassets.androidplatform.net` a los dominios autorizados.
6. En tu repositorio GitHub, entra en **Settings → Secrets and variables → Actions → New repository secret**. Nombre: `FIREBASE_CONFIG_JSON`. Valor: el objeto JSON de configuración de Firebase en una sola línea, por ejemplo:

   `{"apiKey":"...","authDomain":"mi-proyecto.firebaseapp.com","projectId":"mi-proyecto","storageBucket":"mi-proyecto.firebasestorage.app","messagingSenderId":"123","appId":"1:123:web:abc"}`

   No incluyas claves privadas de servidor ni cuentas de servicio. La configuración web de Firebase se incorpora al APK y no es un secreto; la seguridad depende de Authentication y de las reglas de Firestore.
7. Vuelve a ejecutar **Actions → Build Android APK → Run workflow**. En el log de compilación debe aparecer `la sincronización en nube está habilitada`.

La sincronización de partidas utiliza Firestore. Si más adelante quieres activar funciones de red que usen Realtime Database, configura también esa base de datos y añade `databaseURL` al mismo JSON; aplica las reglas correspondientes antes de habilitarla.

## Compilar APK con GitHub Actions

1. Sube esta carpeta a un repositorio GitHub.
2. Configura `FIREBASE_CONFIG_JSON` siguiendo los pasos anteriores para habilitar la nube (opcional si sólo quieres jugar localmente).
3. Ejecuta **Actions → Build Android APK → Run workflow**.
4. Descarga el artefacto `PokeSurvivor-Android-debug-apk`.

El workflow descarga la versión actual del juego original, empaqueta sus archivos, añade el SDK de Firebase localmente para que el juego pueda inicializarse también sin conexión y adapta la comprobación de origen para el WebView seguro de Android.

## Créditos y licencias

- Juego original: JPTER78 — https://github.com/JPTER78/pokesurvivor
- Port Android e integración: JavCombita

El motor sigue siendo HTML/CSS/JavaScript y Canvas 2D dentro de WebView. Conserva `LICENSE` y `CREDITS.md` del proyecto original. El código original está bajo MIT, pero los sprites, fuentes y marcas tienen sus propias condiciones.

## Estado

El proyecto incluye el código para la sincronización automática y conserva la interfaz original del juego. Para activar la nube debes configurar tu propio Firebase y publicar sus reglas. No he podido validar una cuenta real ni compilar el APK desde este entorno. Prueba el APK en un dispositivo: crea una cuenta conectado, juega, activa el modo avión, avanza, vuelve a conectar y comprueba que el indicador de sincronización del juego termine en estado correcto.
