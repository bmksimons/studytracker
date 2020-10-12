# Beskrivelse av studieplanlegger-app

Brukeren er student og vil holde oversikt over hvor mye tid en bruker på hvert fag.
Applikasjonen skal kunne registrere fag, som legges til i databasen. Man kan også til enhver tid se antallet timer brukeren har studert per fag, og legge til/trekke fra timer.
Det skal være mulig å nullstille all data, slik at antall timer studert i alle fag = 0 (nyttig når man starter en ny uke / ny studieperiode).
Man skal kunne slette fag, som skal oppdateres i databasen.

## Brukerhistorie 1:

Brukeren er student og vil holde oversikt over hvor mye tid en bruker på hvert fag.
Man skal kunne se hvilke fag som er lagt til, med estimatet på antall timer man har brukt på faget.
Av funksjoner er det viktig å kunne legge til nye fag, legge til antall timer man har brukt på hvert fag. Timeestimatet skal alltid kunne oppdateres.
I tillegg skal man kunne lagre dataen til neste gang man åpner appen.

### Issues relatert til brukerhistorie 1:
*  #11 - Reset Button logikk
*  #7 - Fillagring
*  #6 - JavaFx utseende

## Brukerhistorie 2:

Brukeren vil slippe å måtte trykke på knapper for å lagre og laste opp tidligere lagret informasjon om semesteret og fagene. 
I stedet skal det være implisitt lagring hver gang brukeren endrer noe informasjon i studieplanleggeren.

### Issues relatert til brukerhistorie 2:
*  #15 - Implementere JSON
*  #8 - Implementere Semester og Course i kontrollerklassen

## Brukerhistorie 3:

Brukeren vil ha muligheten til å fjerne fag som er lagt til i studieplanleggeren. 
Det skal være mulig å velge hvilket fag du vil fjerne via en nedtrekksmeny med alle fagene du allerede har lagt til.

### Issues relatert til brukerhistorie 3:
*  #14 - Slette fag



