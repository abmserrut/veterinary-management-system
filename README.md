# 🐾 Veterinary Management System

Academic project developed in Java applying Object-Oriented Programming (OOP), JDBC, and MySQL database integration.

---

## 🛠️ Technologies
* **Java 17**
* **MySQL 8**
* **JDBC**
* **VS Code**
* **Git & GitHub**

## 🧠 Software Engineering Concepts
* Encapsulation
* Inheritance *(3-level hierarchy)*
* Polymorphism
* Abstract Classes
* Interfaces
* Enums with Business Logic
* Layered Architecture
* DAO Pattern
* Singleton Pattern

## 📂 Project Structure

```text
veterinaria/
├── src/
│   ├── Main.java              # Entry point
│   ├── model/                 # Business logic and OOP hierarchy
│   │   ├── Persona.java       # Base class (Level 1)
│   │   ├── Empleado.java      # Extends Persona (Level 2)
│   │   ├── Veterinario.java   # Extends Empleado (Level 3)
│   │   ├── Recepcionista.java # Extends Empleado (Level 3)
│   │   ├── Paciente.java      # Encapsulated with Interfaces
│   │   ├── Servicio.java      # Abstract Class
│   │   ├── Consulta.java      # Extends Servicio
│   │   └── enums/             # Enums with business logic
│   │       ├── TipoEspecie.java
│   │       └── EstadoConsulta.java
│   ├── interfaces/            # Java interfaces (Contracts)
│   │   ├── IDAO.java
│   │   ├── Auditable.java
│   │   └── Reportable.java
│   ├── dao/                   # Data Access Objects (SQL)
│   │   ├── PacienteDAO.java
│   │   ├── EmpleadoDAO.java
│   │   └── ConsultaDAO.java
│   └── db/                    # MySQL connection (Singleton)
│       └── ConexionDB.java
├── lib/                       # External libraries (.jar)
│   └── mysql-connector-j-X.X.X.jar
└── database/                  # SQL scripts
    └── veterinaria_db.sql
```

## ✨ Features
* Patient Management
* Employee Management
* Veterinary Services
* Database Integration with MySQL
* CRUD Operations

## 🗄️ Database
The project includes the SQL script for database creation and initialization. You can find it here: 

`database/veterinaria_db.sql`

---

## 🚀 How to Run

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/abmserrut/veterinary-management-system.git](https://github.com/abmserrut/veterinary-management-system.git)
   ```

2. **Database Setup:**
   * Open MySQL Workbench or your preferred SQL client.
   * Run the script located in `database/veterinaria_db.sql` to create and initialize the database.

3. **Configure Database Connection:**
   * Open `src/db/ConexionDB.java`.
   * Update the connection credentials (`user` and `password`) to match your local MySQL configuration.

4. **Setup Environment:**
   * Ensure you have **Java 17** installed.
   * Add the `mysql-connector-j-X.X.X.jar` file from the `lib/` folder to your project's classpath / build path in VS Code.

5. **Run the Application:**
   * Compile and execute `src/Main.java`.

---

## 👨‍💻 Author
**Abraham Moreno** *Software Development Student at ITSE*
