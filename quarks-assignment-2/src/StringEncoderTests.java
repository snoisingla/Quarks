import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class StringEncoderTests {
	
	@Test
	public void encodeString_Test1() {
		String expected = "a";
		assertEquals(expected, StringEncoder.encodeString("z"));	
	}
	
	@Test
	public void encodeString_Test2() {
		String expected = "98 ab";
		assertEquals(expected, StringEncoder.encodeString("98 zy"));	
	}
	
	@Test
	public void encodeString_Test3() {
		String expected = "z!z";
		assertEquals(expected, StringEncoder.encodeString("A!a"));	
	}
	
	@Test
	public void encodeString_Test4() {
		String expected = "12	!b";
		assertEquals(expected, StringEncoder.encodeString("12	!y"));	
	}

}
