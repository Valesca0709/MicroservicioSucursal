package sucursal.Service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import sucursal.sucurMicr.model.ProductoSucursal;
import sucursal.sucurMicr.repository.ProductoSucursalRepository;
import sucursal.sucurMicr.service.ProductoSucursalService;

public class ProductoSucursalTest {
    @Mock
    private ProductoSucursalRepository productoSucursalRepository;

    @InjectMocks
    private ProductoSucursalService productoSucursalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

     @Test
    void testListarProductoSucursal(){
     ProductoSucursal prodSuc1 = new ProductoSucursal(1, 2, 123, 5, 1200.0);
     ProductoSucursal prodSuc2 = new ProductoSucursal(2, 3, 1234,  6, 1300.0); 

    when(productoSucursalRepository.findAll()).thenReturn(Arrays.asList(prodSuc1, prodSuc2));
    List<ProductoSucursal> resultado = productoSucursalService.findAll();
    assertThat(resultado).hasSize(2).contains(prodSuc1, prodSuc2);
    verify(productoSucursalRepository).findAll();

 } 

@Test
void testFindByIdProductoAndIdSucursal() {
    int idProducto = 1;
    int idSucursal = 2;
    int stock = 50;

    ProductoSucursal ps = new ProductoSucursal();
    ps.setIdProducto(idProducto);
    ps.setIdSucursal(idSucursal);
    ps.setCantidad(stock); 

    when(productoSucursalRepository.findByIdProductoAndIdSucursal(idProducto, idSucursal))
        .thenReturn(List.of(ps));

    List<ProductoSucursal> resultado = productoSucursalService.findByIdProductoAndIdSucursal(idProducto, idSucursal);

    assertFalse(resultado.isEmpty());
    assertEquals(1, resultado.size());
    assertEquals(idProducto, resultado.get(0).getIdProducto());
    assertEquals(idSucursal, resultado.get(0).getIdSucursal());
    assertEquals(stock, resultado.get(0).getCantidad()); 
  }
}
