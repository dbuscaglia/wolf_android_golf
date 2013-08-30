package com.golf.wolf.util;

import java.util.Date;

import com.activeandroid.serializer.TypeSerializer;


/**
 * serializing of dates for activeandroid
 *
 */
final public class UtilDateSerializer extends TypeSerializer {
	@Override
	public Class<?> getDeserializedType() {
		return Date.class;
	}

	@Override
	public Class<?> getSerializedType() {
		return Long.class;
	}

	@Override
	public Long serialize(Object data) {
		if (data == null) {
			return null;
		}

		return ((Date) data).getTime();
	}

	@Override
	public Date deserialize(Object data) {
		if (data == null) {
			return null;
		}

		return new Date((Long) data);
	}
}