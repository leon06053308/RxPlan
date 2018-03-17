package com.example.caoyouqiang.rxplan.constant;

/**
 * Created by caoyouqiang on 18-3-17.
 */

public class Constants {
	private Constants(){}

	public static final String OP_TAG = "op_tag";
	public static final String OP_NAME = "op_name";


	public enum OpEnum{
		OP_CREATER(0), OP_CHANGE(1), OP_FILTER(2), OP_GROUP(3), OP_ERROE(4), OP_ASSIST(5),
		OP_CONDITION(6), OP_MATH(7), OP_CONNECT(8), OP_TRANSFER(9);
		private int value;
		OpEnum(int val){
			this.value = val;
		}

		public int getVal(){
			return value;
		}
	}
}
