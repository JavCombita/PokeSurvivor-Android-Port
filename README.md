# PokéSurvivor Android — Port by JavCombita

Port Android del juego original de JPTER78: https://github.com/JPTER78/pokesurvivor

## Integración con el juego original

La aplicación abre directamente los archivos originales dentro de un WebView seguro. No añade pantallas de inicio ni menús Android independientes: el acceso, registro, ajustes, pausa y opciones son los del juego.

## Cuenta oficial y sincronización

Esta compilación conserva la configuración Firebase pública incluida en el repositorio original de PokéSurvivor. No crea un proyecto Firebase propio ni cambia el identificador del proyecto, por lo que utiliza el backend oficial autorizado por el autor.

- Inicia sesión o crea una cuenta desde el menú original del juego para usar la nube.
- La partida se guarda localmente para poder continuar sin conexión.
- Al recuperar la conexión, el juego vuelve a sincronizar y aplica la lógica de fusión original.
- La configuración Firebase y las reglas de seguridad son las del proyecto original; no se incluyen credenciales privadas de servidor.
- El workflow empaqueta localmente el SDK de Firebase para que el juego pueda cargar sus recursos desde el APK incluso sin conexión.

**Requisito de despliegue:** la autenticación por contraseña usa el backend oficial. Para que las opciones de inicio de sesión que usan OAuth/Google funcionen desde el origen de Android (`appassets.androidplatform.net`), el administrador del proyecto Firebase debe autorizar ese dominio en Authentication → Settings → Authorized domains y comprobar el flujo de redirección. No se intenta eludir esta comprobación. Si no se ha añadido, usa el método de acceso que el administrador haya confirmado compatible.

## Compilar APK con GitHub Actions

1. Sube esta carpeta a tu repositorio GitHub.
2. Ejecuta **Actions → Build Android APK → Run workflow**.
3. Descarga el artefacto `PokeSurvivor-Android-debug-apk`.

El workflow descarga la versión actual del juego original, conserva su `js/core/firebase-config.js`, empaqueta el SDK Firebase y añade el origen local seguro de Android a la comprobación interna de host del juego. No hace falta crear el secreto `FIREBASE_CONFIG_JSON` ni configurar un proyecto Firebase propio.

## Créditos

- Juego original: JPTER78 — https://github.com/JPTER78/pokesurvivor
- Port Android e integración: JavCombita

Se conservan los archivos de licencia y créditos descargados del repositorio original. Consulta la licencia actual del repositorio antes de redistribuir el APK o el código, ya que las condiciones del proyecto pueden cambiar.

## Prueba recomendada

1. Con conexión, inicia sesión con una cuenta oficial y comprueba que el indicador de guardado llegue al estado correcto.
2. Juega y consigue algún progreso.
3. Activa el modo avión y continúa jugando.
4. Recupera la conexión y espera a que termine la sincronización.
5. Abre la misma cuenta en la web oficial para verificar que el progreso se haya fusionado.

El ZIP contiene el proyecto y el workflow, no un APK precompilado. La compilación y la sincronización real deben validarse en GitHub Actions y en un dispositivo.
