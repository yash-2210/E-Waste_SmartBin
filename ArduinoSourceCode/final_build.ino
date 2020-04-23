#include <Servo.h>   //servo library
#include <Q2HX711.h>
#include <SoftwareSerial.h>
#include <ArduinoJson.h>

//#include <Wire.h> // Library for I2C communication
//#include <LiquidCrystal_I2C.h> // Library for LCD
//// Wiring: SDA pin is connected to A4 and SCL pin to A5.
//// Connect to LCD via I2C, default address 0x27 (A0-A2 not jumpered)
//LiquidCrystal_I2C lcd = LiquidCrystal_I2C(0x27, 20, 4); // Change to (0x27,16,2) for 16x2 LCD.


const int dustbinid = 1;
Servo servo;
int trigPin = 5;
int echoPin = 6;
int servoPin = 7;
int led = 10;
long duration, dist, average;
long aver[3];   //array for average

//------------------Dist Code Vars
const int dist_trigPin = 2;  //2
const int dist_echoPin = 4;  //4

//------------------Weight Server
const byte hx711_data_pin = 3;
const byte hx711_clock_pin = 12; //2

float y1 = 100.0; // calibrated mass to be added
long x1 = 0L;
long x0 = 0L;
float avg_size = 1.0;// amount of averages for each mass measurement
int overweightflag; //0=<4kg   1=>1kg
float lastweight = -1;
Q2HX711 hx711(hx711_data_pin, hx711_clock_pin); // prep hx711


//---------------------Node MCU
SoftwareSerial s(10, 11);
//D6, D5





void setup() {
  Serial.begin(115200);
  s.begin(9600);
  servo.attach(servoPin);
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  servo.write(0);         //close cap on power on
  delay(100);
  servo.detach();
  // -------------Weight Sensor Srtup
  overweightflag = 0;
  delay(1000); // allow load cell and hx711 to settle
  // tare procedure
  for (int ii = 0; ii < int(avg_size); ii++) {
    delay(10);
    x0 += hx711.read();
    Serial.println("iteration : ");
    Serial.println(x0);
    Serial.println("---------------------");
  }
  x0 /= long(avg_size);
  Serial.println("Add Calibrated Mass");
  // calibration procedure (mass should be added equal to y1)
  int ii = 1;
  while (true) {
    if (hx711.read() < x0 + 10000) {
    } else {
      ii++;
      delay(2000);
      for (int jj = 0; jj < int(avg_size); jj++) {
        x1 += hx711.read();
      }
      x1 /= long(avg_size);
      break;
    }
  }
  Serial.println("Calibration Complete");
  Serial.println("Your Arduino is now Operational");
//   lcd.init();
  //lcd.backlight();
}

void loop() {
  for (int i = 0; i <= 2; i++) { //average distance
    measure();
    aver[i] = dist;
    delay(10);              //delay between measurements
  }
  dist = (aver[0] + aver[1] + aver[2]) / 3;

  if ( dist < 50 ) {
    
    Serial.print("Inside If : ");
    Serial.println(dist);
    servo.attach(servoPin);
    delay(1);
    servo.write(0);
    delay(3000);
    servo.write(150);
    delay(1000);
    servo.detach();
    //delay(5000);
     delay(500);
      long dustbin_mesure = getDistance();
      delay(500);
      
    int weight = getCurrentWeight();
    //lcd.clear();
    //lcd.home(); // Set the cursor on the first column and first row.
    //lcd.print(String(weight)); 
   // delay(2000);
    createJsonAndWriteToNodeMCU(weight, 0, dustbin_mesure);
    
  }
 //  Serial.println("Outside If : ");
  //Serial.println(dist);

}


void measure() {
  digitalWrite(10, HIGH);
  digitalWrite(trigPin, LOW);
  delayMicroseconds(5);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(15);
  digitalWrite(trigPin, LOW);
  pinMode(echoPin, INPUT);
  duration = pulseIn(echoPin, HIGH);
  dist = (duration / 2) / 29.1;  //obtain distance
}


//-----------------DIst Utikity Functions

long microsecondsTodist_inches(long microseconds)
{ return microseconds / 74 / 2;
}

long microsecondsToCentimeters(long microseconds)
{
  return microseconds / 29 / 2;
}
long getDistance() {
  long dist_duration, dist_inches, dist_cm;
  pinMode(dist_trigPin, OUTPUT);
  digitalWrite(dist_trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(dist_trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(dist_trigPin, LOW);
  pinMode(dist_echoPin, INPUT);
  dist_duration = pulseIn(dist_echoPin, HIGH);
  dist_inches = microsecondsTodist_inches(dist_duration);
  dist_cm = microsecondsToCentimeters(dist_duration);
  Serial.println("");
  Serial.print(dist_inches);
  Serial.print("in, ");
  Serial.print(dist_cm);
  Serial.print("cm");
  Serial.println("\n");
  //delay(100);
  return dist_cm;
}


//----------------------------------------Weight Utility Functions
int getCurrentWeight() {
  // averaging reading
  long reading = 0;
  for (int jj = 0; jj < int(avg_size); jj++) {
    reading += hx711.read();
  }
  reading /= long(avg_size);
  // calculating mass based on calibration and linear fit
  float ratio_1 = (float) (reading - x0);
  float ratio_2 = (float) (x1 - x0);
  float ratio = ratio_1 / ratio_2;
  float mass = y1 * ratio;
  Serial.print("Raw: ");
  Serial.print(reading);
  Serial.print(", ");
  Serial.println(mass);

  /*if (mass >= 1000)
    {
    //overweightflag=1;
    if (overweightflag != 1)
    {
      Serial.println("Dustbin over weight");
      overweightflag = 1;
    }
    }
    else
    {
    overweightflag = 0;
    }

    if ((lastweight - 10) <= mass  && (lastweight + 10) >= mass)
    {

    }
    else
    {

    //Serial.println("Weight Changed");
    }*/


  lastweight = mass;
  delay(500);
  return mass;
}

void createJsonAndWriteToNodeMCU(int weight, int dustbinDepth, long dustbin_dist) {
  StaticJsonBuffer<1000> jsonBuffer;
  JsonObject& root = jsonBuffer.createObject();
  root["currentWeight"] = weight;
  root["dustbinid"] = dustbinid;
  root["dustbin_dist"] = dustbin_dist;
  root.printTo(s);
  root.printTo(Serial);
  //s.print("JSON STRING THAT HAS TO BE PRINTED");
  //root.printTo(s);
}
