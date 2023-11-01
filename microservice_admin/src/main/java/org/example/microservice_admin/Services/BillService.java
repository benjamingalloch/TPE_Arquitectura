package org.example.microservice_admin.Services;

import org.example.microservice_admin.DTOs.BillDTO;
import org.example.microservice_admin.DTOs.DateFromUntilDTO;
import org.example.microservice_admin.DTOs.NewBillDTO;
import org.example.microservice_admin.Entities.Bill;
import org.example.microservice_admin.Repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service("billService")
public class BillService{
    @Autowired
    private BillRepository billRepository;

    //------------------------------------------------------------ PUNTO 3 D ------------------------------------------------------------
    @Transactional
    public double getBillingByTime(DateFromUntilDTO dateFromUntilDTO) throws Exception {
        LocalDateTime startDate = dateFromUntilDTO.getStartDate();
        LocalDateTime finishDate = dateFromUntilDTO.getFinishDate();

        if (startDate.isAfter(finishDate)) {
            throw new Exception("La fecha inicial no puede ser posterior a la final");
        }

        Double billing = this.billRepository.getBillingByTime(startDate, finishDate);
        if (billing != null) {
            return billing;
        } else {
            return 0.00;
        }
    }

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



    //@Transactional(readOnly = true)
    //public Double getBilling(String dateFrom, String dateTo) {
    //    return this.billRepository.getBilling(dateFrom, dateTo);
    //}

}