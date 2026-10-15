# PokéSurvivor Offline — proyecto Android

**Autor del juego original:** JPTER78  
**Port Android offline:** JavCombita

## Compilar
Abre la carpeta `android-app` en Android Studio, instala Android SDK 35 y JDK 17, sincroniza Gradle y ejecuta la aplicación en un dispositivo Android. Para generar el APK: **Build > Build APK(s)**.

Esta entrega contiene el proyecto fuente Android Studio; no incluye un APK precompilado. La compilación no se pudo ejecutar en este entorno porque no hay Gradle/Android SDK configurados.

## Funciones del port
- Inicio directo sin cuentas ni registro.
- Guardado local persistente y funcionamiento sin conexión.
- Sin permiso de Internet ni módulos de Firebase, ranking, multijugador o cuentas en el paquete Android.
- Botón de apoyo voluntario: abre PayPal externamente en `https://paypal.me/javcombita`; el usuario elige el monto.
- Importar/exportar partidas JSON con el selector nativo de Android.
- Ajustes de sonido, gráficos e idioma; se eliminan los ajustes de controles configurables.

## Licencias
Se incluyen los avisos originales en `app/src/main/assets/www/LICENSE`, `LICENSE-NONCOMMERCIAL.md` y `CREDITS.md`. El código de la versión suministrada está bajo PolyForm Noncommercial y los sprites bajo CC BY-NC 4.0. Antes de distribuir el port o vincularlo a donaciones, revisa las licencias y obtén permiso si fuera necesario.
