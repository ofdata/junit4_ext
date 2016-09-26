package com.liushijie.runner;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import com.liushijie.annotations.FilterMethods;

public class SpecialMethodsRunner extends BlockJUnit4ClassRunner {
	private FilterMethods filter;
	public SpecialMethodsRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
		filter = clazz.getAnnotation(FilterMethods.class);
	}

	/**
	 * EnterPoint
	 */
	@Override
	protected Statement childrenInvoker(final RunNotifier notifier) {
		if (filter == null) {	// keep original runner alone. 
			return super.childrenInvoker(notifier);
		}
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				runMethodsWithFilter(notifier, filter);
			}
		};
	}
	/**
	 * Filter the methods could be run.
	 * @param notifier
	 * @param filterMethods
	 */
	private void runMethodsWithFilter(final RunNotifier notifier, final FilterMethods filterMethods) {
		String[] filters = filterMethods.methods();
		if (filters == null || filters.length == 0) {
			throw new IllegalArgumentException(
					"Wrong parameters!Please check Annotation FilterMthods parameters...");
		}
		
		Set<Pattern> patternSet = new HashSet<Pattern>();
		for (String filter : filters) {
			patternSet.add(Pattern.compile(filter));
		}
		
		for (FrameworkMethod method : getChildren()) {
			for (Pattern pattern : patternSet) {	// loop all patterns
				if (pattern.matcher(method.getName()).matches()) {	// if matches ...break;
					runChild(method, notifier);
					break;
				}
			}
		}
	}
	
}
