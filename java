import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Servlet URL mapping
@WebServlet("/registerStudent")
public class StudentRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/course_registration";
    private static final String DB_USER = "root"; // Replace with your DB username
    private static final String DB_PASSWORD = ""; // Replace with your DB password

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get form data
        String studentName = request.getParameter("studentName");
        String studentRegNo = request.getParameter("studentRegNo");
        String password = request.getParameter("password");

        // Response setup
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Database connection
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // SQL query
            String sql = "INSERT INTO students (student_name, student_reg_no, password) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, studentName);
            statement.setString(2, studentRegNo);
            statement.setString(3, password);

            // Execute query
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                out.println("<h3>Registration successful!</h3>");
            } else {
                out.println("<h3>Registration failed!</h3>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}
