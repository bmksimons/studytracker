# Kildekode for persistenslaget

Domenelaget består av tre klasser Course, Semester og SemesterListener:

- **CourseDeserializer** - Deserialiserer et .json-fil til et Course objekt
- **CourseSerializer** - Serialiserer et Course objekt til en .json-fil
- **SemesterDeserializer** - Deserialiserer et .json til et Semester objekt
- **SemesterSerializer** - Serialiserer et Semester objekt til en .json-fil
- **StudyTrackerModule** - Kobler alle serializer - og deserilaizer-klassene til samme Module
- **StudyTrackerPersistence** - Kobler StudyTrackerModule til en objektmapper. Inneholder metodene writeSemester og readSemester som andre klasser kan bruke for å lagre og hente ting fra json.
