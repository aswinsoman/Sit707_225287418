package web.service;
import org.junit.Assert;
import org.junit.Test;
import web.service.MathQuestionService;

public class TestMathQuestionService {
	
	// ==================== Q1 ADDITION TESTS ====================
	
	@Test
	public void testQ1TrueAdd() {
		Assert.assertEquals(MathQuestionService.q1Addition("1", "2"), 3, 0);
	}
	
	@Test
	public void testQ1AddDecimal() {
		Assert.assertEquals(MathQuestionService.q1Addition("1.5", "2.5"), 4.0, 0);
	}
	
	@Test
	public void testQ1AddNegative() {
		Assert.assertEquals(MathQuestionService.q1Addition("-1", "2"), 1, 0);
	}
	
	@Test
	public void testQ1AddZero() {
		Assert.assertEquals(MathQuestionService.q1Addition("0", "5"), 5, 0);
	}
	
	@Test(expected = NumberFormatException.class)
	public void testQ1AddNumber1Empty() {
		MathQuestionService.q1Addition("", "2");
	}
	
	@Test(expected = NumberFormatException.class)
	public void testQ1AddNumber2Empty() {
		MathQuestionService.q1Addition("1", "");
	}
	
	@Test(expected = NumberFormatException.class)
	public void testQ1AddNonNumeric() {
		MathQuestionService.q1Addition("abc", "2");
	}
	
	@Test(expected = NullPointerException.class)
	public void testQ1AddNull() {
		MathQuestionService.q1Addition(null, "2");
	}
	
	// ==================== Q2 SUBTRACTION TESTS ====================
	
	@Test
	public void testQ2TrueSub() {
		Assert.assertEquals(MathQuestionService.q2Subtraction("5", "2"), 3, 0);
	}
	
	@Test
	public void testQ2SubDecimal() {
		Assert.assertEquals(MathQuestionService.q2Subtraction("5.5", "2.5"), 3.0, 0);
	}
	
	@Test
	public void testQ2SubNegativeResult() {
		Assert.assertEquals(MathQuestionService.q2Subtraction("2", "5"), -3, 0);
	}
	
	@Test
	public void testQ2SubZero() {
		Assert.assertEquals(MathQuestionService.q2Subtraction("5", "0"), 5, 0);
	}
	
	@Test(expected = NumberFormatException.class)
	public void testQ2SubNumber1Empty() {
		MathQuestionService.q2Subtraction("", "2");
	}
	
	@Test(expected = NumberFormatException.class)
	public void testQ2SubNumber2Empty() {
		MathQuestionService.q2Subtraction("5", "");
	}
	
	@Test(expected = NumberFormatException.class)
	public void testQ2SubNonNumeric() {
		MathQuestionService.q2Subtraction("abc", "2");
	}
	
	@Test(expected = NullPointerException.class)
	public void testQ2SubNull() {
		MathQuestionService.q2Subtraction(null, "2");
	}
	
	// ==================== Q3 MULTIPLICATION TESTS ====================
	
	@Test
	public void testQ3TrueMul() {
		Assert.assertEquals(MathQuestionService.q3Multiplication("2", "3"), 6, 0);
	}
	
	@Test
	public void testQ3MulDecimal() {
		Assert.assertEquals(MathQuestionService.q3Multiplication("2.5", "4"), 10.0, 0);
	}
	
	@Test
	public void testQ3MulNegative() {
		Assert.assertEquals(MathQuestionService.q3Multiplication("-2", "3"), -6, 0);
	}
	
	@Test
	public void testQ3MulZero() {
		Assert.assertEquals(MathQuestionService.q3Multiplication("5", "0"), 0, 0);
	}
	
	@Test
	public void testQ3MulByOne() {
		Assert.assertEquals(MathQuestionService.q3Multiplication("5", "1"), 5, 0);
	}
	
	@Test(expected = NumberFormatException.class)
	public void testQ3MulNumber1Empty() {
		MathQuestionService.q3Multiplication("", "2");
	}
	
	@Test(expected = NumberFormatException.class)
	public void testQ3MulNumber2Empty() {
		MathQuestionService.q3Multiplication("5", "");
	}
	
	@Test(expected = NumberFormatException.class)
	public void testQ3MulNonNumeric() {
		MathQuestionService.q3Multiplication("abc", "2");
	}
	
	@Test(expected = NullPointerException.class)
	public void testQ3MulNull() {
		MathQuestionService.q3Multiplication(null, "2");
	}
}