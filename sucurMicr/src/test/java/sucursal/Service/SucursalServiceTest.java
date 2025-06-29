package sucursal.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import sucursal.sucurMicr.model.Sucursal;
import sucursal.sucurMicr.repository.SucursalRepository;
import sucursal.sucurMicr.service.SucursalService;


public class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private SucursalService sucursalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

  @Test
    void testGuardarSucursal() {
        Sucursal sucursal = new Sucursal( 1, 
            "Sucursal1", 
            "Las Condes 123", 
            "12345", 
            "Las Condes", 
            true,
            "Metropolitana");
        Sucursal sucursalGuardada = new Sucursal(
             1, 
            "Sucursal1", 
            "Las Condes 123", 
            "12345", 
            "Las Condes", 
            true,
            "Metropolitana");
            

        when(sucursalRepository.save(sucursal)).thenReturn(sucursalGuardada);

        Sucursal resultado = sucursalService.save(sucursalGuardada);

       assertThat(resultado.getIdSucursal()).isEqualTo(1);
       assertThat(resultado.getNombre()).isEqualTo("Sucursal1");
       assertThat(resultado.getCiudad()).isEqualTo("Las Condes");

        verify(sucursalRepository).save(sucursal);
    }

    @Test  
    void testListarSucursal(){
    Sucursal s1 = new Sucursal ( 
            1, 
            "Sucursal1", 
            "Las Condes 123", 
            "12345", 
            "Las Condes", 
            true,
            "Metropolitana");
    Sucursal s2 = new Sucursal( 
            2, 
             "Sucursal2", 
             "Freire 123", 
             "123456", 
            "Concepción", 
            true,
             "Bío-Bío");

    when(sucursalRepository.findAll()).thenReturn(Arrays.asList(s1,s2));
    List<Sucursal> resultado = sucursalService.findAll();
    assertThat(resultado).hasSize(2).contains(s1,s2);
    verify(sucursalRepository).findAll();
} 
   
@Test
void testListarSucursal_Vacia() {
    //Simula que las sucursales están vacías en la base de datos
    when(sucursalRepository.findAll()).thenReturn(Collections.emptyList());

    List<Sucursal> resultado = sucursalService.findAll();

    assertTrue(resultado.isEmpty());

    verify(sucursalRepository, times(1)).findAll();
}


@Test
    void buscarSucursalPorId(){
        int sucursalId = 1234; 
        Sucursal suc1 = new Sucursal (
            1, 
            "Sucursal1", 
            "Barros123", 
            "12345", 
            "Concepcion", 
            true,
            "Concepcion");

        when(sucursalRepository.findById(sucursalId)).thenReturn(Optional.of(suc1)); // lo busca por el id
        Optional<Sucursal> resultado = sucursalService.findById(sucursalId);

        assertTrue(resultado.isPresent()); // puede estar presente o no
        assertEquals("Sucursal1", resultado.get().getNombre()); // lo esperado  y el resultado
        assertEquals("Barros123", resultado.get().getDireccion());
        verify(sucursalRepository, times(1)).findById(sucursalId);
}

@Test
void buscarSucursalPorId_NoExiste() {
    int sucursalId = 999;

    when(sucursalRepository.findById(sucursalId)).thenReturn(Optional.empty());

    Optional<Sucursal> resultado = sucursalService.findById(sucursalId);

    assertFalse(resultado.isPresent());
    verify(sucursalRepository, times(1)).findById(sucursalId);
}


@Test
void testDeleteById() {
    int id = 999;

    doThrow(new EmptyResultDataAccessException(1)).when(sucursalRepository).deleteById(id);
    // lanza una excepción para saber si el ID no existe, si existe esa sucursal elimina
    assertThrows(EmptyResultDataAccessException.class, () -> {
        sucursalService.deleteById(id);
    });

    verify(sucursalRepository, times(1)).deleteById(id);
}


@Test
void actualizarSucursal() {
    int sucursalId = 1;

    Sucursal sucur1 = new Sucursal(
        1, 
        "Sucursal1",
        "Barros123",
        "12345", 
        "Concepcion",
        true,
        "Concepcion"
        );

    Sucursal sucur1Actualizada = new Sucursal(
    1, 
    "Sucursal2", 
    "ColoColo123", 
    "123456", 
    "Valpo", 
    true,
    "Valpo"
    );

    when(sucursalRepository.findById(sucursalId)).thenReturn(Optional.of(sucur1));

    when(sucursalRepository.save(any(Sucursal.class))).thenReturn(sucur1Actualizada);

    Sucursal resultado = sucursalService.actualizarSucursal(
        sucursalId,
        "Sucursal2",
        "ColoColo123",
        "123456",
        "Valpo",
        "Valpo"
    );

    assertNotNull(resultado);
    assertEquals("Sucursal2", resultado.getNombre());
    assertEquals("ColoColo123", resultado.getDireccion());
    assertEquals("123456", resultado.getTelefono());
    assertEquals("Valpo", resultado.getRegion());

    verify(sucursalRepository).findById(sucursalId);
    verify(sucursalRepository).save(any(Sucursal.class));
  }

@Test
void actualizarSucursal_NoExiste_devuelveNull() {
    int idInvalido = 999;
    when(sucursalRepository.findById(idInvalido)).thenReturn(Optional.empty());

    Sucursal resultado = sucursalService.actualizarSucursal(
        idInvalido,
        "Nombre",
        "Dirección",
        "Teléfono",
        "Ciudad",
        "Región"
    );

    assertNull(resultado); // Se espera que sea null
    verify(sucursalRepository, times(1)).findById(idInvalido);
    verify(sucursalRepository, never()).save(any());
   }
}
