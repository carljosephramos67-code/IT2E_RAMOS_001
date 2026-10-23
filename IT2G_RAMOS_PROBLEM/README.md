# Gym Membership Registration & Roster System

Java 17 console application using Maven and SQLite.

## Run in IntelliJ IDEA
1. Extract the ZIP and open the project folder.
2. Set the Project SDK to Java 17 or newer.
3. Open `pom.xml` and load Maven changes.
4. Run `src/main/java/com/gymmembership/Main.java`.
5. Use menu option 1 to add a member and option 2 to view saved members.

## Database verification
Open `scenario.db` in DB Browser for SQLite or SQLiteStudio. The table is `gym_members`, with columns `id`, `full_name`, `tier`, `monthly_fee`, and `status`. Set IntelliJ's run configuration working directory to the project root so the application uses the included database.

## Architecture
- All JDBC/SQL operations are in `dao` classes.
- Inserts and selects use `PreparedStatement`.
- SQLite `PRAGMA foreign_keys = ON` is enabled on each connection.
- `ScenarioEntity` is a Java record with five attributes.
- Input uses `Scanner.nextLine()` consistently and validates tier, status, and fee.

## GitHub naming
Use your instructor's exact format `[SECTION]_[LASTNAME]_PROBLEM`. Rename the repository if your actual section or surname differs from the example folder name.
