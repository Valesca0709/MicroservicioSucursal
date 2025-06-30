package sucursal.sucurMicr.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import sucursal.sucurMicr.controller.ProductoSucursalController;
import sucursal.sucurMicr.model.ProductoSucursal;
import sucursal.sucurMicr.service.ProductoSucursalService;

@WebMvcTest(ProductoSucursalController.class)
public class ProductoSucursalControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private ProductoSucursalService productoSucursalService;


    @Test
    void testObtenerTodasSucursales_Productos() throws Exception {
    ProductoSucursal ps1 = new ProductoSucursal(
        1, 
        2,
        3, 
        300, 
        2000.0
        );
    ProductoSucursal ps2 = new ProductoSucursal(
        2,
         3,
         5, 
         400, 
         1000.0
         );
    Mockito.when(productoSucursalService.findAll()).thenReturn(Arrays.asList(ps1, ps2));
    System.out.println(productoSucursalService);
    mockMvc.perform(get("/api/v1/productoSucursal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idProductoSucursal").value(1))
                .andExpect(jsonPath("$[0].idSucursal").value(2))
                .andExpect(jsonPath("$[0].idProducto").value(3))
                .andExpect(jsonPath("$[0].cantidad").value(300))
                .andExpect(jsonPath("$[0].precio_unitario").value(2000.0))

                .andExpect(jsonPath("$[1].idProductoSucursal").value(2))
                .andExpect(jsonPath("$[1].idSucursal").value(3))
                .andExpect(jsonPath("$[1].idProducto").value(5))
                .andExpect(jsonPath("$[1].cantidad").value(400))
                .andExpect(jsonPath("$[1].precio_unitario").value(1000.0));
  }

  @Test
    void testGetProductoSucursal_NoContent() throws Exception {
        when(productoSucursalService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/productoSucursal"))
                .andExpect(status().isNoContent());
    }

@Test
    void testBuscarProductoById_OK() throws Exception {
        ProductoSucursal ps = new ProductoSucursal();
        ps.setIdProductoSucursal(1);
        ps.setIdProducto(1);
        ps.setIdSucursal(1);

        when(productoSucursalService.findByIdProductoAndIdSucursal(1, 1))
            .thenReturn(Arrays.asList(ps));

        mockMvc.perform(get("/api/v1/productoSucursal/buscar")
                .param("idProducto", "1")
                .param("idSucursal", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idProductoSucursal").value(1))
                .andExpect(jsonPath("$[0].idProducto").value(1))
                .andExpect(jsonPath("$[0].idSucursal").value(1));
    }

 @Test
    void testBuscarProductoById_NoContent() throws Exception {
        when(productoSucursalService.findByIdProductoAndIdSucursal(1, 1))
            .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/productoSucursal/buscar")
                .param("idProducto", "1")
                .param("idSucursal", "1"))
                .andExpect(status().isNoContent());
    }
}
