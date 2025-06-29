package sucursal.sucurMicr.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import sucursal.sucurMicr.controller.SucursalControllerV2;
import sucursal.sucurMicr.model.Sucursal;

@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>> {

    @Override
    public EntityModel<Sucursal> toModel(Sucursal sucursal) {
       return EntityModel.of(sucursal,
                linkTo(methodOn(SucursalControllerV2.class).getSucursalById(sucursal.getIdSucursal())).withSelfRel(),
                linkTo(methodOn(SucursalControllerV2.class).getAllSucursal()).withRel("sucursal"));  
    }
}
