package g26.eDucaApp.Model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;



public class TeacherUpdateRequest {
    private Map<String, String> updates;
    private String[] classes;

    // Constructors, if needed

    // Setter for updates
    public void setUpdates(Map<String, String> updates) {
        this.updates = updates;
    }

    // Getter methods for individual properties
    public String getName() {
        return updates != null ? updates.get("name") : null;
    }

    public String getEmail() {
        return updates != null ? updates.get("email") : null;
    }

    public String getSchool() {
        return updates != null ? updates.get("school") : null;
    }

    public String getPassword() {
        return updates != null ? updates.get("password") : null;
    }

    public List<Subject> getSubjects() {
        // Implement logic to retrieve subjects
        return null;
    }

    public String[] getClasses() {
        return classes;
    }
}
