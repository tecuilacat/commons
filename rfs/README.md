# RV-3SB Robot connection interface for RFS class (ADV)

[![Latest Release](https://jitpack.io/v/tecuilacat/commons.svg)](https://jitpack.io/#tecuilacat/commons)
![MIN_SDK Badge](https://img.shields.io/badge/MIN_SDK-Java_17-red)
![Licence](https://img.shields.io/badge/licence-MIT-purple)

---

### !!! This is a former school-project. Do not use for anything else and always test programs with lower speed first !!!

---

### Dies ist ein Schulprojekt und nicht für andere Betriebsarten geeignet
#### Diese API erlaubt es eine schnelle Verbindung zum RV-3SB herzustellen und diesen möglichst einfach im Code zu benutzen

---

## Disclaimer
Es wird nicht gewährleistet, dass diese API den Roboter zuverlässig steuert. Der Entwickler (ich) haftet nicht für jegliche Schäden, die potenziell entstehen können. Der Nutzer ist verantwortlich für den sicheren Umgang mit dem Roboter und muss ein geschriebenes Programm vor dem normalen Einsatz testen (zum Beispiel langsam mit der Hand am Notaus ausführen).

---


## Maven-Dependency / Gradle-Dependency
(Neuste Version steht oben unter JitPack)
```xml
<dependency>
    <groupId>com.github.tecuilacat.commons</groupId>
    <artifactId>rfs</artifactId>
    <version>[latest-release]</version>
</dependency>
```

oder gradle:
```groovy
maven { url 'https://jitpack.io' }

dependencies {
    implementation 'com.github.tecuilacat.commons:rfs:[latest-release]'
}
```

---
## Verbinden mit einem Roboter
Zum Verbinden mit dem Roboter RV-3SB benötigt man 2 Informationen:
- IP im lokalen Netzwerk
- Port des Controllers

Dann kann man einfach mit dem `RobotBuilder` eine Verbindung konfigurieren und herstellen (Beachte: Man muss auf jeden Fall eine sichere Startposition und einen Befehlssatz wählen - der Rest ist optional):

```java
import com.github.tecuilacat.rfs.commands.MelfaBasic4CommandSet;
import com.github.tecuilacat.rfs.control.Robot;
import com.github.tecuilacat.rfs.control.RobotBuilder;
import com.github.tecuilacat.rfs.nav.Position;

public class RobotConnector {

  private static final String IP_ADDRESS = "192.168.1.223";
  private static final int PORT = 10001;
  private static final Position SAFE_POSITION = new Position(420.0, 0.0, 300.0);

  public static void main(String[] args) {
    Robot robot = new RobotBuilder(IP_ADDRESS, PORT)
            .setCommandSet(MelfaBasic4CommandSet.getCommandSet())
            .setSafePosition(SAFE_POSITION)
            .enableCommunication()
            .enableOperation()
            .enableServo()
            .setSpeed(10)
            .build();
  }
}
```

---

## Die Klasse `Position`
Zum Ansteuern von Koordinaten gibt es eine einfache Klasse `Position`, in denen Koordinaten gespeichert sind.  
Für den Use-Case, dass man diese Koordinaten, aber etwas höher benötigt, gibt es die Methode `alterZ(i: int)`:
```java
robot.movToPosition(position.alterZ(-50)); //fährt 50 mm über die Koordinate
robot.mvsToPosition(position); //fährt zur Koordinate
```
`alterZ(i: int)` bezieht sich immer relativ zur Position. Will man einen absoluten Z-Wert, kann man die Methode `alterAbsoluteZ(i: int)` nutzen.

Will man sich Koordinaten ohne Referenz kopieren, gibt es die Methode `copy()`

---
## Programme und Unterprogramme schreiben
Für das Schreiben von Programmen und Unterprogrammen gibt es das Interface `RunnableProgram`, welches die jeweilige Klasse implementieren muss. Von dort aus ist es sehr einfach Routinen zu schreiben:

```java
import com.github.tecuilacat.rfs.control.RobotOperations;
import com.github.tecuilacat.rfs.nav.Position;
import com.github.tecuilacat.rfs.programs.RunnableProgram;

public class Unterprogramm implements RunnableProgram {
  @Override
  public void runProgram(RobotOperations robot) {
    robot.movToPosition(new Position(200.0, 200.0, 200.0));
    robot.grab();
    robot.movToPosition(new Position(-200.0, -200.0, 200,0));
    robot.drop();
    robot.movToSafePosition();
  }
}
```

Der Aufruf in der Hauptklasse erfolgt dann folgendermaßen:
```java
robot.runProgram(new Unterprogramm());
```

---
## Ansteuern über die Konsole
Um live über die Konsole auf den Roboter zuzugreifen, kann man die Klasse `Terminal` nutzen:
```java
Terminal terminal = new Terminal(robot);
terminal.open();
```

Es erscheint dann in der Konsole eine Eingabefunktion. Davor sollte beim Initialisieren über den `RobotBuilder` dem Roboter einen Namen gegeben werden, mit dem man ihn ansprechen kann.  
Um die zur Verfügung stehenden Befehle abzurufen, gibt man folgenden Command ein:
> RV-3SB> help

--- 
## Mehr Informationen - mehr wissen
Wenn man diese API im Debug-Modus ausführt, werden alle Befehle und ähnliche Sachen in die Konsole geloggt

---

&copy; tecuilacat 2023