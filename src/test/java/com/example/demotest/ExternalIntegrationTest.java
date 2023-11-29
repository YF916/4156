package com.example.demotest;

import org.junit.*;
import java.sql.*;

public class ExternalIntegrationTest {
    private static Connection connection;
    private static final String TEST_DATA = "Test Data";

    @BeforeClass
    public static void setUpClass() throws SQLException {
        // Setup the connection with the RDS MySQL database
        String url = "jdbc:mysql://database-2.cvlxq8ccnbut.us-east-1.rds.amazonaws.com:3306/users";
        String username = "admin";
        String password = "Natalie3399!";
        connection = DriverManager.getConnection(url, username, password);
    }

    @Before
    public void setUp() throws SQLException {
        // Create a temporary table before each test
        try (Statement stmt = connection.createStatement()) {
            String createTableSQL = "CREATE TEMPORARY TABLE IF NOT EXISTS temp_test_table (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255))";
            stmt.execute(createTableSQL);
        }
    }

    @Test
    public void testInsert() throws SQLException {
        String insertSQL = "INSERT INTO temp_test_table (name) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, "Test Name");
            int affectedRows = pstmt.executeUpdate();
            Assert.assertEquals(1, affectedRows);
        }
    }

    // CRUD

    @Test
    public void testRead() throws SQLException {
        testInsert(); // Ensure there's data to read
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM temp_test_table WHERE name = 'Test Name'");
            Assert.assertTrue(rs.next());
            Assert.assertEquals("Test Name", rs.getString("name"));
        }
    }

    @Test
    public void testUpdate() throws SQLException {
        testInsert(); // Ensure there's data to update
        String updateSQL = "UPDATE temp_test_table SET name = ? WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setString(1, "Updated Name");
            pstmt.setString(2, "Test Name");
            int affectedRows = pstmt.executeUpdate();
            Assert.assertEquals(1, affectedRows);
        }
    }

    @Test
    public void testDelete() throws SQLException {
        testInsert(); // Ensure there's data to delete
        String deleteSQL = "DELETE FROM temp_test_table WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setString(1, "Test Name");
            int affectedRows = pstmt.executeUpdate();
            Assert.assertEquals(1, affectedRows);
        }
    }

    @Test
    public void testPersistenceThroughRestart() throws SQLException {
        // Step 1: Insert Data
        insertTestData();

        // Step 2: Manually restart the RDS instance or do it via script.

        // Step 3: Verify Data Persistence
        verifyTestData();
    }

    private void insertTestData() throws SQLException {
        String insertSQL = "INSERT INTO temp_test_table (name) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, TEST_DATA);
            pstmt.executeUpdate();
        }
    }

    private void verifyTestData() throws SQLException {
        String selectSQL = "SELECT * FROM temp_test_table WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, TEST_DATA);
            ResultSet rs = pstmt.executeQuery();
            Assert.assertTrue("Test data was not persisted through restart", rs.next());
            Assert.assertEquals("Data integrity check failed after restart", TEST_DATA, rs.getString("name"));
        }
    }

    @After
    public void tearDown() throws SQLException {
        // Drop the temporary table after each test
        // Note: With TEMPORARY tables, this step might not be necessary as they are session-specific and are dropped when the session ends
        try (Statement stmt = connection.createStatement()) {
            String dropTableSQL = "DROP TEMPORARY TABLE IF EXISTS temp_test_table";
            stmt.execute(dropTableSQL);
        }
    }

    @AfterClass
    public static void tearDownClass() throws SQLException {
        // Close database connection
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
