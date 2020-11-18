# StudyTracker

[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.idi.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master)

Repositoriet består av en README.md fil (denne fila) og .md fil for beskrivelse av applikasjon samt brukerhistorier i [beskrivelse.md](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/blob/master/beskrivelse.md).
Kodeprosjektet er modularisert i fem ulike moduler: [fxui](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master/IT1901%2Ffxui), [core](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master/IT1901%2Fcore), [integrationtests](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master/IT1901%2Fintegrationtests), [restapi](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master/IT1901%2Frestapi) og [restserver](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master/IT1901%2Frestserver).

## Kjøring av prosjektet

For å bygge prosjektet må man først kjøre kommandoen 
```bash
mvn install -DskipTest
```
Deretter må man starte serveren med 

```bash
mvn -pl integrationtests jetty:run -D"jetty.port=8999"
```
dette er for å sette riktig serveradresse.
Nå kan man kjøre mvn install som vanlig eller sjekke tester. 

For å kjøre appen bruker man kommandoen:
```bash
mvn javafx:run -f fxui/pom.xml
```

Vi har brukt JACOC for å sjekke testdekningsgrad. For å sjekke hvor mye av koden som er testet bruker man kommandoen:
```bash
mvn jacoco:report
```
Hvis dette ikke fungerer kan man alternativt bruke:
```bash
mvn test verify
```

## Bakgrunn for oppsett

Dette prosjektet kan ikke kjøres lokalt. Det vil si at når man kjører mvn install, vil testene (som er knyttet opp mot serveren) gi feil. Derfor må vi skippe testene ved første bygging
for å så sette opp serveren Da vil prosjektet kjøre som forventet. 