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
@Table(name = "producto_sucursal")
public class ProductoSucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProductoSucursal;
    
    @Column(nullable = false)
    private int idSucursal;
    
    @Column(nullable = false)
    private int idProducto; 
    
    @Column(nullable = false)
    private int cantidad;
    
    @Column(nullable = false)
    private double precio_unitario;
}
