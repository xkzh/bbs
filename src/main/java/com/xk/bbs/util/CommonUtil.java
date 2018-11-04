package com.xk.bbs.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

	public static String getNow(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	public static String SHA1(String str){
		return DigestUtils.shaHex(str);//对接受的字符串进行sha1加密并返回
	}

}
