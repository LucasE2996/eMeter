# eMonitor

O que fazer?
-----------------
Sistema IoT de Monitoramento de Consumo Doméstico


Por que fazer?
-----------------
Diante da pouca eficiência da classificação de consumo e falta de controle dos gastos de energia dos eletrodomésticos gerando um aumento na conta de energia elétrica, foi proposto o desenvolvimento deste protótipo.

Para que fazer?
-----------------
Gerenciar o controle de gastos evitando o desperdício através da conferência da classificação do consumo de energia do eletrodoméstico;

Tecnologias usadas:
-----------------
Frontend:
- Phonegap

Backend:
- Spring boot

Server:
- Heroku
- ThingSpeak

Hardware:
- ESP8266

Como utilizar:
---
Iniciar para produção:
````
java -jar -Dspring.profiles.active=prod app-0.0.1-SNAPSHOT.jar
````

Iniciar para desenvovimento ou teste:
````
java -jar -Dspring.profiles.active=dev app-0.0.1-SNAPSHOT.jar 
````

---------------------------------------
TCC College Project - All rights reserved
