package com.takeit.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/** 
 * <pre>
 * 객체 생성없이 사용하기 위한 공통기능 유틸클래스
 * </pre>
 */
public class Utility {
	/** argument로 전달받은 주소의 위도, 경도를 반환 */
	public static HashMap<String, String> getLatlng(String address) {
		String clientId = "5ta9sn0kog";
		String clientSecret = "2uWwVDqXH2WqejM8bumlMLrjJXu9pPvLkRLgdQiT";
		
		StringBuffer response = new StringBuffer();
		try {
            String addr = URLEncoder.encode(address, "UTF-8");  
            String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { 
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		JSONArray jsonArray= null;
		JSONObject jsonObject2 = null;
		String x = null;
		String y = null;
		try {
			jsonObject=(JSONObject) parser.parse(response.toString());
			jsonArray = (JSONArray)jsonObject.get("addresses");
			for(Object object: jsonArray) {
				jsonObject2= (JSONObject)object;
				if(jsonObject2.get("x")!=null) {
					x=jsonObject2.get("x").toString();
				}
				if(jsonObject2.get("y")!=null) {
					y=jsonObject2.get("y").toString();
				}
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		HashMap<String, String> latLng = new HashMap<String, String>();
		latLng.put("lat", y);
		latLng.put("lng", x);
		return latLng;
	}
	
	/** 랜덤값 반환 메서드 */
	public static String getSecureString(int length, boolean isUpper) {
		Random extractNo = new Random((long)(Math.random() * System.nanoTime()));
		String secureCode = "";
		for (int index = 0; index < length; index++) {
			if (extractNo.nextBoolean()) {
				secureCode += extractNo.nextInt(10); // 0 ~ 9 숫자
			} else {
				if (isUpper) {	// 영문 대문자
					secureCode += (char)(extractNo.nextInt(26) + 65);
				} else {		// 영문 소문자
					secureCode += (char)(extractNo.nextInt(26) + 97);
				}
			}
		}
		
		return secureCode;
	}
	
	/**
	 * 현재날짜 반환 
	 * @return 현재 기본형식(년도4-월2-일2) 날짜 
	 */
	public static String getCurrentDate() {
		return getCurrentDate("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
	}
	
	public static String getCurrentDate(String pattern) {
		return getCurrentDate(pattern, Locale.KOREA);
	}
	
	public static String getCurrentDate(String pattern, Locale locale) {
		return new SimpleDateFormat(pattern, locale).format(new Date());
	}
	
	/** 문자열 형식의 날짜를 Date객체로 반환 */
	public static Date convertStringToDate(String date, String pattern) throws java.text.ParseException {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return new Date(format.parse(date).getTime());
	}
	
	/** 두 Date객체의 날짜 간격 일 수를 반환 */
	public static int getDayBetweenAandB(Date firstDate, Date secondDate) {
		long date = firstDate.getTime() - secondDate.getTime(); 
		long dateDays = date / (24*60*60*1000);
		return (int)dateDays;
	}
	
	/**
	 * <pre>
	 * 보안문자 숫자형식 반환
	 * </pre>
	 * @return 보안문자 숫자6자리 반환
	 */
	public static String getSecureNumber() {
		return getSecureNumber(6);
	}
	
	public static String getSecureNumber(int length) {
		StringBuilder secureNumber = new StringBuilder();
		Random random = new Random((long)(System.nanoTime() * Math.random()));
		for (int index = 0; index < length; index++) {
			secureNumber.append(random.nextInt(10));
		}
		return secureNumber.toString();
	}
	
	/**
	 * <pre>
	 * 보안문자 숫자+영문조합 반환
	 * </pre>
	 * @return 기본 숫자+영문조합 6자리 
	 */
	public static String getSecureNumberAndString() {
		return getSecureNumberAndString(6, true);
	}
	
	public static String getSecureNumberAndString(int length) {
		return getSecureNumberAndString(length, true);
	}

	/**
	 * <pre>
	 * 보안문자 숫자+영문조합 반환
	 * </pre>
	 * @param length 길이
	 * @param isUpper 보안영문 대소문자 
	 * @return 지정한 길의 영문대소문자+숫자결합 
	 */
	public static String getSecureNumberAndString(int length, boolean isUpper) {
		StringBuilder secureNumber = new StringBuilder();
		Random random = new Random((long)(System.nanoTime() * Math.random()));
		for (int index = 0; index < length; index++) {
			if (random.nextBoolean()) {
				secureNumber.append(random.nextInt(10));
			} else {
				if (isUpper) {
					secureNumber.append((char)(random.nextInt(26) + 65));
				} else {
					secureNumber.append((char)(random.nextInt(26) + 97));
				}
			}
		}
		return secureNumber.toString();
	}	
	
	public static void main(String[] args) {
		System.out.println("\n현재날짜");
		System.out.println(Utility.getCurrentDate());
		System.out.println(Utility.getCurrentDate("yyyy.MM.dd"));
		System.out.println(Utility.getCurrentDate("MM.dd"));
		System.out.println(Utility.getCurrentDate("HH:mm:ss"));
		System.out.println(Utility.getCurrentDate("[a]hh:mm:ss"));
		System.out.println(Utility.getCurrentDate("[a]hh:mm:ss", Locale.US));
		System.out.println(Utility.getCurrentDate("[a]hh:mm:ss", Locale.CHINA));
		
		System.out.println("\n보안문자 : 숫자형식");
		System.out.println(Utility.getSecureNumber());
		System.out.println(Utility.getSecureNumber(6));
		System.out.println(Utility.getSecureNumber(8));
		System.out.println(Utility.getSecureNumber(12));
		
		System.out.println("\n보안문자 : 숫자 + 대문자");
		System.out.println(Utility.getSecureNumberAndString());
		System.out.println(Utility.getSecureNumberAndString(4));
		System.out.println(Utility.getSecureNumberAndString(10));
		
		System.out.println("\n보안문자 : 숫자 + 대.소문자");
		System.out.println(Utility.getSecureNumberAndString(10, true));
		System.out.println(Utility.getSecureNumberAndString(12, false));
	}
}
