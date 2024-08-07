package group.adminservice.database.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@Entity
@Table(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var employee_id: Long = 0,
    @Column(name = "email")
    var email: String = "",
    @Column(name = "password")
    var password: String = "",
    @ManyToOne
    @JoinColumn(name = "admin_id")
    @JsonBackReference
    val admin: Admin? = null,
    @OneToMany(mappedBy = "employee")
    @JsonManagedReference
    var usedDays: List<UsedDays> = mutableListOf(),
    @OneToMany(mappedBy = "employee")
    @JsonManagedReference
    var vacations: List<Vacation> = mutableListOf(),
) {
    constructor(id: Long, email: String, password: String) : this(id, email, password, null, emptyList())
}

