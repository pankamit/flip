package com.flip.transformer;



import org.springframework.beans.BeanUtils;

import com.flip.entity.GeneralMenFootWearInfo;
import com.flip.sharedObject.GeneralMenFootWearInfoSharedObject;

public class GeneralMenFootWearSharedObjectEntityTransformer {

	public static GeneralMenFootWearInfo apply(GeneralMenFootWearInfoSharedObject generalMenFootWearInfoSharedObject) {
		GeneralMenFootWearInfo generalMenFootWearInfo = new GeneralMenFootWearInfo();
		
		BeanUtils.copyProperties(generalMenFootWearInfoSharedObject,generalMenFootWearInfo);
		return generalMenFootWearInfo;
	}
	
}
