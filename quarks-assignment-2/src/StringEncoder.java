import java.util.Scanner;

public class StringEncoder {
	public static String encodeString(String str) {
		String s = str.toLowerCase();
		int referenceValue = 'a';
		String finalString = "";
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			int charValue = c;
			if(c > 96 && c < 123){
				/* 
				 * int reversedValue = charValue + (25 - 2*(charValue - referenceValue));
				 * 98 -> 98 + (25-2)
				 * 99 -> 99 + (25-4)
				 */

				int reversedValue = 25 - charValue + 2 * referenceValue;
				finalString += (char)reversedValue;
			}
			else { //special charcters
				finalString += c;
			}
		}
		return finalString;
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		String result = encodeString(str);
		System.out.println(result);
		sc.close();
	}
}
