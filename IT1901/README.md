# Studytracker
=====

Vårt prosjekt omhandler appen StudyTracker. I appen skal brukeren kunne legge inn fag, og sette antall timer man jobber med hvet enkelt fag. 
I tillegg skal det kunne vises statistikk for antallet timer som har blitt studert.
Appen er en trelagsapplikasjoen som består av domenelag, brukergrensesnitt og persistens. 

## Organisering av koden
Prosjektet er organisert i 5 moduler: fxui, core, integrationtests, restapi og restserver. FXUI, core, restserver og integrationtests har to kildekodemapper, en for koden(main)
og en for testene. Restapi har ikke en kildekodemappe for tester, da denne blir testet av testene som ligger i restserver sin testkode. 

fxui:
- **fxui/src/main/java/studytracker/ui** for controllere og App.java samt tilhørende hjelpeklasser. 
- **fxui/src/main/resources/studytracker/ui** for FXML-filen.
- **fxui/src/test/java/fxui** for testkoden til Controlleren og App.java.
- **fxui/src/test/resources/studytracker/ui** for en enklere kopi av fxml-filen som brukes i testene til Controlleren og App.java.

core:
- **core/src/main/java/studytracker/core** for Semester og Course klassene.
- **core/src/main/java/studytracker/json** for Serializer, og Deserializer klassene for å håndtere fillagring via JSON og Jackson.
- **core/src/test/java** for testkoden til JSON, Semester og Course klassene.

integrationtests:
- **integrationtests/src/main/webapp/WEB-INF** for å starte serveren.
- **integrationtests/src/test/java/studytracker/ui** for testing av serveren.
- **integrationtests/src/test/resources/studytracker/ui** for kopi av fxml-filen for testing inne i integrationtests.

restapi:
- **restapi/src/main/java/stduytracker/restapi** to klasser behandling av serverrequests; POST, PUT, DELETE og GET.

restserver:
- **restserver/src/main/java/studytracker/restserver** for å sette opp og konfigurere serveren.
- **restserver/src/resources/studytracker/restserver** for JSON-filen appen lagres i.
- **restserver/src/test/java/restserver** for testkoden til både restserver og restapi.

### Domenelaget

Vår app handler om å kunne legge til fag man har et semester og hvor manage timer man bruker på å jobbe med faget. Man skal kunne oppdatere timeestimatet hele tiden. For å
håndtere tilhørende informasjon har vi Course og Semseter klasser. Disse ligger i [core.java.studytracker.core](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master/IT1901%2Fcore%2Fsrc%2Fmain%2Fjava%2Fstudytracker%2Fcore).

I domenelaget har vi klassene som omhandler logikk og objektene som applikasjoenen bruker. Dette laget fungerer uavhengig av resten av koden. 

### Brukergrensesnittlaget

I brukergrensesnittlaget har vi klasser og logikk som er knyttet opp mot visning av appen,
samt håndtering av informasjon brukeren legger inn. I vår app handler dette om å vise brukeren hvilke fag som er lagt inn, og hvor mye tid man har brukt på hvert fag. Det er 
også mulig å se statistikk over hvor mye tid man bruker på hvert fag. 
I brukergrensesnittlaget ligger også koden for å håndtere inputs fra brukeren. Koden til kjøringen av appen og kontrolleren finnes i [fxui.java.studytracker.ui](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master/IT1901%2Ffxui%2Fsrc%2Fmain%2Fjava%2Fstudytracker%2Fui).
Koden til fxml-filen finnes i [fxui.resources.studytracker.ui](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master/IT1901%2Ffxui%2Fsrc%2Fmain%2Fresources%2Fstudytracker%2Fui).


### Persistenslaget

I persistenslaget ligger klasser og logikk tilknyttet lagring og lesing fra fil. Filhåndteringen håndteres av JSON med Jacksonbibilioteket. 
Course og Semester har sine Serializers og Deserializers klasser som beskriver hvordan man skal skrive og lese objektene fra html-format. De finnes i [core.java.studytracker.json](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master/IT1901%2Fcore%2Fsrc%2Fmain%2Fjava%2Fstudytracker%2Fjson).

Via JSON har vi valgt implisitt lagring i stedet for dokumentmetafor. Det er mest funksjonelt for appen vår fordi brukeren kun har et semester å forholde seg til om gangen.
Dokumentmetafor med mulighet for å bytte mellom ulike semestere er unødvendig og vil få brukeren til å få flere ting å forholde seg til. Med implisitt lagring blir appen
enklere og mer brukervennlig.

### Serverlaget

I serverlaget ligger klasser og logikk tilknyttet lagring og henting av informasjon fra server. Serveren kjøres med jetty. Koden til serveren finnes i
[resterver/java/studytracker/restserver](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master/IT1901/restserver/src/main/java/studytracker/restserver).FXUI har RemoteSemesterAccess-klassen som 
sender http-request til serveren. Dette går via restapi, som er modulen som behandler slike forespørsler. Koden for hvordan vi behandler du ulike forespørselene ligger i 
[restapi/java/studytracker/restapi](https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2066/gr2066/-/tree/master/IT1901/restapi/src/main/java/studytracker/restapi)


### Klassediagram
Trykk på bildeikonet for å åpne diagrammet:
```plantuml
@startuml
interface SemesterListener
class Course
class Semester
class Controller
class ControllerStatistic
class RemoteSemesterAccess
class ModifyTime
class StudyTrackerPersistence
class SemesterService
class CourseResource

Course "1" -- "Semester: 0-4" Semester
Controller ..|> SemesterListener
Semester -- "SemesterListener *" SemesterListener
Controller --> RemoteSemesterAccess
ControllerStatistic --> RemoteSemesterAccess
Controller --> ModifyTime
StudyTrackerPersistence --> Semester
StudyTrackerPersistence --> Course
CourseResource --> StudyTrackerPersistence
RemoteSemesterAccess --> Semester
CourseResource --> Semester
SemesterService --> Semester
CourseResource --> Course
SemesterService --> CourseResource
SemesterService --> StudyTrackerPersistence

Semester : List<Course> courseList
Semester : Collection<SemesterListener> semesterListeners
Semester : void addCourse(Course)
Semester : void deleteCourse(String)
Semester : void addTimeToCourse(String, Double)
Semester : void resetSemester(Boolean)
Semester : void fireSemesterChanged()

Course : String courseName
Course : String timeSpent
Course : void addTime(Double)
Course : void setCourseName(String)

CourseResource : Semester semester
CourseResource : String courseName
CourseResource : Course course
CourseResource : StudyTrackerPersistence studyTrackerPersistence
CourseResource : Course getCourse()
CourseResource : Boolean addTimeToCourse(String)
CourseResource : Boolean deleteCourse()

SemesterService : Semester semester
SemesterService : Semester getSemester()
SemesterService : Boolean putSemester(Semester) 
SemesterService : Boolean resetSemester()
SemesterService : CourseResource getCourse(String)

SemesterListener : void semesterChanged(semester)

Controller : Semester semester
Controller : ObservableList<String> courseList
Controller : void addCourse()
Controller : void updateCourseViewAfterDeletion()
Controller : void addStudyHours()
Controller : void onResetButtonClick()
Controller : void onOpenStatisticsClick(ActionEvent)

ControllerStatistic : NumberAxis naHours
ControllerStatistic : CategoryAxis caHours
ControllerStatistic : BarChart<String, Number> barchart
ControllerStatistic : void onCloseStatisticsClick()

RemoteSemesterAccess : URI endpointUri
RemoteSemesterAccess : ObjectMapper objectMapper
RemoteSemesterAccess : Semester semester
RemoteSemesterAccess : Semester getSemester()
RemoteSemesterAccess : void putSemester(Semester)
RemoteSemesterAccess : void deleteSemester()
RemoteSemesterAccess : void addTimeToCourse(String, Double)
RemoteSemesterAccess : void deleteCourse(String)

ModifyTime : String addTime(String)
ModifyTime : String removeTime(String)
ModifyTime : List<Double> makeStudyHours(String, String)

StudyTrackerPersistence : void writeSemester(String, Semester)
StudyTrackerPersistence : Semester readSemester(Reader)

@enduml
```

### Pakkeløsningsdiagram
Trykk på bildeikonet for å åpne diagrammet:
```plantuml
@startuml

component restserver {
}

component core {
	package studytracker.core
	package studytracker.json
}
component jackson {
}

studytracker.json ..> jackson


component fxui {
	package studytracker.ui
}

component javafx {
	component fxml {
	}
}

fxui ..> javafx
fxui ..> fxml

studytracker.ui ..> studytracker.core

component restapi {
	package studytracker.restapi
}

studytracker.restapi ..> core

component restserver {
	package studytracker.restserver
}


component jersey {
}

component grizzly2 {
}

restserver ..> grizzly2
restserver ..> jersey

studytracker.restserver ..> studytracker.restapi


component integrationtests {
    package main.WEBAPP
}

main.WEBAPP ..> restserver
integrationtests ..> studytracker.core
integrationtests ..> fxui
studytracker.restserver ..> core

@enduml
```
