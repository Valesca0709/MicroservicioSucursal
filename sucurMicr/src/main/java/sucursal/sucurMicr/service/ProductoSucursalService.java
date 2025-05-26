package sucursal.sucurMicr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sucursal.sucurMicr.model.ProductoSucursal;
import sucursal.sucurMicr.repository.ProductoSucursalRepository;

@Service
public class ProductoSucursalService {

    @Autowired
    private ProductoSucursalRepository productoSucursalRepository;

    @Autowired
    private RestTemplate restTemplate;

    // ¡Ojo! Asegúrate de que esta URL termina en /
    private final String PRODUCTO_URL = "http://localhost:8070/api/v1/producto/";

    public List<ProductoSucursal> findAll() {
        return productoSucursalRepository.findAll();
    }

   public List<ProductoSucursal> findByIdProductoAndIdSucursal(int idProducto, int idSucursal) {

    // Filtra los datos locales (en la base) por idProducto e idSucursal
    return productoSucursalRepository.findByIdProductoAndIdSucursal(idProducto, idSucursal);
   }
}
