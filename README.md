<img src="./images/dokmqjcw.png" style="width:0.87153in;height:0.95896in" />

**KAUNO** **TECHNOLOGIJOS** **UNIVERSITETAS**

**INFORMATIKOS** **FAKULTETAS**

**T120B105** **Saityno** **Taikomųjų** **Programų** **Projektavimas**

Projekto ataskaita

**Atliko:** <br>
IFF – 1/6 gr. stud. Kasparas Putrius <br> **Priėmė:** <br>
lekt. Kiudys Eligijus

**KAUNAS** **2024**

# 1. Sprendžiamo uždavinio aprašymas

### 1.1. Sistemos paskirtis

Kuriamo projekto tikslas – filmų peržiūros svetainė, kurioje vartotojai gali peržiūrėti iš są-rašo pasirinkto filmo informaciją, dalyvauti pasirinkto filmo diskusijose, ir jose pasidalinti savo mintimis.

Veikimo principas – vartotojai gali peržiūrėti filmų sąrašą ir neužsiregistravę. Neregistruoti vartotojai taip pat gali peržiūrėti pasirinkto filmo informaciją, peržiūrėti jų diskusijas, bei dis-kusijų komentarus. Registruoti vartotojai gali skurti, trinti ir redaguoti savo komentarus, ar pra-dėtas diskusijas. Administratorius kuria, redaguoja ir trina filmus, komentarus ir diskusijas.

### 1.2. Funkciniai reikalavimai

Neregistruotas sistemos naudotojas gali:

- Peržiūrėti filmų sąrašą
- Peržiūrėti pasirinkto filmo informaciją
- Peržiūrėti pasirinkto filmo diskusijos komentarus
- Registruotis
- Prisijungti

Registruotas sistemos naudotojas gali:

- Atsijungti
- Peržiūrėti kitų naudotojų profilį
- Peržiūrėti ir redaguoti savo profilį
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
- Keisti naudotojo rolę
- Atsijungti

### 1.3. Sistemos architektūra

Sistemos technologinės dalys:

- Kliento pusė (angl. _Front-End_) – naudojamas React 18.0, Typescript 5.6.2, Material UI.
- Serverio pusė (angl _Back-End_) – naudojamas Spring Boot, Java 21, Hibernate
- Duomenų bazė: PostgreSQL

Debesų technologijos naudojamos projekte:

- Web‘inės aplikacijos pakalaikymas panaudojant AWS S3 Storage serverį
- Serverinės dalis bus patalpinta AWS EC2 serveryje.
- Duomenų bazė bus patalpinta AWS RDS serveryje.

Vartotojai, naudodamiesi kliento dalimi, atlieka užklausas į serverio pusę, kuri komunikuoja su duomenų baze, kurioje yra sukuriami, atnaujinami, gaunami, ištrinami duomenis.

Sistemos architektūra pavaizduota žemiau:

<img src="images/bf10gp41.png" style="width:6.3in;height:4.96528in" /><br>

# 2. Naudotojo sąsaja

Šioje dalyje pateikti langų _wireframe_ ir kaip tie langai yra įgyvendinami puslapyje. Taip pat pateikta kaip kai kurie puslapiai atrodo mobiliojoje versijoje.

### 2.1. Filmų langas

<img src="images/uzxixpp4.png" style="width:5.50208in;height:4.85208in" />
<img src="images/svve3v2r.png" style="width:6.3in;height:3.175in" />

### 2.2.Filmų langas mobili versija

<img src="images/bpanud3k.png" style="width:2.64375in;height:7.49347in" />
<img src="images/ezwrzl4q.png" style="width:3.30542in;height:7.1368in" />

### 2.3.Filmo informacijos langas

<img src="images/hxparwrh.png" style="width:6.295in;height:5.55139in" />
<img src="images/ehj1hxa0.png" style="width:6.3in;height:3.18889in" />

### 2.4.Filmo informacijos lango mobili versija

<img src="images/uorszuit.png" style="width:6.29514in;height:8.44694in" />
<img src="images/mhpi2hbw.png" style="width:2.58472in;height:8.48847in" />
<img src="images/2mio2flo.png" style="width:2.79653in;height:5.01194in" />

### 2.5.Filmo diskusijų langas

<img src="images/pvv0rcnm.png" style="width:6.295in;height:5.55139in" />
<img src="images/4lq33vpt.png" style="width:6.03805in;height:3.08958in" />

### 2.6.Filmo diskusijų lango mobili versija

<img src="images/fwo5d5on.png" style="width:6.29514in;height:7.03944in" />
<img src="images/hvnlwwrq.png" style="width:3.04792in;height:5.51278in" />
<img src="images/vibfrpbr.png" style="width:3.0118in;height:5.5118in" />

### 2.7.Naudotojų sąrašo langas

<img src="images/pwb1x0em.png" style="width:6.295in;height:5.55139in" />
<img src="images/whu1sqwp.png" style="width:6.29861in;height:3.17917in" />

### 2.8.Naudotojų sąrašo lango mobili versija

<img src="images/jrynioqk.png" style="width:5.55139in;height:6.78319in" />
<img src="images/cd2r4ypt.png" style="width:6.3in;height:6.4125in" />

### 2.9.Naudotojo informacijos langas

<img src="images/yuq2xafp.png" style="width:6.295in;height:5.55139in" />
<img src="images/5gsh5p0m.png" style="width:5.80972in;height:2.91569in" />

### 2.10. Naudotojo informacijos atnaujinimo langas

<img src="images/k0i2bggm.png" style="width:6.295in;height:5.55139in" />
<img src="images/u3e5m1jv.png" style="width:6.3in;height:3.1618in" />

### 2.11. Prisijungimo langas

<img src="images/4ifc4liw.png" style="width:2.9275in;height:6.575in" />
<img src="images/tr0a1kuk.png" style="width:3.30625in;height:5.41347in" />

### 2.12. Registracijos langas

<img src="images/1nuiobcn.png" style="width:2.9275in;height:6.575in" />
<img src="images/3dloujsl.png" style="width:2.70139in;height:6.65931in" />

### 2.13. Įrašo trynimo įspėjimo langelis

<img src="images/kzzixh50.png" style="width:2.8243in;height:0.94375in" />
<img src="images/erscks2b.png" style="width:6.3in;height:5.18264in" />

### 2.14. Filmo kūrimo ir redagavimo langelis

<img src="images/kye0iqwj.png" style="width:2.39222in;height:2.47986in" />
<img src="images/ccdnai11.png" style="width:3.07431in;height:5.76042in" />
<img src="images/ii4jedha.png" style="width:6.3in;height:3.16805in" />

### 2.15. Diskusijos kūrimo ir redagavimo langelis

<img src="images/aiyu15t5.png" style="width:2.82319in;height:1.67917in" />
<img src="images/lfsryrqz.png" style="width:6.3in;height:3.18542in" />

# 3. API specifikacija

### 3.1.Gauti visus sistemos naudotojus

<img src="images/image-2.png" alt="alt text" />
<img src="images/lsqgwpje.png" style="width:5.40139in;height:4.31722in" />
<img src="images/5ftgcmuo.png" style="width:6.29875in;height:3.375in" />
<img src="images/dbesequ1.png" style="width:6.3in;height:2.94167in" />

### 3.2.Gauti vieną sistemos naudotoja

<img src="images/image-1.png" alt="alt text" />
<img src="images/ao2yyeaa.png" style="width:6.3in;height:3.80278in" />
<img src="images/0psnl2xk.png" style="width:6.3in;height:3.10278in" />
<img src="images/z213bygw.png" style="width:6.3in;height:3.33055in" />
<img src="images/ldmao1sv.png" style="width:6.29875in;height:3.53542in" />

### 3.3.Užsiregistruoti

<img src="images/image-3.png" alt="alt text" />
<img src="images/vxtxu1ga.png" style="width:5.77889in;height:3.51181in" />
<img src="images/3szxuqud.png" style="width:5.65347in;height:3.2193in" />
<img src="images/ia3d55by.png" style="width:6.29875in;height:3.30903in" />

### 3.4.Redaguoti profilį

<img src="images/image-4.png" alt="alt text" />
<img src="images/crtq4zc3.png" style="width:5.15556in;height:3.10681in" />
<img src="images/nuad0h5q.png" style="width:5.18764in;height:2.69792in" />
<img src="images/b5x431oa.png" style="width:5.30139in;height:3.08194in" />

### 3.5.Prisijungimas

<img src="images/image-5.png" alt="alt text" />
<img src="images/drbiar4t.png" style="width:6.3in;height:3.44514in" />
<img src="images/ngob40i2.png" style="width:5.40681in;height:2.82917in" />
<img src="images/0dzxofhf.png" style="width:5.37167in;height:2.91736in" />
<img src="images/o1g4wdnq.png" style="width:5.61722in;height:2.65694in" />
<img src="images/2b4cvps4.png" style="width:6.3in;height:3.07153in" />
<img src="images/b1b0v2ol.png" style="width:6.29889in;height:3.875in" />

### 3.6.Ištrinti sistemos naudotoją

<img src="images/image-6.png" alt="alt text" />
<img src="images/fdhkctge.png" style="width:6.29847in;height:2.9in" />

### 3.7.Redaguoti sistemos naudotojo rolę

<img src="images/image-7.png" alt="alt text" />
<img src="images/rimmhbo0.png" style="width:6.3in;height:3.74375in" />
<img src="images/v3m2jgnf.png" style="width:6.29875in;height:3.69028in" />

### 3.8.Gauti visus sistemos filmus

<img src="images/image-8.png" alt="alt text" />
<img src="images/ry1rfiem.png" style="width:6.3in;height:4.77778in" />

### 3.9.Gauti tam tikrą filmą

<img src="images/image-9.png" alt="alt text" />
<img src="images/xyiv3cjp.png" style="width:6.3in;height:4.26181in" />

### 3.10. Sukurti naują filmą

<img src="images/image-11.png" alt="alt text" />
<img src="images/mbhqag40.png" style="width:6.3in;height:3.89375in" />

### 3.11. Redaguoti filmą

<img src="images/image-12.png" alt="alt text" />
<img src="images/zvj34rhh.png" style="width:6.3in;height:3.60903in" />

### 3.12. Ištrinti filmą

<img src="images/image-13.png" alt="alt text" />
<img src="images/i4ph4krk.png" style="width:6.29875in;height:3.30764in" />

### 3.13. Gauti visas filmo diskusijas

<img src="images/image-14.png" alt="alt text" />
<img src="images/ke45upko.png" style="width:6.29917in;height:5.12014in" />

### 3.14. Sukurti naują filmo diskusiją

<img src="images/image-15.png" alt="alt text" />
<img src="images/rc4ok5am.png" style="width:6.3in;height:3.47917in" />

### 3.15. Redaguoti filmo diskusiją

<img src="images/image-16.png" alt="alt text" />
<img src="images/rqvunt11.png" style="width:5.61194in;height:3.57361in" />

### 3.16. Ištrinti filmo diskusiją

<img src="images/image-17.png" alt="alt text" />
<img src="images/pw4vft5a.png" style="width:5.49653in;height:2.95972in" />

### 3.17. Gauti visus diskusijos komentarus

<img src="images/image-18.png" alt="alt text" />
<img src="images/sbb0mfkq.png" style="width:6.29903in;height:4.80903in" />

### 3.18. Kurti naują diskusijos komentarą

<img src="images/image-19.png" alt="alt text" />
<img src="images/mwekyqpv.png" style="width:6.29903in;height:4.21181in" />

### 3.19. Gauti komentarą

<img src="images/image-20.png" alt="alt text" />
<img src="images/4qkqu1hh.png" style="width:6.3in;height:5.04028in" />

### 3.20. Redaguoti komentarą

<img src="images/image-21.png" alt="alt text" />
<img src="images/j4rpajzq.png" style="width:5.66792in;height:4.43333in" />

### 3.21. Ištrinti diskusijos komentarą

<img src="images/image-22.png" alt="alt text" />
<img src="images/w1i4m41l.png" style="width:6.29903in;height:4.25764in" />

### 3.22. Filmo nuotraukos įkėlimas

<img src="images/image-23.png" alt="alt text" />
<img src="images/m3xwbgv3.png" style="width:6.3in;height:4.67431in" />

### 3.23. Filmo nuotraukos gavimas

<img src="images/image-24.png" alt="alt text" />
<img src="images/suczuatu.png" style="width:6.3in;height:5.78264in" />

### 3.24. Pakeisti filmo nuotrauką

<img src="images/image-25.png" alt="alt text" />
<img src="images/44za4yfv.png" style="width:6.3in;height:5.97917in" />

# 4. Išvados

Puslapio kūrimas iš dalies vyko kriokliu, kas nėra geras būdas. Pradedant darbus nuo _backend_ ir baigiant _fron-tend_, atsiranda daug problemų ypač darbų gale, kuomet suprantama, kad metodai ne tokie, ne taip implementuoti ar iš viso nereikalingi. Kuriant sprintais, kuomet sukuriama po vieną _endpoint_ _frontend_ ir _backend_ dalyse, galima žymiai greičiau pastebėti trūkumus ar klaidas vieonoje ar kitoje dalyje.

Kadangi kūriau abi dalis kartu, greičiau pastebėjau, kuriose vietose yra problemos ir jas patvarkiau. Projektas smagus, tikrai išmokau daug dalykų, bet jei reikėtų viską daryti iš naujo, tai daryčiau daug ką kitaip.
