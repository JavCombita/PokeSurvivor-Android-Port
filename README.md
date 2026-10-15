# PokéSurvivor Android — Port by JavCombita

Port Android del juego web original: https://github.com/JPTER78/pokesurvivor

## Modos de juego

Al abrir la aplicación, elige uno de los dos modos:

- **Online:** abre https://pokesurvivor.com dentro de la aplicación. Requiere conexión y permite utilizar las funciones online que ofrezca la versión alojada, como cuentas, guardado en la nube, ranking o cooperativo cuando estén disponibles/configurados.
- **Offline:** carga la copia del juego incluida en el APK. No requiere conexión para jugar en solitario. El progreso local de este modo se guarda en el almacenamiento web de la aplicación.
- **Cambio automático:** si eliges Online y la página no puede cargarse por un error de conexión, la aplicación intenta abrir la copia Offline.

Los datos guardados en el modo online y en el modo offline pueden ser independientes: el almacenamiento local de cada origen no se sincroniza automáticamente. Las funciones que dependan de servidores no estarán disponibles offline.

## Créditos

- Juego original: JPTER78 — https://github.com/JPTER78/pokesurvivor
- Port Android: JavCombita

El proyecto utiliza un WebView Android; el motor del juego sigue siendo HTML/CSS/JavaScript y Canvas 2D. Revisa las licencias antes de redistribuirlo. El código del repositorio original se indica bajo MIT, pero los sprites, la fuente y las marcas tienen condiciones propias. Conserva `LICENSE` y `CREDITS.md` del original.

## Compilar APK con GitHub Actions

1. Sube el contenido de esta carpeta a un repositorio de GitHub.
2. Abre **Actions** y ejecuta **Build Android APK** (o haz push a `main`/`master`).
3. Descarga el artefacto `PokeSurvivor-Android-debug-apk` cuando termine el workflow.

El workflow descarga la versión actual del repositorio original durante la compilación y la empaqueta para el modo offline. El modo online utiliza la web pública y requiere conexión.

## Compilar localmente

Requisitos: JDK 17, Android SDK y Gradle compatible con el proyecto.

Coloca una copia del repositorio original dentro de `app/src/main/assets/game/` (debe incluir `index.html`) y ejecuta:

```bash
gradle assembleDebug
```

APK: `app/build/outputs/apk/debug/app-debug.apk`.

## Estado

El proyecto está preparado para compilar ambos modos. Después de compilar, prueba Online con conexión y Offline con el dispositivo en modo avión para confirmar el comportamiento en tu dispositivo.
