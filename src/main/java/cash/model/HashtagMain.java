package cash.model;

public class HashtagMain {

	public static void main(String[] args) {
		String content = "안녕하세요 #구디아카데미 입니다. 하반기 #자바 교육과정 시간표 공지하였습니다.";
		//#이 있는곳까지의 숫자
		int index = content.indexOf("#");
		System.out.println("인덱스 확인 : "+index);
		
		//index부터 시작해서 띄어쓰기 있는곳까지의 숫자
		int firstLdx = content.indexOf(" ", index);

		String result = content.substring(index+1, firstLdx);
		System.out.println("글자 확인 : "+result);
		

		//index부터 시작해서 띄어쓰기 있는곳까지의 숫자
		int secondIdx = content.indexOf("#", firstLdx);
		System.out.println("인덱스 확인 : "+secondIdx);

		int secondLdx = content.indexOf(" ", secondIdx);
		System.out.println("글자 확인 : "+secondLdx);
		
		String result2 = content.substring(secondIdx+1, secondLdx);
		System.out.println("글자 확인 : "+result2);
		

		int qIdx = content.indexOf("#", secondLdx);
		System.out.println("인덱스 확인 : "+qIdx);

		
	}

}
