package com.ads.dentalapp.service;
import com.ads.dentalapp.model.Bill;
import java.util.List;

public interface BillService {
    Bill saveBill(Bill bill);
    Bill getBillById(Long id);
    List<Bill> getAllBills();
    void deleteBill(Long id);
    Bill updateBill(Bill bill);


    boolean hasUnpaidBills(Long patientId);
}
