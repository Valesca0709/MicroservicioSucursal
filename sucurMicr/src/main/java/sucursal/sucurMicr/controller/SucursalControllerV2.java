package sucursal.sucurMicr.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import sucursal.sucurMicr.assemblers.SucursalModelAssembler;
import sucursal.sucurMicr.model.Sucursal;
import sucursal.sucurMicr.service.SucursalService;

@RestController
@RequestMapping("/api/v2/sucursal")
public class SucursalControllerV2 {
 @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalModelAssembler assembler;


    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Sucursal>> getAllSucursal() {
        List<EntityModel<Sucursal>> sucursales = sucursalService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(sucursales,
                linkTo(methodOn(SucursalControllerV2.class).getAllSucursal()).withSelfRel());

    }

    @GetMapping(value = "/{idSucursal}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Sucursal> getSucursalById(@PathVariable int idSucursal) {
     Sucursal sucursal = sucursalService.findById(idSucursal).get();
     return assembler.toModel(sucursal);
    }


    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> createCarrera(@RequestBody Sucursal sucursal) {
        Sucursal newSucursal = sucursalService.save(sucursal);
        return ResponseEntity
                .created(linkTo(methodOn(SucursalControllerV2.class).getSucursalById(newSucursal.getIdSucursal())).toUri())
                .body(assembler.toModel(newSucursal));
    }

     @PutMapping(value = "/{idSucursal}")
    public ResponseEntity<EntityModel<Sucursal>> updateSucursal(
            @PathVariable int idSucursal,
            @RequestBody Sucursal sucursal) {

        sucursal.setIdSucursal(idSucursal);
        Sucursal updatedSucursal = sucursalService.save(sucursal);

        return ResponseEntity.ok(assembler.toModel(updatedSucursal));
    }  
}
