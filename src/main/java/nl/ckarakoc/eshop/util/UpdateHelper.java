package nl.ckarakoc.eshop.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

@Component
public class UpdateHelper {

	private UpdateHelper() {
	}

	/**
	 * Partial update from {@code dto} to {@code target} ignoring null values.
	 *
	 * @param target
	 * @param dto
	 * @param <T>
	 * @param <S>
	 * @return the updated target
	 */
	public static <T, S> T updateFromDto(T target, S dto) {
		if (target == null || dto == null) return target;

		String[] nullPropertyNames = getNullPropertyNames(dto);

		BeanUtils.copyProperties(dto, target, nullPropertyNames);
		return target;
	}

	private static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> nulls = new HashSet<>();
		for (PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null) {
				nulls.add(pd.getName());
			}
		}
		return nulls.toArray(new String[0]);
	}
}

