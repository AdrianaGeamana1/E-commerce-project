package siit.model;

import lombok.Data;
import lombok.Value;

import java.time.LocalDate;

@Data
public class TestModel {
    final int id;
    final String name;
    final String phone;
    final String email;
     LocalDate birthDate;

    public static void main(String[] args) {
        TestModel testModel = new TestModel(1, "", "", "");

        testModel.getId();
        testModel.setBirthDate(null);
    }
}
