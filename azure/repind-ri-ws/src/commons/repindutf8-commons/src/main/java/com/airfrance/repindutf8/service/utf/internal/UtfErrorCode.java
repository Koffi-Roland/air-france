package com.airfrance.repindutf8.service.utf.internal;

public enum UtfErrorCode {

	UTF_LIST_TOO_BIG(1),
	UTFDATA_LIST_TOO_BIG(2),
	UTFDATA_KEY_ALREADY_EXIST(3),
	UTF_TYPE_DOESNT_EXIST(4),
	UTFDATA_KEY_DOESNT_EXIST_FOR_TYPE(5),
	UTFDATA_LIST_WAY_TOO_BIG(6), // the list of utfdata inside DB
									// already exceed the limit: aka bug
									// in the code
	UTF_LIST_WAY_TOO_BIG(7), // the list of utf inside DB already exceed the limit. aka: bug in the code
	AT_LEAST_1_UTFDATA_REQUIRED_TO_CREATE_UTF(8),
	UTFDATA_KEY_CANNOT_BE_NULL(9),
	CANNOT_DELETE_UTDATA_ON_NON_EXISTING_UTF(10),
	UTFDATA_LIST_CANNOT_BE_NULL(11),
	UTFDATA_VALUE_CANNOT_BE_NULL(12),
	UTF_TOO_MANY_UTF_WITH_THIS_TYPE(13),
	THIS_UTF_ID_DOESNT_EXIST(14),
	THIS_UTF_DATA_ID_DOESNT_EXIST(15),
	RACE_CONDITION_STOP_SPAMMING(16);

	private int _id;

	UtfErrorCode(final int id) {
		_id = id;
	}

	public int getId() {
		return _id;
	}
}
