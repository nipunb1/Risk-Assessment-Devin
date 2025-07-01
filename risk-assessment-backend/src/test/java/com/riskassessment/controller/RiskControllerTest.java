package com.riskassessment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.riskassessment.dto.RiskDTO;
import com.riskassessment.entity.Risk;
import com.riskassessment.service.RiskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RiskController.class)
public class RiskControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private RiskService riskService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private RiskDTO testRiskDTO;
    
    @BeforeEach
    public void setUp() {
        testRiskDTO = new RiskDTO();
        testRiskDTO.setRiskId(1L);
        testRiskDTO.setRiskDate(LocalDate.now());
        testRiskDTO.setRiskType(Risk.RiskType.MARKET_PRACTICE);
        testRiskDTO.setRiskProbability(Risk.RiskProbability.HIGH);
        testRiskDTO.setRiskStatus(Risk.RiskStatus.OPEN);
        testRiskDTO.setRiskRemarks("Test remarks");
        testRiskDTO.setRiskImpact(Risk.RiskImpact.HIGH);
    }
    
    @Test
    public void testGetAllRisks() throws Exception {
        when(riskService.getAllRisks()).thenReturn(Arrays.asList(testRiskDTO));
        
        mockMvc.perform(get("/api/risks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].riskId").value(1L))
;
    }
    
    @Test
    public void testGetRiskById() throws Exception {
        when(riskService.getRiskById(1L)).thenReturn(Optional.of(testRiskDTO));
        
        mockMvc.perform(get("/api/risks/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.riskId").value(1L))
;
    }
    
    @Test
    public void testGetRiskByIdNotFound() throws Exception {
        when(riskService.getRiskById(1L)).thenReturn(Optional.empty());
        
        mockMvc.perform(get("/api/risks/1"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void testCreateRisk() throws Exception {
        when(riskService.createRisk(any(RiskDTO.class))).thenReturn(testRiskDTO);
        
        mockMvc.perform(post("/api/risks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testRiskDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.riskId").value(1L))
;
    }
    
    @Test
    public void testUpdateRisk() throws Exception {
        when(riskService.updateRisk(anyLong(), any(RiskDTO.class))).thenReturn(Optional.of(testRiskDTO));
        
        mockMvc.perform(put("/api/risks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testRiskDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.riskId").value(1L))
;
    }
    
    @Test
    public void testUpdateRiskNotFound() throws Exception {
        when(riskService.updateRisk(anyLong(), any(RiskDTO.class))).thenReturn(Optional.empty());
        
        mockMvc.perform(put("/api/risks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testRiskDTO)))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void testDeleteRisk() throws Exception {
        when(riskService.deleteRisk(1L)).thenReturn(true);
        
        mockMvc.perform(delete("/api/risks/1"))
                .andExpect(status().isNoContent());
    }
    
    @Test
    public void testDeleteRiskNotFound() throws Exception {
        when(riskService.deleteRisk(1L)).thenReturn(false);
        
        mockMvc.perform(delete("/api/risks/1"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void testGetRisksByStatus() throws Exception {
        when(riskService.getRisksByStatus(Risk.RiskStatus.OPEN)).thenReturn(Arrays.asList(testRiskDTO));
        
        mockMvc.perform(get("/api/risks/status/OPEN"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].riskId").value(1L));
    }
    
    @Test
    public void testGetEnumValues() throws Exception {
        mockMvc.perform(get("/api/risks/enums"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.riskTypes").isArray())
                .andExpect(jsonPath("$.riskProbabilities").isArray())
                .andExpect(jsonPath("$.riskStatuses").isArray());
    }
}
