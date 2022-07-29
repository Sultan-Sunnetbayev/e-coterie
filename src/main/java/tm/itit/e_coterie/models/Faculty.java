package tm.itit.e_coterie.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.flywaydb.core.internal.database.DatabaseExecutionStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "faculties")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "full_name")
    @NotBlank(message = "full name faculty is mandatory")
    @NotEmpty(message = "full name facuty is empty")
    private String fullName;
    @Column(name = "short_name")
    @NotBlank(message = "short name faculty is mandatory")
    @NotEmpty(message = "short name faculty is empty")
    private String shortName;
    @Column(name = "created")
    @CreationTimestamp
    private Date created;
    @Column(name = "updated")
    @UpdateTimestamp
    private Date updated;

    @OneToMany(mappedBy = "faculty", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private List<DeanFaculty> deanFaculties;
    @OneToMany(mappedBy = "faculty", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private List<SpecialityStudent>specialityStudents;
}
