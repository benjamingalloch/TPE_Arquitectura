package org.example.microservice_admin.Services;

import org.example.microservice_admin.DTOs.BillDTO;
import org.example.microservice_admin.DTOs.NewBillDTO;
import org.example.microservice_admin.Entities.Bill;
import org.example.microservice_admin.Repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("billService")
public class BillService{
    @Autowired
    private BillRepository billRepository;

    @Transactional(readOnly = true)
    public List<BillDTO> findAll() {
        return this.billRepository.findAll().stream().map(BillDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public BillDTO findById(Long id) {
        return this.billRepository.findById(id).map(BillDTO::new).orElseThrow(
                () -> new IllegalArgumentException("ID de integrante invalido: " + id));
    }

    @Transactional
    public BillDTO save(NewBillDTO entity) {
        return new BillDTO(this.billRepository.save(new Bill(entity)));
    }

    @Transactional
    public void delete(Long id) {
        billRepository.delete(billRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de integrante invalido: " + id)));
    }

    @Transactional
    public void update(Long id, BillDTO entity) {
        Bill bill = billRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de integrante invalido: " + id));
        bill.setBillDate(entity.getBillDate());
        bill.setAmount(entity.getAmount());
        bill.setDescription(entity.getDescription());
        billRepository.save(bill);
    }

    @Transactional(readOnly = true)
    public Double getBilling(String dateFrom, String dateTo) {
        return this.billRepository.getBilling(dateFrom, dateTo);
    }

}