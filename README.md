# Sprendžiamo uždavinio aprašymas

## 1. Sistemos paskirtis

Kuriamo projekto tikslas – filmų peržiūros svetainė, kurioje vartotojai gali peržiūrėti iš są-rašo pasirinkto filmo informaciją, dalyvauti pasirinkto filmo diskusijose, ir jose pasidalinti savo mintimis.

Veikimo principas – vartotojai gali peržiūrėti filmų sąrašą ir neužsiregistravę. Neregistruoti vartotojai taip pat gali peržiūrėti pasirinkto filmo informaciją, peržiūrėti jų diskusijas, bei dis-kusijų komentarus. Registruoti vartotojai gali kurti, trinti ir redaguoti savo komentarus, ar pra-dėtas diskusijas. Administratorius kuria, redaguoja ir trina filmus, komentarus ir diskusijas.

## 2. Funkciniai reikalavimai

Neregistruotas sistemos naudotojas gali:

- Peržiūrėti filmų sąrašą
- Peržiūrėti pasirinkto filmo informaciją
- Peržiūrėti pasirinkto filmo diskusijos komentarus
- Registruotis
- Prisijungti

Registruotas sistemos naudotojas gali:

- Atsijungti
- Redaguoti savo profilį
- Peržiūrėti savo profilį
- Peržiūrėti kitų naudotojų profilį
- Kurti diskusijas
- Ištrinti savo sukurtą diskusiją
- Rašyti diskusijų komentarus
- Redaguoti ir trinti savo diskusijų komentarus

Sistemos administratorius papildomai gali daryti:

- Kurti naujus filmus
- Redaguoti ir trinti bet kurį sistemoje esantį filmą
- Trintį bet kurią filmo diskusiją
- Trinti bet kurį diskusijos komentarą
- Peržiūrėti visus sistemoje užsiregistravusius vartotojus
- Pašalinti sistemoje užsiregistravusį naudotoją

## 3. Sistemos architektūra

Sistemos technologinės dalys:

- Kliento pusė (angl. Front-End) – naudojamas React 18.0, Typescript 5.6.2, Material UI
- Serverio pusė (angl Back-End) – naudojamas Spring Boot, Java 21, Hibernate
- Duomenų bazė: PostgreSQL

Vartotojai, naudodamiesi kliento dalimi, atlieka užklausas į serverio pusę, kuri komunikuo-ja su duomenų baze, kurioje yra sukuriami, atnaujinami, gaunami, ištrinami duomenis.
