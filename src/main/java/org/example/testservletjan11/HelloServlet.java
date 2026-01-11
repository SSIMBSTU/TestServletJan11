package org.example.testservletjan11;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/helloServlet")
public class HelloServlet extends HttpServlet {

    // Database credentials
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/student_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "1234";

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Read form values
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        double cgpa = Double.parseDouble(request.getParameter("cgpa"));

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create connection
            Connection con = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASS);

            // SQL Insert statement
            String sql =
                    "INSERT INTO students(id, name, cgpa) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            // Set values
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setDouble(3, cgpa);

            // Execute query
            ps.executeUpdate();

            // Close resources
            ps.close();
            con.close();

            // Response
            response.getWriter().println("Student Saved Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
