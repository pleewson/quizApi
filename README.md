# ENGLISH #

# QuziApi

This is a Spring Boot application that interacts with the Quzi API to fetch quiz questions and store them in a local database via Hibernate.

## How to Run the Project

1. **Download the project:**
 ```bash
   git clone git@github.com:pleewson/quizApi.git
   ```

3. **Open the project in your IDE.**

4. **Create the database: You can use this script:**
```sql
   CREATE SCHEMA quzi_api_db;
   ```

4.**Fill in the credentials in the application.properties file: Update the database username and password.**

5.**Start the application: You can do this from your IDE or via the terminal:**
```bash
   mvn spring-boot:run
   ```
**Go to your browser to verify functionality: Visit http://localhost:8080/question.html to check if the application is working correctly.**


## Database Configuration Note
When using `spring.jpa.hibernate.ddl-auto=create-drop`,
the application may generate an error caused by "non-existent table in database." 
You can change the setting to `update` to solve this problem, or simply ignore the error, as it does not cause any issues in using the application.







# POLISH #

# QuziApi

To jest aplikacja Spring Boot, która współpracuje z API Quzi, aby pobierać pytania quizowe i przechowywać je w lokalnej bazie danych za pomocą Hibernate.

## Jak uruchomić projekt

1. **Pobierz projekt:**
 ```bash
   git clone git@github.com:pleewson/quizApi.git
   ```
2. **Otwórz projekt w swoim IDE.**

3. **Utwórz bazę danych: Możesz użyć tego skryptu:**
   ```sql
   CREATE SCHEMA quzi_api_db;
   ```


4. **Uzupełnij dane w pliku application.properties: Zaktualizuj nazwę użytkownika i hasło bazy danych.**

5. **Uruchom aplikację: Możesz to zrobić z IDE lub za pomocą terminala:**
```bash
   mvn spring-boot:run
   ```
**Przejdź do przeglądarki, aby zweryfikować funkcjonalność: Odwiedź http://localhost:8080/question.html, aby sprawdzić, czy aplikacja działa poprawnie.**

## Uwaga dotycząca konfiguracji bazy danych
Podczas korzystania z `spring.jpa.hibernate.ddl-auto=create-drop` aplikacja może wygenerować błąd spowodowany "nieistniejącą tabelą w bazie danych." 
Możesz zmienić ustawienie na `update`, aby rozwiązać ten problem, lub po prostu zignorować błąd, ponieważ nie powoduje on żadnych problemów w korzystaniu z aplikacji.
