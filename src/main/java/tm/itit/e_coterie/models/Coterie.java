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
@Table(name = "coteries")
public class Coterie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotBlank(message = "coterie name is mandatory")
    @NotEmpty(message = "coterie name is empty")
    private String name;
    @Column(name = "image_path")
    private String imagePath;
    @Column(name = "created")
    @CreationTimestamp
    private Date created;
    @Column(name = "updated")
    @UpdateTimestamp
    private Date updated;

    @ManyToMany(mappedBy = "coteries", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Student>students;
    @ManyToMany(mappedBy = "coteries", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Teacher>teachers;

}
