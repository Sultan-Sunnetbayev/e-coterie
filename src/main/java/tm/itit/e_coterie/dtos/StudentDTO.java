package tm.itit.e_coterie.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tm.itit.e_coterie.models.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {

    private int id;
    private String name;
    private String surname;
    private String patronymicName;
    private String email;
    private String role;
    private String imagePath;
    private Gender gender;

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymicName='" + patronymicName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", gender=" + gender +
                '}';
    }
}
