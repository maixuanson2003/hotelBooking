package com.example.webHotelBooking.Service;

import com.example.webHotelBooking.DTO.Response.SaleCodeDTO;

import java.util.List;

public interface SaleCodeService {
    public void CreateSaleCode(SaleCodeDTO saleCodeDTO);
    public void UpdateSaleCode(SaleCodeDTO saleCodeDTO,Long SaleCodeId);
    public void DeleteSaleCode(Long SaleCodeId);
    public void AutoDeleteSaleCode();
    public List<SaleCodeDTO> GetAllSaleCode();
    public SaleCodeDTO GetSaleCodeById(Long id);
}
