package tm.itit.e_coterie.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "governor_coteries")
public class GovernorCoterie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "coterie_id", referencedColumnName = "id")
    private Coterie coterie;
    @Column(name = "created")
    @CreationTimestamp
    private Date created;
    @Column(name = "updated")
    @UpdateTimestamp
    private Date updated;

}
