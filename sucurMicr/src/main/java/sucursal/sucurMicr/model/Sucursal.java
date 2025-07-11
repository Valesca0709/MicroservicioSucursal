package sucursal.sucurMicr.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sucursal")
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sucursal")
    private int idSucursal;
    
    @Column(length = 50, nullable = false)
    private String nombre;
    
    @Column(length = 100, nullable = false)
    private String direccion;
    
    @Column(length = 15, nullable = false, unique = true)
    private String telefono;
    
    @Column(length = 100, nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private boolean activa;

    @Column(length = 50, nullable = false)
    private String region;
}