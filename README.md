# 🚀 First JDBC Servlet Project

### JSP → Servlet → MySQL using Tomcat 11 (IntelliJ IDEA Ultimate)

This project demonstrates how to build your **first Java Web Application** using **JSP, Servlet, JDBC, Maven, and Tomcat 11**.
The application sends **Student ID, Name, and CGPA** from a JSP page to a **MySQL database** using a Servlet.

---

## 📌 Technologies Used

* Java JDK 17+
* IntelliJ IDEA Ultimate
* Apache Tomcat 11
* Jakarta Servlet (Jakarta EE 10)
* MySQL 8+
* Maven

---

## 📁 Project Structure

```
StudentServletApp
 ├── pom.xml
 └── src
     └── main
         ├── java
         │   └── org.example.studentservletapp
         │        └── StudentServlet.java
         └── webapp
             ├── index.jsp
             └── WEB-INF
                 └── web.xml
```

---

## 1️⃣ Database Setup (MySQL)

Login to MySQL and execute:

```sql
CREATE DATABASE student_db;
USE student_db;

CREATE TABLE students (
    student_id INT PRIMARY KEY,
    name VARCHAR(100),
    cgpa DOUBLE
);
```

---

## 2️⃣ Create Project in IntelliJ IDEA Ultimate

1. **File → New → Project**
2. Select **Jakarta EE**
3. Template: **Web Application**
4. Build System: **Maven**
5. JDK: **17 or higher**
6. Application Server: **Tomcat 11**
7. Project Name: `StudentServletApp`
8. Finish

---

## 3️⃣ Maven Dependencies (`pom.xml`)

Add the following dependencies:

```xml
<dependencies>

    <!-- Jakarta Servlet API for Tomcat 11 -->
    <dependency>
        <groupId>jakarta.servlet</groupId>
        <artifactId>jakarta.servlet-api</artifactId>
        <version>6.0.0</version>
        <scope>provided</scope>
    </dependency>

    <!-- MySQL JDBC Driver -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.3.0</version>
    </dependency>

</dependencies>
```

📌 Reload Maven after saving.

---

## 4️⃣ JSP Page (User Input Form)

📁 `src/main/webapp/index.jsp`

```jsp
<html>
<head>
    <title>Student Entry</title>
</head>
<body>

<h2>Add Student</h2>

<form action="<%= request.getContextPath() %>/StudentServlet"
      method="post">

    Student ID: <input type="text" name="id" required><br><br>
    Name: <input type="text" name="name" required><br><br>
    CGPA: <input type="text" name="cgpa" required><br><br>

    <input type="submit" value="Save Student">
</form>

</body>
</html>
```

📌 `request.getContextPath()` ensures correct deployment path.

---

## 5️⃣ Servlet (JDBC Logic)

📁 `src/main/java/org/example/studentservletapp/StudentServlet.java`

```java
package org.example.studentservletapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/student_db";
    private static final String DB_USER = "****";
    private static final String DB_PASS = "****";

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        double cgpa = Double.parseDouble(request.getParameter("cgpa"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASS);

            String sql =
                "INSERT INTO students (student_id, name, cgpa) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setDouble(3, cgpa);

            ps.executeUpdate();

            ps.close();
            con.close();

            response.getWriter().println(
                "<h3>Student saved successfully!</h3>");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println(
                "<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}
```

---

## 6️⃣ web.xml (Optional but Recommended)

📁 `src/main/webapp/WEB-INF/web.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
         https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
         version="6.0">
</web-app>
```

---

## 7️⃣ Configure Tomcat 11 in IntelliJ

1. **Run → Edit Configurations**
2. Add **Tomcat Server → Local**
3. Deployment → Add Artifact
4. Select `StudentServletApp:war exploded`
5. Apply → OK

---

## 8️⃣ Run the Application

Start Tomcat and open:

```
http://localhost:8080/StudentServletApp_war/index.jsp
```

Enter student details and submit.

---

## 9️⃣ Verify Data in Database

```sql
SELECT * FROM students;
```

---

## ✅ Learning Outcomes

* Understand JSP → Servlet → JDBC flow
* Learn Maven dependency management
* Learn Jakarta Servlet API
* Learn MySQL connectivity
* Understand context path handling

---

## 🚀 Next Enhancements

* MVC with DAO pattern
* Display student list in JSP
* Input validation
* Connection pooling
* Migrate to Spring Boot

---

### 👨‍🏫 Author

**Dr. Ziaur Rahman**, 
Professor, Dept of ICT, 
Mawlana Bhashani Science and Technology University

---
