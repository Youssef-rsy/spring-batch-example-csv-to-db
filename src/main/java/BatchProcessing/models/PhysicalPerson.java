package BatchProcessing.models;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor @NoArgsConstructor
public class PhysicalPerson {

    private String firstName;
    private String lastName;
    private Date birthDate;
    private String job;
    private Character classStatus;
    private String status;
    private Date integrationDate;

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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getIntegrationDate() {
        return integrationDate;
    }

    public void setIntegrationDate(Date integrationDate) {
        this.integrationDate = integrationDate;
    }
}
