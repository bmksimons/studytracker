# Studytracker
=====

Vårt prosjekt omhandler appen StudyTracker. I appen skal brukeren kunne legge inn fag, og sette antall timer man jobber med hvet enkelt fag.
Appen er en trelagsapplikasjoen som består av domenelag, brukergrensesnitt og persistens. 

## Organisering av koden
Prosjektet er organisert to moduler, fxui og core. Hver modul har to kildekodemapper, en for koden(main) og en for test.

Fxui:
- **fxui/src/main/java/studytracker/ui** for Controlleren og App.java
- **fxui/src/main/resources/studytracker/ui**for FXML-filen
- **fxui/src/test/java** for testkoden til Controlleren og App.java
- **fxui/src/test/resources** for en enklere kopi av fxml-filen som brukes i testene til Controlleren og App.java

Core:
- **core/src/main/java/studytracker/core** for Semester og Course klassene
- **core/src/main/java/studytracker/json** for Serializer, og Deserializer klassene for å håndtere fillagring via JSON og Jackson
- **core/src/test** for testkododen til JSON, Semester og Course klassene


## Domenelaget

Vår app handler om å kunne legge til fag man har et semester og hvor manage timer man bruker på å jobbe med faget. Man skal kunne oppdatere timeestimatet hele tiden. For å
håndtere tilhørende informasjon har vi Course og Semseter klasser. Disse ligger i [core.java.studytracker.core](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master/IT1901%2Fcore%2Fsrc%2Fmain%2Fjava%2Fstudytracker%2Fcore).

I domenelaget har vi klassene som omhandler logikk og objektene som applikasjoenen bruker. Dette laget fungerer uavhengig av resten av koden. 

## Brukergrensesnittlaget

I brukergrensesnittlaget har vi klasser og logikk som er knyttet opp mot visning av appen,
samt håndtering av informasjon brukeren legger inn. I vår app handler dette om å vise brukeren hvilke fag som er lagt inn, og hvor mye tid man har brukt på hvert fag. 
I brukergrensesnittlaget ligger også koden for å håndtere inputs fra brukeren. Koden til kjøringen av appen og kontrolleren finnes i [fxui.java.studytracker.ui](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master/IT1901%2Ffxui%2Fsrc%2Fmain%2Fjava%2Fstudytracker%2Fui).
Koden til fxml-filen finnes i [fxui.resources.studytracker.ui](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master/IT1901%2Ffxui%2Fsrc%2Fmain%2Fresources%2Fstudytracker%2Fui).


## Persistenslaget

I persistenslaget ligger klasser og logikk tilknyttet lagring og lesing fra fil. Filhåndteringen håndteres av JSON med Jacksonbibilioteket. 
Course og Semester har sine Serializers og Deserializers klasser som beskriver hvordan man skal skrive og lese objektene fra html-format. De finnes i [core.java.studytracker.json](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master/IT1901%2Fcore%2Fsrc%2Fmain%2Fjava%2Fstudytracker%2Fjson).

Via JSON har vi valgt implisitt lagring i stedet for dokumentmetafor. Det er mest funksjonelt for appen vår fordi brukeren kun har et semester å forholde seg til om gangen.
Dokumentmetafor med mulighet for å bytte mellom ulike semestere er unødvendig og vil få brukeren til å få flere ting å forholde seg til. Med implisitt lagring blir appen
enklere og mer brukervennlig.

## plantUML diagramkode

@startUML

component Core {
    package studyTracker.core{
        [Course]
        [Semester]
    }
    package studyTracker.JSON
}

component jackson{
}

studyTracker.JSON ..> jackson

component fxui {
    package studyTracker.fxui
}

studyTracker.fxui ..> studyTracker.core
studyTracker.fxui ..> studyTracker.JSON

component javafx{
    component fxml{
    }
}

fxui ..> javafx

[Course] ..> [Semester] : owner:1
[Semester]..> [Course] : 0-4


@endum