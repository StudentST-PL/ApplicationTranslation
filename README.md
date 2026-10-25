# ApplicationTranslation — Projekt Lokalizacji Aplikacji Android

Aplikacja mobilna stworzona w ramach laboratoriów z Android Jetpack Compose, demonstrująca pełny proces lokalizacji interfejsu użytkownika (i18n / l10n), obsługę form gramatycznych liczby mnogiej, dynamiczną zmianę języka wewnątrz aplikacji oraz testowanie za pomocą pseudojęzyków.

---

## 🛠️ Zrealizowane Funkcjonalności

1. **Zadanie 1: Lokalizacja zasobów i etykiety aplikacji**
   - Dodano wsparcie dla języka angielskiego (domyślny) oraz polskiego (`values-pl/strings.xml`).
   - Sparametryzowano nazwę aplikacji w `AndroidManifest.xml` (`@string/app_name`), dzięki czemu nazwa pod ikoną na ekranie głównym dostosowuje się do języka systemu.
   - Wykorzystano parametry formatujące `%1$s` oraz `%2$d` w `stringResource()`.

2. **Zadanie 2: Losowanie żartów z tablicy zasobów (`string-array`)**
   - Zdefiniowano zestawy żartów w plikach XML za pomocą `<string-array name="jokes">`.
   - Wdrożono losowanie żartu przy użyciu funkcji Kotlin `.random()` i obsłudze stanu Compose.

3. **Zadanie 3: Licznik z obsługą liczby mnogiej (`plurals`)**
   - Zaimplementowano licznik kliknięć z dynamicznym dopasowaniem formy gramatycznej.
   - Uwzględniono różnice gramatyczne: 2 kategorie dla języka angielskiego (`one`, `other`) oraz 3 kategorie dla języka polskiego (`one`, `few`, `many`).

4. **Zadanie 4: Wbudowany przełącznik języka**
   - Utworzono konfigurację `res/xml/locales_config.xml`.
   - Zaimplementowano bezrestartowe przełączanie języka wewnątrz aplikacji z wykorzystaniem systemowego `LocaleManager` (dla Android 13+) oraz `AppCompatDelegate`.

5. **Zadanie 5: Testowanie pseudojęzykami (`en-XA`)**
   - Włączono flagę `isPseudoLocalesEnabled = true` w pliku `build.gradle.kts` (buildType `debug`).
   - Przeprowadzono weryfikację odporności układu UI na wydłużone ciągi znaków i akcenty diakrytyczne.

---

## ❓ Odpowiedzi do Sprawozdania (Punkt 2.1 z konspektu)

### 1. Ile miejsc w kodzie trzeba zmienić, aby dodać czwarty żart?
**Zero.** Kod w Kotlinie operuje na całej tablicy pobranej z zasobów (`stringArrayResource(R.array.jokes)`). Aby dodać nowy żart, wystarczy dopisać nowy element `<item>` w plikach `strings.xml` dla poszczególnych wersji językowych.

### 2. W jaki sposób wylosować jeden z trzech zasobów, skoro każdy ma inną nazwę?
Nie nadaje się osobnych nazw pojedynczym tekstom. Zamiast tego grupuje się je w jedną strukturę `<string-array>`, pobiera całą listę do aplikacji jako kolekcję i losuje element przy pomocy funkcji `.random()`.

### 3. Co się stanie, gdy tłumacz doda w jednym języku piąty żart, a w drugim nie?
Jeśli aplikacja próbowałaby odwołać się do elementu po konkretnym indeksie (np. indeks 4 dla piątego żartu), a w drugim języku tablica miałaby tylko 4 elementy, nastąpiłby błąd `IndexOutOfBoundsException` i awaria aplikacji (crash). W przypadku użycia `.random()` na pobranej tablicy danej wersji językowej błąd nie wystąpi, ale liczba dostępnych wariantów będzie różna w zależności od wybranego języka. Z tego powodu liczby elementów w tablicach XML powinny być zawsze równe w każdym pliku zasobów.
