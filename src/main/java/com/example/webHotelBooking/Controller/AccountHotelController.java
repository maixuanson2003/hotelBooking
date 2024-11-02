package com.example.webHotelBooking.Controller;

import com.example.webHotelBooking.DTO.Response.AccountHotelDTO;
import com.example.webHotelBooking.Enums.UpdateAccount;
import com.example.webHotelBooking.Exception.ResourceNotFoundException;
import com.example.webHotelBooking.Service.AccountHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountHotelController {

    @Autowired
    private AccountHotelService accountHotelService;

    // Tạo tài khoản cho một khách sạn
    @PostMapping("/create/{hotelId}")
    public ResponseEntity<String> createAccountByHotel(@PathVariable Long hotelId) {
        try {
            accountHotelService.CreatAccountByHotel(hotelId);
            return ResponseEntity.ok("Tài khoản cho khách sạn đã được tạo thành công.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Khách sạn không tìm thấy.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi tạo tài khoản.");
        }
    }

    // Xóa tài khoản khách sạn theo Id
    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<String> deleteAccountById(@PathVariable Long accountId) {
        try {
            accountHotelService.DeleteAccountById(accountId);
            return ResponseEntity.ok("Tài khoản đã được xóa thành công.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi xóa tài khoản.");
        }
    }
    @GetMapping("/GetAll/account")
    public  ResponseEntity<List<AccountHotelDTO>>GetAllHotelAccount(){
        return ResponseEntity.ok( accountHotelService.GetAllAccountHotel());
    }

    // Thay đổi mật khẩu của tài khoản
    @PutMapping("/change-password/{accountId}")
    public ResponseEntity<String> changePassword(@PathVariable Long accountId,
                                                 @RequestParam String password,
                                                 @RequestParam String oldPassword) {
        try {
            String result = accountHotelService.ChangePassWord(password, oldPassword, accountId);
            if (result.equals(UpdateAccount.SUCCESS.getMessage())) {
                return ResponseEntity.ok("Mật khẩu đã được thay đổi thành công.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thay đổi mật khẩu thất bại.");
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tài khoản không tìm thấy.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi thay đổi mật khẩu.");
        }
    }
    @PostMapping("/creat/all")
    public  void  CreatAllAccount(){
        accountHotelService.CreateAllAccountHotel();
    }

}
