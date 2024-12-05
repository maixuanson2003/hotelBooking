package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Response.SaleCodeDTO;
import com.example.webHotelBooking.Service.SaleCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salecodes")
public class SaleCodeController {

    @Autowired
    private SaleCodeService saleCodeService;

    // Tạo mới SaleCode
    @PostMapping
    public ResponseEntity<String> createSaleCode(@RequestBody SaleCodeDTO saleCodeDTO) {
        saleCodeService.CreateSaleCode(saleCodeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sale Code created successfully");
    }

    // Cập nhật SaleCode theo ID
    @PutMapping("/{id}")
    public ResponseEntity<String> updateSaleCode(@PathVariable("id") Long saleCodeId, @RequestBody SaleCodeDTO saleCodeDTO) {
        saleCodeService.UpdateSaleCode(saleCodeDTO, saleCodeId);
        return ResponseEntity.ok("Sale Code updated successfully");
    }

    // Xóa SaleCode theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSaleCode(@PathVariable("id") Long saleCodeId) {
        saleCodeService.DeleteSaleCode(saleCodeId);
        return ResponseEntity.ok("Sale Code deleted successfully");
    }

    // Lấy tất cả SaleCodes
    @GetMapping
    public ResponseEntity<List<SaleCodeDTO>> getAllSaleCodes() {
        List<SaleCodeDTO> saleCodeDTOList = saleCodeService.GetAllSaleCode();
        return ResponseEntity.ok(saleCodeDTOList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SaleCodeDTO> getSaleCodesById(@PathVariable("id") Long id) {
        SaleCodeDTO saleCodeDTO = saleCodeService.GetSaleCodeById(id);
        return ResponseEntity.ok(saleCodeDTO);
    }

    // Auto-delete SaleCodes đã hết hạn (sử dụng thủ công cho testing)
    @DeleteMapping("/autodelete")
    public ResponseEntity<String> autoDeleteSaleCodes() {
        saleCodeService.AutoDeleteSaleCode();
        return ResponseEntity.ok("Auto deletion of expired Sale Codes completed");
    }
}

