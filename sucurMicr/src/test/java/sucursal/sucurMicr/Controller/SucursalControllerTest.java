package sucursal.sucurMicr.Controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import sucursal.sucurMicr.controller.SucursalController;
import sucursal.sucurMicr.model.Sucursal;
import sucursal.sucurMicr.service.SucursalService;

@WebMvcTest(SucursalController.class)
public class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private SucursalService sucursalService;

    
    @Autowired
    private ObjectMapper objectMapper;
    
   @Test
    void testObtenerTodasSucursales() throws Exception {
        Sucursal s1 = new Sucursal(
            1, 
            "Sucursal1", 
            "Las Condes 123", 
            "12345", 
            "Las Condes", 
            false,
            "Metropolitana");
        Sucursal s2 = new Sucursal(
             2, 
             "Sucursal2", 
             "Freire 123", 
             "123456", 
             "Concepción", 
             true,
             "Bío-Bío"
            );
        
        Sucursal s3 = new Sucursal(
            3, 
            "Sucursal3", 
            "Valdivia 123", 
            "1234567", 
            "Valdivia", 
            true,
            "Los lagos");

        Mockito.when(sucursalService.findAll()).thenReturn(Arrays.asList(s1, s2, s3));
        System.out.println(sucursalService);
        mockMvc.perform(get("/api/v1/sucursal"))
                .andExpect(status().isOk())
        
                .andExpect(jsonPath("$[0].idSucursal").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Sucursal1"))
                .andExpect(jsonPath("$[0].direccion").value("Las Condes 123"))
                .andExpect(jsonPath("$[0].telefono").value("12345"))
                .andExpect(jsonPath("$[0].ciudad").value("Las Condes"))
                .andExpect(jsonPath("$[0].region").value("Metropolitana"))
                .andExpect(jsonPath("$[0].activa").value(false))
              
                .andExpect(jsonPath("$[1].idSucursal").value(2))
                .andExpect(jsonPath("$[1].nombre").value("Sucursal2"))
                .andExpect(jsonPath("$[1].direccion").value("Freire 123"))
                .andExpect(jsonPath("$[1].telefono").value("123456"))
                .andExpect(jsonPath("$[1].ciudad").value("Concepción"))
                .andExpect(jsonPath("$[1].region").value("Bío-Bío"))
                .andExpect(jsonPath("$[1].activa").value(true))
              
                .andExpect(jsonPath("$[2].idSucursal").value(3))
                .andExpect(jsonPath("$[2].nombre").value("Sucursal3"))
                .andExpect(jsonPath("$[2].direccion").value("Valdivia 123"))
                .andExpect(jsonPath("$[2].telefono").value("1234567"))
                .andExpect(jsonPath("$[2].ciudad").value("Valdivia"))
                .andExpect(jsonPath("$[2].region").value("Los lagos"))
                .andExpect(jsonPath("$[2].activa").value(true));
    }


    @Test
    void testGetSucursal_NoContent() throws Exception {
        when(sucursalService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/sucursal"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCrearSucursal() throws Exception {
        Sucursal nueva = new Sucursal (
            1,
             "Sucursal 1", 
             "Caupolicán 123", 
             "123456", 
             "Concepción", 
             true,
             "Bio-bio");
        Sucursal guardada = new Sucursal(
            1, 
            "Sucursal 1", 
            "Caupolicán 123", 
            "123456", 
            "Concepción", 
            true,
            "Bio-bio");

        Mockito.when(sucursalService.save(any(Sucursal.class))).thenReturn(guardada);
        
        mockMvc.perform(post("/api/v1/sucursal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idSucursal").value(1))
                .andExpect(jsonPath("$.nombre").value("Sucursal 1"))
                .andExpect(jsonPath("$.direccion").value("Caupolicán 123"))
                .andExpect(jsonPath("$.telefono").value("123456"))
                .andExpect(jsonPath("$.ciudad").value("Concepción"))
                .andExpect(jsonPath("$.region").value("Bio-bio"))
                .andExpect(jsonPath("$.activa").value(true));
    } 

     @Test
    void testCreateSucursal_InternalServerError() throws Exception {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre("Sucursal Test");
        sucursal.setDireccion("Dirección Test");

        when(sucursalService.save(any(Sucursal.class))).thenThrow(new RuntimeException("Error simulado"));

        mockMvc.perform(post("/api/v1/sucursal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sucursal)))
                .andExpect(status().isInternalServerError());
    }


    @Test
    void testGetSucursalById_Found() throws Exception {
    Sucursal sucursal = new Sucursal(
            1, 
            "Sucursal 1", 
            "Caupolicán 123", 
            "1234567", 
            "Concepción", 
            true,
            "Bio-Bio");

    Mockito.when(sucursalService.findById(1)).thenReturn(Optional.of(sucursal));

    mockMvc.perform(get("/api/v1/sucursal/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idSucursal").value(1))
            .andExpect(jsonPath("$.nombre").value("Sucursal 1"))
            .andExpect(jsonPath("$.direccion").value("Caupolicán 123"))
            .andExpect(jsonPath("$.ciudad").value("Concepción"))
            .andExpect(jsonPath("$.region").value("Bio-Bio"))
            .andExpect(jsonPath("$.activa").value(true));
    }


    @Test
    void testObtenerPorIdNoExistente() throws Exception {
        Mockito.when(sucursalService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/sucursal/99"))
                .andExpect(status().isNotFound());
    }

   @Test
    void testActualizarSucursal_Found() throws Exception {
        Sucursal actualizado = new Sucursal(1, 
            "Sucursal Actualizada", 
            "Nueva Dirección", 
            "9876543", 
            "Santiago", 
            true,
            "Metropolitana");

        Mockito.when(sucursalService.actualizarSucursal(
                Mockito.eq(1),
                Mockito.eq("Sucursal Actualizada"),
                Mockito.eq("Nueva Dirección"),
                Mockito.eq("9876543"),
                Mockito.eq("Metropolitana"),
                Mockito.eq("Santiago")
        )).thenReturn(actualizado);

        mockMvc.perform(put("/api/v1/sucursal/1")
                .param("nombre", "Sucursal Actualizada")
                .param("direccion", "Nueva Dirección")
                .param("telefono", "9876543")
                .param("region", "Metropolitana")
                .param("ciudad", "Santiago"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idSucursal").value(1))
                .andExpect(jsonPath("$.nombre").value("Sucursal Actualizada"))
                .andExpect(jsonPath("$.direccion").value("Nueva Dirección"))
                .andExpect(jsonPath("$.telefono").value("9876543"))
                .andExpect(jsonPath("$.region").value("Metropolitana"))
                .andExpect(jsonPath("$.ciudad").value("Santiago"));
       }

    @Test
        void testActualizarSucursal_NotFound() throws Exception {
            Mockito.when(sucursalService.actualizarSucursal(
                    Mockito.eq(99),
                    Mockito.anyString(),
                    Mockito.anyString(),
                    Mockito.anyString(),
                    Mockito.anyString(),
                    Mockito.anyString()
            )).thenReturn(null);

            mockMvc.perform(put("/api/v1/sucursal/99")
                    .param("nombre", "Sucursal X")
                    .param("direccion", "Dir X")
                    .param("telefono", "0000000")
                    .param("region", "Región X")
                    .param("ciudad", "Ciudad X"))
                    .andExpect(status().isNotFound()); // No existe esa sucursal
        }


    @Test
    void testEliminarSucursal_Found() throws Exception {
        Mockito.doNothing().when(sucursalService).deleteById(1);
        mockMvc.perform(delete("/api/v1/sucursal/1"))
                .andExpect(status().isNoContent());
        }
    }
