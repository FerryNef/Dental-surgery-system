package com.ads.dentalapp.service;
import com.ads.dentalapp.dto.request.BillRequestDTO;
import com.ads.dentalapp.model.Bill;

import java.math.BigDecimal;
import java.util.List;

public interface BillService {

    boolean hasUnpaidBills(Long patientId);

    Bill updateBill(Long billId, BillRequestDTO billRequestDTO);
    List<Bill> getBillsForLoggedPatient();

    void addBillToPatient(Long patientId, BillRequestDTO billRequestDTO);

}
