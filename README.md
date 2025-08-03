# Kliensalkalmazások házi feladat

Információk [itt](https://bmeviaubb03.github.io/laborok/hf/mobil)

### 2025.05.19
### SportTracker
### Für Mónika - OBEU6X
### mon.fur89@gmail.com

## Bemutatás

A házi feladat ötletek között találtam. Fogyni vágyók vagy egészségtudatos emberek lehetnének az app célközönsége, akik szeretnék naplószerűen vezetni és nyomon követni a fizikai aktivitásukat.

## Főbb funkciók

- Új edzések hozzáadása különböző típusokkal (futás, úszás, súlyzós edzés), időtartammal, dátummal (CreateNewWorkoutItemFragment).
- Szerkesztés nézet: a lista elemek adatai szerkeszthetők a ceruza ikonra kattintva, kivéve a kalória számítás, mert az automatikusan számítódik a kapott adatok alapján (EditWorkoutItemFragment).
- Edzések listázása RecyclerView segítségével (WorkoutListFragment, valamint WorkoutAdapter, amely összeköti a háttérben tárolt edzésadatokat a RecyclerView vizuális megjelenítésével).
- Lista nézetben lehetőség van az edzések módosítására, törlésére, új elem felvételére, valamint az edzés állapotának megjelölésére ("Done").
- Heti statisztikák oszlopdiagram (WeeklyStatsFragment): időtartam és elégetett kalória bontásban (2 db y tengelyen skálázva) vannak megjelenítve az elvégzett edzések, 
  alatta egy összesítéssel, hogy összesen mennyi időt töltöttünk mozgással, és hány kalóriát égettünk el.
  Lehetőség van a dátumtartomány kiválasztására, amely alapján frissül a diagram és az összesítés (a Load gombra kattintva).
- Kördiagram (ChartFragment): A ShoppingList laborhoz hasonló módon jeleníti meg az adatokat, az elvégzett, és a még el nem végzett edzéseket külön diagramon, 
  valamint az edzéstípusok egymáshoz való arányát (attól függően, hogy melyikre mennyi időt szántunk/akarunk szánni).
- Az adatokat Room adatbázisban tárolja, így azok perzisztensen megmaradnak (WorkoutItem, WorkoutItemDao, WorkoutListDatabase).

## Választott technológiák:

- Kötelezők: UI, RecyclerView, ViewBinding és Room
- Opcionálisak: fragmentek, MPAndroidChart
