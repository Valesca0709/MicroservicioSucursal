package sucursal.sucurMicr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sucursal.sucurMicr.model.Sucursal;
import sucursal.sucurMicr.service.SucursalService;

@RestController
@RequestMapping("/api/v1/sucursal")
public class SucursalController {
    @Autowired
    private SucursalService sucursalService;
    
    @GetMapping
    public ResponseEntity<List<Sucursal>> getSurcursal(){
        List<Sucursal> sucursal =sucursalService.findAll();
        if(sucursal.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(sucursal,HttpStatus.OK);  
    }

    //Busca usuario por id
    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> getSucursalById(@PathVariable int id) {
        return sucursalService.findById(id)
                .map(sucursal-> new ResponseEntity<>(sucursal, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    // Crear una nueva suscursal
    @PostMapping
    public ResponseEntity<Sucursal> createSucursal(@RequestBody Sucursal sucursal) {
        try {
            Sucursal nuevaSurcursal = sucursalService.save(sucursal);
            return new ResponseEntity<>(nuevaSurcursal, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //que sea haga un update de los datos
    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> actualizarSucursal(
            @PathVariable int id,
            @RequestParam String nombre,
            @RequestParam String direccion,
            @RequestParam String telefono,
            @RequestParam String region,
            @RequestParam String ciudad) {

        Sucursal actualizado = sucursalService.actualizarSucursal(id, nombre, direccion, telefono, region, ciudad);

        if (actualizado == null) {
            return ResponseEntity.notFound().build(); 
        }

        return ResponseEntity.ok(actualizado);
    }

}
