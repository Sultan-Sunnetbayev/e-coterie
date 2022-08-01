package tm.itit.e_coterie.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Table(name = "pulpits")
public class Pulpit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "full_name")
    @NotBlank(message = "pulpit full name is mandatory")
    @NotEmpty(message = "pulpit full name is empty")
    private String fullName;
    @Column(name = "short_name")
    @NotBlank(message = "pulpit short name is mandatory")
    @NotEmpty(message = "pulpit short name is empty")
    private String shortName;
    @Column(name = "created")
    @CreationTimestamp
    private Date created;
    @Column(name = "updated")
    @UpdateTimestamp
    private Date updated;

    @OneToMany(mappedBy = "pulpit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GovernorPulpit>governorPulpits;
    @OneToMany(mappedBy = "pulpit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Coterie>coteries;

}
