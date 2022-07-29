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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "emails")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotBlank(message = "email name is mandatory")
    @NotEmpty(message = "email name is empty")
    private String name;
    @Column(name = "password")
    @NotBlank(message = "email password is mandatory")
    @NotEmpty(message = "email password is empty")
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "status")
    private boolean status;
    @Column(name = "created")
    @CreationTimestamp
    private Date created;
    @Column(name = "updated")
    @UpdateTimestamp
    private Date updated;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
    @OneToOne(mappedBy = "email",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Admin admin;
}
