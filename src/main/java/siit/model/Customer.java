package siit.model;

import lombok.*;

import java.time.LocalDate;

//@EqualsAndHashCode
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Builder
public class Customer {
    private int id;
    private String name;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private String roles;
    private String username;
    private String password;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getRoles() {
        return roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object obj) {
        Customer other = (Customer) obj;
        return this.getName().equals(other.getName()) && this.getEmail().equals(other.getEmail()) && this.getUsername().equals(other.getUsername()) && this.getPassword().equals(other.getPassword());
    }
    @Override
    public int hashCode() {
        int result=0;
        result= result+(getName()!= null ? getName().hashCode() : 0);
        result= result+(getEmail()!= null ? getEmail().hashCode() : 0);
        result= result+(getUsername()!= null ? getUsername().hashCode() : 0);
        result= result+(getPassword()!= null ? getPassword().hashCode() : 0);
        return result;
    }
}
