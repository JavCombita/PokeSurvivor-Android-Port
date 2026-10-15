# PokéSurvivor Android — Port by JavCombita

Port Android empaquetado del proyecto web original:
https://github.com/JPTER78/pokesurvivor

## Créditos
- Juego original: JPTER78 — https://github.com/JPTER78/pokesurvivor
- Port Android: JavCombita

Este proyecto es un contenedor Android con WebView que carga una copia local del juego original. No es una reescritura del motor en Kotlin. El juego se ejecuta dentro de la aplicación y los archivos web se incluyen en los assets del APK.

Revisa las licencias del proyecto original antes de redistribuirlo. El repositorio original indica que el código está bajo MIT, pero los sprites, fuentes y marcas tienen condiciones distintas. Conserva `LICENSE` y `CREDITS.md` del original.

## Compilar APK
La forma más sencilla es usar GitHub Actions:

1. Crea un repositorio nuevo en GitHub y sube el contenido de esta carpeta.
2. Abre la pestaña **Actions** y permite ejecutar workflows si GitHub lo solicita.
3. Ejecuta **Build Android APK** desde `Actions` → `Build Android APK` → `Run workflow`.
4. Descarga el artefacto `PokeSurvivor-Android-debug-apk` al terminar.

El workflow descarga el código fuente público del proyecto original durante la compilación y lo copia dentro de los assets. El APK resultante no necesita descargar el juego al iniciarse. Las funciones que dependan de servicios online del juego original seguirán necesitando conexión.

## Requisitos para compilar localmente
- JDK 17
- Android SDK
- Gradle 8.7

Desde la raíz del proyecto, coloca una copia del repositorio original en `game/` (debe contener `index.html`) y ejecuta:

```bash
gradle assembleDebug
```

El APK se genera en `app/build/outputs/apk/debug/app-debug.apk`.

## Estado
Plantilla inicial de port y compilación. Debe compilarse y probarse en un dispositivo Android real; todavía no se ha validado el APK en un dispositivo.
