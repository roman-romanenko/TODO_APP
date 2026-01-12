# TODO_APP 📝

![Java](https://img.shields.io/badge/Java-25-brightgreen)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-blue)
![Maven](https://img.shields.io/badge/Maven-4.0.1-orange)
![Tests](https://img.shields.io/badge/Tests-Passing-brightgreen)


Dies ist ein kleines Spring Boot Projekt für ein **TODO-Backend**, das MongoDB verwendet und ein REST-API für CRUD-Operationen bereitstellt.

---

## 🛠 Features

- CRUD für Todos (Create,Read, Update, Delete)  
- Persistenz über **MongoDB**  
- **Integrationstests** mit MockMvc & Flapdoodle Embedded MongoDB  

---

## ⚡ Technologien

- Java 25  
- Spring Boot 4.0.1  
- Spring Data MongoDB  
- Embedded MongoDB (Flapdoodle) für Tests  
- JUnit 5, Mockito für Unit- und Integrationstests  
- Maven als Build-Tool  

---

## 🚀 Setup

1. **Repository klonen**  

```bash
git clone https://github.com/roman-romanenko/TODO_APP
cd TODO_APP
```

2 .**Abhängigkeiten installieren & Build**
```bash
mvn clean install
```

3.App starten
```bash
mvn spring-boot:run
```
Die API ist erreichbar unter: [http://localhost:8080/api/todo](http://localhost:8080/api/todo)

---

## 📚 API Endpoints

| Methode | Endpoint           | Beschreibung                  | Statuscodes          |
|---------|------------------|-------------------------------|-------------------|
| GET     | /api/todo        | Alle Todos abrufen            | 200 OK            |
| GET     | /api/todo/{id}   | Todo nach ID abrufen          | 200 OK / 404 Not Found |
| POST    | /api/todo        | Neues Todo erstellen          | 201 Created       |
| PUT     | /api/todo/{id}   | Bestehendes Todo aktualisieren| 200 OK            |
| DELETE  | /api/todo/{id}   | Todo löschen                  | 204 No Content    |

---

## 🧪 Tests

Integrationstests mit **MockMvc** und **Embedded MongoDB**.  

Tests führen CRUD-Operationen durch und prüfen **HTTP Status** & **JSON-Inhalte**.

```bash
mvn test
```

Für Tests: Flapdoodle Embedded MongoDB ist bereits konfiguriert

## 💾 Beispiel JSON

**Todo Objekt:**

```json
{
  "id": "1",
  "description": "Learn Spring",
  "status": "OPEN"
}
```

**TodoDTO (für Create/Update):**

```json
{
  "description": "Learn Spring",
  "status": "DONE"
}
```



