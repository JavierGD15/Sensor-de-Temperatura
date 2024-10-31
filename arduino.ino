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