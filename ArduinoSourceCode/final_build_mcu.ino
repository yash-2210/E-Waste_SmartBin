#include <SoftwareSerial.h>
#include <ArduinoJson.h>
#include <ESP8266WiFi.h>

SoftwareSerial s(D6, D5);
const char* ssid     = "Tirth";
const char* password = "Tirth65231";

const char* host = "192.168.0.104";

void setup() {
  Serial.begin(115200);
  s.begin(9600);
  while (!Serial) continue;
  setupWifi();
}
int value = 0;
void setupWifi() {
  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void loop() {
  if (s.available() > 0) {
    String payload = (s.readString());
    const size_t capacity = JSON_OBJECT_SIZE(3) + JSON_ARRAY_SIZE(2) + 60;
    DynamicJsonBuffer jsonBuffer(capacity);
    JsonObject& root = jsonBuffer.parseObject(payload);
    int curWeight = root["currentWeight"];
    int dustbinId = root["dustbinid"];
    long dustbin_dist = root["dustbin_dist"];
    Serial.println("-------------------XXXX---------------");
    Serial.println(curWeight);
    Serial.println(dustbinId);
    Serial.println(dustbin_dist);
    makeNetworkRequest(dustbinId, curWeight, dustbin_dist);
  }
}

void makeNetworkRequest(int dustbinid, int weight, long dustbin_dist) {
  Serial.print("connecting to ");
  Serial.println(host);
  WiFiClient client;
  const int httpPort = 80;
  if (!client.connect(host, httpPort)) {
    Serial.println("connection failed");
    return;
  }
  String url = "http://192.168.0.104/EWSB/Rest_API/dustinStatus.php?weight=DGFweight&dustbinid=DGFdustbinid&dustbin_dist=DGFdustbindist";
  url.replace("DGFweight", String(weight));
  url.replace("DGFdustbinid", String(dustbinid));
  url.replace("DGFdustbindist", String(dustbin_dist));
 
  Serial.print("Requesting URL: ");
  Serial.println(url);
  client.print(String("GET ") + url + " HTTP/1.1\r\n" +
               "Host: " + host + "\r\n" +
               "Connection: close\r\n\r\n");
  unsigned long timeout = millis();
  while (client.available() == 0) {
    if (millis() - timeout > 5000) {
      Serial.println(">>> Client Timeout !");
      client.stop();
      return;
    }
  }
  while (client.available()) {
    String line = client.readStringUntil('\r');
    Serial.print(line);
  }
  Serial.println();
  Serial.println("closing connection");
  url = "";
}
