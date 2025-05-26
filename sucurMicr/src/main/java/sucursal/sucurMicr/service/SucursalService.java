package sucursal.sucurMicr.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sucursal.sucurMicr.model.Sucursal;
import sucursal.sucurMicr.repository.SucursalRepository;

@Service
public class SucursalService {
    @Autowired
    private SucursalRepository sucursalRepository;
    
    // Listar todos los productos
    public List<Sucursal> findAll() {
        return sucursalRepository.findAll();
    }
    
    // Buscar producto por ID
    public Optional<Sucursal> findById(int id) {
        return sucursalRepository.findById(id);
    }
    
    // Crear o actualizar un producto
    public Sucursal save(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }
    
    // Eliminar un producto
    public void deleteById(int id) {
        sucursalRepository.deleteById(id);
    }
    
    // Actualizar solo el stock
    public Sucursal actualizarSucursal(int id, String nombre, String direccion, String telefono, String region) {
    Optional<Sucursal> sucursalOpt = sucursalRepository.findById(id);
    
    if (sucursalOpt.isEmpty()) {
        return null; // No se encontró el usuario
    }

    Sucursal sucursal = sucursalOpt.get();
    sucursal.setNombre(nombre);
    sucursal.setDireccion(direccion);
    sucursal.setTelefono(telefono);
    sucursal.setRegion(region);

      return sucursalRepository.save(sucursal);
        }

    }
