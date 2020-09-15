# Studytracker
=====

Vårt prosjekt omhandler appen StudyTracker. I appen skal brukeren kunne legge inn fag, og sette antall timer man jobber med hvet enkelt fag.
Appen er en trelagsapplikasjoen som består av domenelag, brukergrensesnitt og persistens. 

## Organisering av koden
Prosjektet er organisert med 2 kildekodemapper, en for koden og en for testene:
- **src/main/java** for koden til applikasjonene
- **src/test/java** for testkoden
- 
## Domenelaget

Vår app handler om å kunne legge til fag man har et semester og hvor manage timer man bruker på å jobbe med faget. For å
håndtere tilhørende informasjon har vi course og semseter klasser. Disse ligger i [studyTracker.core](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/NewStart/IT1901%2Fsrc%2Fmain%2Fjava%2FstudyTracker%2Fcore)-pakken

I domenelaget har vi klassene som omhandler logikk og objektene som applikasjoenen bruker. Dette laget fungerer uavhengig
av resten av koden. 

## Brukergrensesnittlaget

I brukergrensesnittlaget har vi klasser og logikk som er knyttet opp mot visning av appen,
samt håndtering av informasjon brukeren legger inn. I vår app handler dette om å vise brukeren hvilke fag som er lagt inn, og hvor mye tid man har brukt på hvert fag. 
I brukergrensesnittlaget ligger også koden for å håndtere inputs fra brukeren. Koden finnes i [studyTracker.ui](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/NewStart/IT1901%2Fsrc%2Fmain%2Fjava%2FstudyTracker%2Ffilehandling).


## Persistenslaget

I persistenslaget ligger klasser og logikk tilknyttet lagring og lesing fra fil. For øyeblikket baserer vi oss på scanner- og 
printWriter-klassene. Videre planlegger vi å implementere JSON. Koden for fillagring liiger i [studyTracker.filehandeling](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/NewStart/IT1901%2Fsrc%2Fmain%2Fjava%2FstudyTracker%2Ffilehandling). 