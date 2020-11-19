# Beskrivelse av studytracker-app

Brukeren er student og vil holde oversikt over hvor mye tid en bruker på hvert fag.
Applikasjonen skal kunne registrere fag, som legges til i databasen. Til enhver tid skal brukeren kunne se antallet timer som har blitt brukt på hvert fag, og legge til/trekke fra timer. Brukeren kan også vise fram statistikk over antallet timer studert i de ulike fagene.
Det skal være mulig å nullstille all data, slik at antall timer studert i alle fag = 0. Dette er nyttig hvis det er starten på en ny uke eller et nytt semester.
Det skal være mulig å slette fag fra appen. 

## Brukerhistorie 1

Brukeren er student og vil holde oversikt over hvor mye tid en bruker på hvert fag.
Man skal kunne se hvilke fag som er lagt til, med estimatet på antall timer man har brukt på faget.
Av funksjoner er det viktig å kunne legge til nye fag, legge til antall timer man har brukt på hvert fag. Timeestimatet skal alltid kunne oppdateres.
I tillegg skal man kunne lagre dataen til neste gang man åpner appen.

### Issues relatert til brukerhistorie 1:
*  #11 - Reset Button logikk
*  #7 - Fillagring
*  #6 - JavaFx utseende

### Sekvensdiagram til brukerhistorie 1
Trykk på bildeikonet for å åpne diagrammet:
``` plantuml
@startuml
actor user
user -> "~#newCourse: TextField" as newCourse: write
newCourse -> "~#addCourse: Button" as addCourse: click
addCourse -> Controller: addCourse
Controller -> Semester: makeCourse
Semester -> Semester: fireSemesterChanged
Semester -> SemesterListener: semesterChanged
SemesterListener -> Controller: saveSemester
Controller -> RemoteSemesterAccess: putSemester
RemoteSemesterAccess -> SemesterService: putSemester
SemesterService -> Semester: setCourses

@enduml
```

## Brukerhistorie 2

Brukeren vil slippe å måtte trykke på knapper for å lagre og laste opp tidligere lagret informasjon om semesteret og fagene. 
I stedet skal det være implisitt lagring hver gang brukeren endrer noe informasjon i studieplanleggeren.

### Issues relatert til brukerhistorie 2:
*  #15 - Implementere JSON
*  #8 - Implementere Semester og Course i kontrollerklassen

## Brukerhistorie 3

Brukeren vil ha muligheten til å fjerne fag som er lagt til i studieplanleggeren. 
Det skal være mulig å velge hvilket fag du vil fjerne via en nedtrekksmeny med alle fagene du allerede har lagt til.

### Issues relatert til brukerhistorie 3:
*  #14 - Slette fag


### Sekvensdiagram for brukerhistorie 3:
Trykk på bildeikonet for å åpne diagrammet:
``` plantuml
@startuml
actor user
user -> "~#pickCourseDelete: ChoiceBox" as pickCourseDelete: click
pickCourseDelete -> "~#delete: Button" as delete: click
delete -> Controller: deleteCourse
Controller -> Semester: makeDeleteCourse
Controller -> RemoteSemesterAccess: deleteCourse
RemoteSemesterAccess -> SemesterService: getCourse
SemesterService -> CourseResource: deleteCourse
CourseResource -> Semester: deleteCourse


@enduml
```

## Brukerhistorie 4:

Brukeren vil ha muligheten til å trykke på en knapp som viser fram statistikk for antall timer studert i hvert enkelt fag. 

### Issues relatert til brukerhistorie 4:
*  #22 - Lage nytt javafx-vindu
*  #23 - Lage klasse for statistikk

#### Sekvensdiagram for brukerhistorie 4
Trykk på bildeikonet for å åpne diagrammet:
``` plantuml
@startuml
actor user

user -> "~#statistic: Button" as statistic: click
statistic -> Controller: onOpenStatisticsClick
Controller -> ControllerStatistic
ControllerStatistic -> RemoteSemesterAccess: getSemester
RemoteSemesterAccess -> SemesterService: getSemester
SemesterService -> Semester: getSemester


@enduml
```



