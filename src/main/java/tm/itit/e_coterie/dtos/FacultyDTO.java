package tm.itit.e_coterie.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacultyDTO {

    private int id;
    private String fullName;
    private String shortName;

    @Override
    public String toString() {
        return "FacultyDTO{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", shortName='" + shortName + '\'' +
                '}';
    }
}
