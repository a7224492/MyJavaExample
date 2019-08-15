package com.mycode.research.jvm.my.code;

/**
 * @author jiangzhen
 */
public enum Code {
	ALOAD0(			(byte)42,	"", 	1, 	""		),
	INVOKEVIRTUAL(	(byte)182, 	"", 	3, 	""		),
	INVOKESPECIAL(	(byte)183, 	"", 	3, 	""		),
	INVOKESTATIC(	(byte)184, 	"", 	3, 	""		),
	GETSTATIC(		(byte)178, 	"", 	3, 	""		),
	IRETURN(		(byte)172, 	"", 	1, 	""		),
	RETURN(			(byte)177, 	"", 	1, 	""		),
	BIPUSH(			(byte)16, 	"", 	2, 	""		),
	ISTORE1(		(byte)60, 	"", 	1, 	""		),
	ISTORE2(		(byte)61, 	"", 	1, 	""		),
	ISTORE3(		(byte)62,	"", 	1, 	""		),
	IADD(			(byte)96, 	"", 	1, 	""		),
	ILOAD0(			(byte)26, 	"", 	1, 	""		),
	ILOAD1(			(byte)27, 	"", 	1, 	""		),
	ILOAD2(			(byte)28,	"", 	1, 	""		),
	ILOAD3(			(byte)29, 	"", 	1, 	""		);

	private byte id;
	private int len;
	private String name;
	private String format;

	Code(byte id, String name, int len, String format) {
		this.id = id;
		this.name = name;
		this.len = len;
		this.format = format;
	}

	public int id() {
		return id;
	}

	public int len() {
		return len;
	}

	public static Code parse(int id) {
		for (Code value : Code.values()) {
			if (value.id() == id) {
				return value;
			}
		}

		return null;
	}
}
