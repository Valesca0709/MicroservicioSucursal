package sucursal.sucurMicr.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sucursal.sucurMicr.model.ProductoSucursal;
import sucursal.sucurMicr.service.ProductoSucursalService;


@RestController
@RequestMapping("/api/v1/productoSucursal")
public class ProductoSucursalController {
    @Autowired
    private ProductoSucursalService productoSucursalService;


    //Listar sucursal en el que aparece el producto y la sucursal en el que se encuentra el producto
   @GetMapping
    public ResponseEntity<List<ProductoSucursal>> getAllProductosSucursal() {
        List<ProductoSucursal> productos = productoSucursalService.findAll();
        if (productos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    // Buscar por nombre o id el producto
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoSucursal>> buscarProductobyId(
            @RequestParam int idProducto,
            @RequestParam int idSucursal) {
        
        List<ProductoSucursal> resultados = productoSucursalService.findByIdProductoAndIdSucursal(idProducto, idSucursal);
        
        if (resultados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(resultados);
    }
}
