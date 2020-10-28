package BatchProcessing.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data @AllArgsConstructor @Builder @Setter @Getter
public class Collabotor {

    @Id
    private UUID collaboratorId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private int age;
    private String job;
    private Character classStatus;
    private LocalDate integrationDate;
    private String status;
    private LocalDateTime lastUpdate;

    public Collabotor() {
    }

    public UUID getCollaboratorId() {
        return collaboratorId;
    }

    public void setCollaboratorId(UUID collaboratorId) {
        this.collaboratorId = collaboratorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Character getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(Character classStatus) {
        this.classStatus = classStatus;
    }

    public LocalDate getIntegrationDate() {
        return integrationDate;
    }

    public void setIntegrationDate(LocalDate integrationDate) {
        this.integrationDate = integrationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
