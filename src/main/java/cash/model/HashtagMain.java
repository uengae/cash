package cash.model;

import java.util.ArrayList;

public class HashtagMain {
	public static ArrayList<String> splitString(String memo) {
		ArrayList<String> stringList = new ArrayList<String>();
		String[] stringArray = memo.split(" ");
		for (String s1 : stringArray) {
			if (s1.startsWith("#")) {
				String[] hashStringArray = s1.split("#");
				for (String s2 : hashStringArray) {
					if (!"".equals(s2)) {
						stringList.add(s2);
					}
				}
			}
		}
		return stringList;
	}

	public static void main(String[] args) {
		String memo = "안녕하세요 #구디아카데미 입니다. 하반기 #자바 교육과정 시간표 공지하였습니다.";
		for(String s : splitString(memo)) {
			System.out.println(s);
		}
		
	}

}
