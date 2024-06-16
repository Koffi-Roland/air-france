package com.afklm.batch.cleanupcreditcards.processor;

import com.afklm.batch.cleanupcreditcards.service.CleanupCreditCardsSummaryService;
import com.afklm.repindpp.paymentpreference.dao.FieldsRepository;
import com.afklm.repindpp.paymentpreference.encoding.AES;
import com.afklm.repindpp.paymentpreference.entity.Fields;
import com.afklm.repindpp.paymentpreference.entity.PaymentDetails;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Component
@Slf4j
public class CleanupCreditCardsProcessor implements ItemProcessor<PaymentDetails, PaymentDetails> {

    @Autowired
    @Lazy
    private FieldsRepository fieldsRepository;

    @Autowired
    CleanupCreditCardsSummaryService summaryService;

    @Override
    public PaymentDetails process(@NotNull PaymentDetails paymentDetails) throws Exception {
        summaryService.incrementProcessedCounter();

        // every 100 paymentDetails print log counter
        if(summaryService.getProcessed().get() % 100==0){
            log.info("=========> Processed {} PaymentDetails", summaryService.getProcessed().get());
        }

        if(isCCorDC(paymentDetails)){
            List<Fields> fields = fieldsRepository.findByPaymentdetailsPaymentId(paymentDetails.getPaymentId());
            for (Fields field : fields) {
                    try{
                        String paymentFieldCode = AES.decrypt(field.getPaymentFieldCode());
                        String paymentFieldPreference = AES.decrypt(field.getPaymentFieldPreference());
                        if (isFieldPciToken(paymentFieldCode)) {
                            String firstSixChar = paymentFieldPreference.substring(0,6);
                            if(!isNumeric(firstSixChar)){
                                log.info(summaryService.printCounter());
                                log.info("paymentID is CC or DC {}", paymentDetails.getPaymentId());
                                log.info("PCI_TOKEN={}", paymentFieldPreference);
                                log.info("substr PCI_TOKEN={}", firstSixChar);
                                return paymentDetails;
                            }
                        }
                    } catch(StringIndexOutOfBoundsException e){
                        log.error("Index out of bound exception: ",e.getMessage());
                    }catch(InvalidKeyException | IllegalBlockSizeException | BadPaddingException
                             | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
                        log.error("Problem with the encryption operation: ",e);
                    }catch (Exception e){
                        log.error("Caught exception : ",e);
                    }
            }
        }
        return null;
    }

    private boolean isCCorDC(PaymentDetails paymentDetails){
        String paymentGroupType = paymentDetails.getPaymentGroup().toUpperCase();
        return paymentGroupType.equals("CC") || paymentGroupType.equals("DC");
    }

    private boolean isNumeric(String field){
        return field.matches("\\d+$");
    }

    private boolean isFieldPciToken(String fieldCode){
        return "PCI_TOKEN".equalsIgnoreCase(fieldCode);
    }



}
