package sucursal.sucurMicr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sucursal.sucurMicr.model.ProductoSucursal;

@Repository
public interface ProductoSucursalRepository extends JpaRepository<ProductoSucursal, Integer> {
     
   List<ProductoSucursal> findByIdProductoAndIdSucursal(int idProducto, int idSucursal);
   ProductoSucursal findByIdProductoSucursal(int idProductoSucursal);
   
} 
   