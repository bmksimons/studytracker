# StudyTracker

[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.idi.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master)

Repositoriet består av en README.md fil (denne fila) og .md fil for beskrivelse av applikasjon samt brukerhistorier i [beskrivelse.md](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/blob/master/beskrivelse.md).
Kodeprosjektet er modularisert i fem ulike moduler: [fxui](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master/IT1901%2Ffxui), [core](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master/IT1901%2Fcore), [integrationtests](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master/IT1901%2Fintegrationtests), [restapi](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master/IT1901%2Frestapi) og [restserver](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master/IT1901%2Frestserver).

## Kjøring av prosjektet

Prosjektet bruker maven for bygging og kjøring. For å starte bygging av prosjektet brukes kommandoen 
```bash
mvn install -DskipTests
```
Deretter må serveren startes med   

```bash
mvn -pl integrationtests jetty:run -D"jetty.port=8999"
```
Dette er for å sette riktig serveradresse.
Nå er det mulig å bruke ```mvn install``` som vanlig og tester kan kjøres. 

Prosjektet må kjøres ifra fxui-modulen. Det kan gjøres ved å enten gå inn i fxui ```cd fxui```, eller ved å bruke kommandoen under fra IT1901.
```bash
mvn javafx:run -f fxui/pom.xml
```

Vi har brukt jacoco for å sjekke testdekningsgrad. Testdekningsgraden kan sjekkes ved bruke av:
```bash
mvn jacoco:report
```
Hvis dette ikke fungerer brukes kommandoen:
```bash
mvn test verify
```

## Bakgrunn for oppsett

Dette prosjektet kan ikke kjøres lokalt. Det vil si at ved bruken av kommandoen mvn install, vil testene (som er knyttet opp mot serveren) gi feil. Det er derfor nødvendig 
å hoppe over testene ved første bygging og deretter sette opp serveren. Da vil prosjektet kjøre som forventet. 

## Arbeidsvaner
I dette prosjektet har vi brukt utviklingsoppgavene aktivt. Vi har laget en branch til hvert issue, der alle branchnavn er på formen Issue-x-issuename. Commitmeldingene starter alle 
med #issuenr slik at commiten kommer opp i loggen til issuet. Vi har jobbet mye i par og i gruppe, og har brukt parprogrammering aktivt og på den måten forbedret 
hverandres kode underveis.
