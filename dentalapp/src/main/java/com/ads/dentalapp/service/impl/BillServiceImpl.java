package com.ads.dentalapp.service.impl;
import com.ads.dentalapp.dto.request.BillRequestDTO;
import com.ads.dentalapp.model.Bill;
import com.ads.dentalapp.model.Patient;
import com.ads.dentalapp.model.User;
import com.ads.dentalapp.repository.BillRepository;
import com.ads.dentalapp.repository.PatientRepository;
import com.ads.dentalapp.repository.UserRepository;
import com.ads.dentalapp.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;


    @Override
    public Bill updateBill(Long billId, BillRequestDTO billRequestDTO) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        BigDecimal newAmount = billRequestDTO.amount();
        bill.setAmount(newAmount);

        if (newAmount.compareTo(BigDecimal.ZERO) > 0) {
            bill.setStatus("Paid");
        } else {
            bill.setStatus("Unpaid");
        }

        return billRepository.save(bill);
    }

    @Override
    public List<Bill> getBillsForLoggedPatient() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Patient patient = patientRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return billRepository.findByPatientId(patient.getId());
    }

    @Override
    public void addBillToPatient(Long patientId, BillRequestDTO billRequestDTO) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Bill bill = new Bill();
        bill.setAmount(billRequestDTO.amount());
        bill.setStatus(billRequestDTO.status());
        bill.setPatient(patient);

        billRepository.save(bill);
    }


    @Override
    public boolean hasUnpaidBills(Long patientId) {
        return billRepository.existsByPatientIdAndStatus(patientId, "Unpaid");
    }
}
