# bysykkel
Javakode skrevet i eclipse.
Bruk gson-2.6.2.jar og json-simple-1.1.jar for å bygge (disse importeres i koden og må være tilgjengelige). I tillegg trengs standard JRE System library.
I ecplise lastes de ved å høyreklikke på prosjektet, velge 
"Build path" -> "Configure build path" -> "Add External JARs".

Jeg valgte å la num_bikes_available og num_locks_available være string fordi 
det foreløpig ikke var behov for matematiske operasjoner, og det var enklere slik.
Det var heller ikke behov for å lage objekter av stationstatus, og selv om det ville vært naturlig valgte jeg den enkleste løsningen.
