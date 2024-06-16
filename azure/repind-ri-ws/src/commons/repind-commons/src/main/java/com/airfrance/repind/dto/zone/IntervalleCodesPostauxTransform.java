package com.airfrance.repind.dto.zone;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.zone.IntervalleCodesPostaux;

public final class IntervalleCodesPostauxTransform {
	
	private IntervalleCodesPostauxTransform() {
    }
	
	public static IntervalleCodesPostauxDTO bo2DtoLight(IntervalleCodesPostaux intervalleCodesPostaux) throws JrafDomainException {
		IntervalleCodesPostauxDTO intervalleCodesPostauxDTO = new IntervalleCodesPostauxDTO();
		bo2DtoLightImpl(intervalleCodesPostaux, intervalleCodesPostauxDTO);
		return intervalleCodesPostauxDTO;
    }
	
	private static void bo2DtoLightImpl(IntervalleCodesPostaux intervalleCodesPostaux, IntervalleCodesPostauxDTO intervalleCodesPostauxDTO) {
		intervalleCodesPostauxDTO.setCle(intervalleCodesPostaux.getCle());
		intervalleCodesPostauxDTO.setCodePays(intervalleCodesPostaux.getCodePays());
		intervalleCodesPostauxDTO.setCodePostalDebut(intervalleCodesPostaux.getCodePostalDebut());
		intervalleCodesPostauxDTO.setCodePostalFin(intervalleCodesPostaux.getCodePostalFin());
		intervalleCodesPostauxDTO.setUsage(intervalleCodesPostaux.getUsage());
    }
	
	public static IntervalleCodesPostaux dto2BoLight(IntervalleCodesPostauxDTO intervalleCodesPostauxDTO) throws JrafDomainException {
		IntervalleCodesPostaux intervalleCodesPostaux = new IntervalleCodesPostaux();
		dto2BoLightImpl(intervalleCodesPostauxDTO, intervalleCodesPostaux);
		return intervalleCodesPostaux;
    }
	
	private static void dto2BoLightImpl(IntervalleCodesPostauxDTO intervalleCodesPostauxDTO, IntervalleCodesPostaux intervalleCodesPostaux) {
		intervalleCodesPostaux.setCle(intervalleCodesPostauxDTO.getCle());
		intervalleCodesPostaux.setCodePays(intervalleCodesPostauxDTO.getCodePays());
		intervalleCodesPostaux.setCodePostalDebut(intervalleCodesPostauxDTO.getCodePostalDebut());
		intervalleCodesPostaux.setCodePostalFin(intervalleCodesPostauxDTO.getCodePostalFin());
		intervalleCodesPostaux.setUsage(intervalleCodesPostauxDTO.getUsage());
    }

}
