import com.example.project.model.*;
import org.junit.jupiter.api.*;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Used to test database connection
 */
public class TestDatabase {
    @Test
    public void testConnection() {
        Connection conn = SqliteConnection.getInstance();
        assertNotNull(conn);
    }
}
