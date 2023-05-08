# Antworten zu Aufgabe 2

## 2a)

Der Fibonacciservice ist wie folgt beschrieben implementiert. Es sollte ein REST-Client verwendet werden, der **Cookies unterstützt**.

Es gibt zwei Arten, eine Fibonacci-Zahl abzufragen:

* Explizit die _n_-te Zahl abfragen: `GET http://localhost:8088/fibonacci/n`
* Die Zahlenfolge kontinuierlich steigern. Dies haben wir über Cookies implementiert, die den Zustand lokal speichern.
  * `POST http://localhost:8088/fibonacci` liefert die _nächste_ Fibonacci-Zahl zurück.
  * `DELETE http://localhost:8088/fibonacci` setzt den Counter zurück.

## 2b)

* Die _Ressource_ ist der Fibonacci-Zahlenwert, die Repräsentation ist die Ausgabe. Hier im Format `TEXT/PLAIN`.
* Als MIME-Type haben wir `text/plain` gewählt, um eine ansprechende Antwort wie "fibonacci(30) = 832040" auszugeben. Wenn allerdings andere Services, auf den Fibonacci-Service zugreifen sollen, dann sollte hier ein MIME-Type wie `application/xml`oder `application/json` gewählt werden. Die Ausgabe sollte dann passend modelliert werden, sodass das Ergebnis ohne spezielles Parsing wieterverwendet werden kann.
* Der Zustand wird zwar auf dem Client in Form eines Cookies vorgehalten, allerdings ändern beide Anfagen ("nächste Zahl" und "zurücksetzen") den Zustand, weshalb sie unsafe sind. Das Abfragen der nächsten Zahl ist zudem nicht idempotent, da erneutes Aufrufen stets ein anderes Ergebnis liefert. Das Zurücksetzen kann beliebig oft hintereinander aufgerufen werden, weshlab dieser Aufruf idempotent ist. Aus diesem Grund wird das Abrufen der nächsten Zahl mit `POST` erfolgen, das Zurücksetzen mit `DELETE`.
* Tests siehe `FibonacciTest.java`