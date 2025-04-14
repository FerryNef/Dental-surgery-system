package com.ads.dentalapp.service.impl;
import com.ads.dentalapp.model.Bill;
import com.ads.dentalapp.repository.BillRepository;
import com.ads.dentalapp.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;

    @Override
    public Bill saveBill(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public Bill getBillById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
    }

    @Override
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    @Override
    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }

    @Override
    public Bill updateBill(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public boolean hasUnpaidBills(Long patientId) {
        return billRepository.findByPatientId(patientId)
                .stream()
                .anyMatch(bill -> bill.getStatus().equalsIgnoreCase("unpaid"));
    }
}
