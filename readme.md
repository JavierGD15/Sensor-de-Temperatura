# Proyecto: Lector de Temperatura y Humedad

## Descripción
Esta aplicación permite medir la temperatura y la humedad usando un sensor DHT11 conectado a un Arduino. Los datos se muestran en una interfaz gráfica de usuario (GUI) desarrollada en Java. Además, permite exportar los registros a un archivo PDF y generar gráficas de tendencias de temperatura.

## Requisitos
- Arduino con DHT11
- Java JDK 8 o superior
- Biblioteca iText 5.4.0 (para exportar a PDF)
- Biblioteca JFreeChart (para generar gráficos)
- Conector JDBC de MySQL (para almacenar datos)

## Conexiones del Hardware

### Conexiones del Sensor DHT11
1. **Pin VCC**: Conectar al pin de 5V del Arduino.
2. **Pin GND**: Conectar al pin GND del Arduino.
3. **Pin de Datos**: Conectar al pin digital D5 del Arduino.

### Conexiones de los LEDs
1. **LED Verde**: Conectar al pin D6 del Arduino.
2. **LED Amarillo**: Conectar al pin D7 del Arduino.
3. **LED Rojo**: Conectar al pin D8 del Arduino.

### Diagrama de Conexión

![Diagrama de Conexión](https://link_a_tu_imagen_diagrama.png)

## Código del Arduino

```cpp
#include "DHT.h"

#define DHTPIN 5      // Pin digital conectado al sensor DHT
#define DHTTYPE DHT11 // Tipo de sensor

DHT dht(DHTPIN, DHTTYPE);

// Definición de pines para los LEDs
#define LED_VERDE 6   // LED verde para temperatura estable
#define LED_AMARILLO 7 // LED amarillo para precaución
#define LED_ROJO 8    // LED rojo para temperatura grave

void setup() {
  Serial.begin(9600);
  dht.begin();

  pinMode(LED_VERDE, OUTPUT);
  pinMode(LED_AMARILLO, OUTPUT);
  pinMode(LED_ROJO, OUTPUT);
}

void loop() {
  delay(2000);  // Espera unos segundos entre lecturas

  float h = dht.readHumidity();    // Lee la humedad
  float t = dht.readTemperature();  // Lee la temperatura en °C

  // Verifica si las lecturas fallan
  if (isnan(h) || isnan(t)) {
    Serial.println("Failed to read from DHT sensor!");
    return;
  }

  // Enviar datos en formato CSV: "temperatura,humedad"
  Serial.print(t);
  Serial.print(",");
  Serial.println(h);

  // Control de LEDs
  if (t < 20) {
    digitalWrite(LED_ROJO, HIGH);
    digitalWrite(LED_AMARILLO, LOW);
    digitalWrite(LED_VERDE, LOW);
  } else if (t >= 20 && t < 25) {
    digitalWrite(LED_ROJO, LOW);
    digitalWrite(LED_AMARILLO, HIGH);
    digitalWrite(LED_VERDE, LOW);
  } else {
    digitalWrite(LED_ROJO, LOW);
    digitalWrite(LED_AMARILLO, LOW);
    digitalWrite(LED_VERDE, HIGH);
  }
}
```

## Guía para Usar la Aplicación

1. **Instala las dependencias**:
   - Asegúrate de tener configuradas las bibliotecas necesarias (iText y JFreeChart) en tu proyecto.
   - Si usas Maven, agrega las dependencias al `pom.xml`.

2. **Conecta el Arduino**:
   - Realiza las conexiones del DHT11 y los LEDs como se describe arriba.
   - Carga el código del Arduino en tu placa.

3. **Ejecuta la Aplicación Java**:
   - Abre la terminal y navega a la carpeta de tu proyecto.
   - Ejecuta el comando `mvn clean install` para compilar el proyecto.
   - Luego, ejecuta la aplicación desde tu IDE o usando la línea de comandos.

4. **Interacción con la GUI**:
   - Observa los valores de temperatura y humedad en la interfaz.
   - Usa el botón **Exportar PDF** para generar un archivo PDF con los registros.
   - Haz clic en **Generar Gráfica** para visualizar la tendencia de temperaturas.
   - Usa el botón **Eliminar Registros** para limpiar la base de datos.

### Especificaciones de Uso

- **Temperatura Celsius**: Muestra la temperatura actual en grados Celsius.
- **Sensor Humedad**: Muestra el nivel de humedad actual.
- **Exportar PDF**: Permite guardar los registros actuales en un archivo PDF.
- **Generar Gráfica**: Crea un gráfico de las temperaturas más altas registradas.
- **Eliminar Registros**: Limpia todos los datos almacenados en la base de datos.

## Conclusión

Este proyecto proporciona una solución integral para la monitorización de temperatura y humedad utilizando un sensor DHT11 y una interfaz gráfica de usuario en Java. Asegúrate de seguir todos los pasos de conexión y configuración para obtener los mejores resultados.
