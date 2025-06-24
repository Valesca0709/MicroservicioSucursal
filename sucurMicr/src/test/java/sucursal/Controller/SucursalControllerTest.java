package sucursal.Controller;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import sucursal.sucurMicr.controller.SucursalController;
import sucursal.sucurMicr.model.Sucursal;
import sucursal.sucurMicr.service.SucursalService;

/*@WebMvcTest(SucursalController.class)
public class SucursalControllerTest {

     @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private SucursalService sucursalService;

    
   @Test
    void testObtenerTodasSucursales() throws Exception {
        Sucursal s1 = new Sucursal(
            1, 
            "Sucursal1", 
            "Las Condes 123", 
            "12345", 
            "Las Condes", 
            "Metropolitana");
        Sucursal s2 = new Sucursal(
             2, 
             "Sucursal2", 
             "Freire 123", 
             "123456", 
             "Concepción", 
             "Bío-Bío"
            );
        
        Sucursal s3 = new Sucursal(
            3, 
            "Sucursal3", 
            "Valdivia 123", 
            "1234567", "Valdivia", 
            "Los lagos");

        Mockito.when(sucursalService.findAll()).thenReturn(Arrays.asList(s1, s2, s3));
        System.out.println(sucursalService);
        mockMvc.perform(get("/api/v1/sucursal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Sucursal1"))
                .andExpect(jsonPath("$[1].direccion").value("Freire 123"));
    }

}*/
