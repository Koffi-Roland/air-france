package com.afklm.repindpp.paymentpreference.service.internal;

import com.afklm.repindpp.paymentpreference.dao.FieldsRepository;
import com.afklm.repindpp.paymentpreference.dao.PaymentDetailsRepository;
import com.afklm.repindpp.paymentpreference.dto.PaymentDetailsTransform;
import com.afklm.repindpp.paymentpreference.dto.PaymentsDetailsDTO;
import com.afklm.repindpp.paymentpreference.entity.Fields;
import com.afklm.repindpp.paymentpreference.entity.PaymentDetails;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.util.UList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentPreferencesDS {

	@Autowired
	private PaymentDetailsRepository paymentDetailsRepository;

	@Autowired
	private FieldsRepository fieldsRepository;
	
	@Transactional
	public void create(PaymentsDetailsDTO paymentsDetailsDTO) throws JrafDomainException {
		PaymentDetails paymentDetails = PaymentDetailsTransform.dto2BoLight(paymentsDetailsDTO);
		PaymentDetailsTransform.dto2BoLink(paymentsDetailsDTO, paymentDetails);
		
		paymentDetailsRepository.saveAndFlush(paymentDetails);
		
		if (!UList.isNullOrEmpty(paymentDetails.getFields())) {
			for (Fields fields : paymentDetails.getFields()) {
				fieldsRepository.saveAndFlush(fields);
			}
		}
	}

	@Transactional
	public void remove(PaymentsDetailsDTO dto) throws JrafDomainException {
		paymentDetailsRepository.deleteById(dto.getPaymentId());
	}
	
	@Transactional
	public void update(PaymentsDetailsDTO paymentsDetailsDTO) throws JrafDomainException {
		paymentDetailsRepository.findById(paymentsDetailsDTO.getPaymentId()).ifPresent(
				e -> {
					PaymentDetailsTransform.dto2BoLight(paymentsDetailsDTO, e);
					paymentDetailsRepository.save(e);
				});
	}
	
	@Transactional
	public void updateV2(final PaymentsDetailsDTO dto) throws JrafDomainException {
		paymentDetailsRepository.findById(dto.getPaymentId()).ifPresent(e -> {
			fieldsRepository.deleteByPaymentdetailsPaymentId(dto.getPaymentId());
			PaymentDetailsTransform.dto2BoLight(dto, e);
			PaymentDetailsTransform.dto2BoLink(dto, e);
			paymentDetailsRepository.saveAndFlush(e);
		});
	}
	
	@Transactional
	public void updatePCIDSS(PaymentsDetailsDTO paymentsDetailsDTO) throws JrafDomainException {
		paymentDetailsRepository.findById(paymentsDetailsDTO.getPaymentId()).ifPresent(e -> {
			PaymentDetailsTransform.dto2BoLight(paymentsDetailsDTO, e);
			paymentDetailsRepository.saveAndFlush(e);
		});
	}
	
	@Transactional
	public boolean deletePaymentPreferences(String gin) throws JrafDomainException {
		return paymentDetailsRepository.deleteByGin(gin) != 0;
	}

	@Transactional
	public List<PaymentsDetailsDTO> getNonTokenizedPayments() throws JrafDomainException {
		List<PaymentsDetailsDTO> dtoList = new ArrayList<>();
		for(PaymentDetails p : paymentDetailsRepository.findByIsTokenizedOrIsTokenized(null, "N")) {
			PaymentsDetailsDTO dto = PaymentDetailsTransform.bo2DtoLight(p);
			PaymentDetailsTransform.bo2DtoLink(p, dto);
			dtoList.add(dto);
		} 
		
		return dtoList;
	}
	
	@Transactional
	public List<PaymentsDetailsDTO> findByGin(String gin) throws JrafDomainException {
		List<PaymentsDetailsDTO> dtoList = new ArrayList<PaymentsDetailsDTO>();
		for(PaymentDetails p : paymentDetailsRepository.findByGin(gin)) {
			PaymentsDetailsDTO dto = PaymentDetailsTransform.bo2DtoLight(p);
			PaymentDetailsTransform.bo2DtoLink(p, dto);
			dtoList.add(dto);
		} 
		return dtoList;
	}	
	
	@Transactional
	public List<PaymentsDetailsDTO> findByGinAndPaymentId(String gin, Integer paymentId) throws JrafDomainException {
		List<PaymentsDetailsDTO> dtoList = new ArrayList<PaymentsDetailsDTO>();
		for(PaymentDetails p : paymentDetailsRepository.findByGinAndPaymentId(gin, paymentId)) {
			PaymentsDetailsDTO dto = PaymentDetailsTransform.bo2DtoLight(p);
			PaymentDetailsTransform.bo2DtoLink(p, dto);
			dtoList.add(dto);
		} 
		return dtoList;
	}	

	@Transactional
	public boolean deletePaymentPreferencesByPaymentId(String paymentId) throws JrafDomainException {
		try {
			Integer paymentIdInt = Integer.parseInt(paymentId);
			
			Optional<PaymentDetails> payment = paymentDetailsRepository.findById(paymentIdInt);
			
			if (payment.isPresent()) {
				List<Fields> fields = fieldsRepository.findByPaymentdetailsPaymentId(paymentIdInt);
				
				if (!UList.isNullOrEmpty(fields)) {
					for (Fields field : fields) {
						fieldsRepository.deleteById(field.getFieldId());
					}
				}
				paymentDetailsRepository.deleteById(paymentIdInt);
				
				return true;
			}
			
			return false;

		} catch(NumberFormatException | NullPointerException e) {
			return false;
		}
	}
}
