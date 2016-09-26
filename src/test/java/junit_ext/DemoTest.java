package junit_ext;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.liushijie.annotations.FilterMethods;
import com.liushijie.runner.SpecialMethodsRunner;

@RunWith(SpecialMethodsRunner.class)
@FilterMethods(methods = { "^.*2$", "^m.*", "ab21"})
public class DemoTest {
	@Test
	public void m1() {
		System.out.println(123);
	}

	@Test
	public void mmm2() {
		System.out.println(456);
	}

	@Test
	public void a2() {
		System.out.println(791);
	}
	
	@Test
	public void ab21() {
		System.out.println(7890);
	}
}
