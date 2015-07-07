package net.bolbat.kit.ioc;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import net.bolbat.kit.ioc.scope.CompositeScope;
import net.bolbat.kit.ioc.scope.CustomScope;
import net.bolbat.kit.ioc.scope.DistributionScope;
import net.bolbat.kit.ioc.scope.Scope;
import net.bolbat.kit.ioc.scope.ScopeUtil;
import net.bolbat.kit.ioc.scope.TypeScope;

/**
 * {@link ScopeUtil} test cases.
 * 
 * @author Alexandr Bolbat
 */
public class ScopeUtilTest {

	private static final CompositeScope INNER2 = CompositeScope.get(CustomScope.get("TEST1"), CustomScope.get("TEST2"), DistributionScope.LOCAL);
	private static final CompositeScope INNER1 = CompositeScope.get(INNER2, null, CustomScope.get("TEST2"), TypeScope.PERSISTENCE_SERVICE);
	private static final CompositeScope PARENT = CompositeScope.get(INNER1, INNER2, null, CustomScope.get("TEST"), TypeScope.SERVICE, DistributionScope.LOCAL);

	private static final CustomScope NULL_SCOPE = null;
	private static final CompositeScope EMPTY_COMPOSITE_SCOPE = CompositeScope.get();

	/**
	 * Scopes to array test case.
	 */
	@Test
	public void scopesToArrayTest() {
		Scope[] scopes = ScopeUtil.scopesToArray(false, PARENT);
		Assert.assertEquals(6, scopes.length);

		final Set<String> sNames = new HashSet<>();
		for (final Scope scope : scopes)
			sNames.add(scope.getId());

		Assert.assertEquals(6, sNames.size());
		Assert.assertTrue(sNames.contains("TEST"));
		Assert.assertTrue(sNames.contains("TEST1"));
		Assert.assertTrue(sNames.contains("TEST2"));
		Assert.assertTrue(sNames.contains("LOCAL"));
		Assert.assertTrue(sNames.contains("SERVICE"));
		Assert.assertTrue(sNames.contains("PERSISTENCE_SERVICE"));

		// case with default scope
		scopes = ScopeUtil.scopesToArray(true);
		Assert.assertEquals(1, scopes.length);
		Assert.assertEquals(Manager.DEFAULT_SCOPE, scopes[0]);

		scopes = ScopeUtil.scopesToArray(true, NULL_SCOPE);
		Assert.assertEquals(1, scopes.length);
		Assert.assertEquals(Manager.DEFAULT_SCOPE, scopes[0]);

		scopes = ScopeUtil.scopesToArray(true, EMPTY_COMPOSITE_SCOPE);
		Assert.assertEquals(1, scopes.length);
		Assert.assertEquals(Manager.DEFAULT_SCOPE, scopes[0]);

		// case with empty scopes
		scopes = ScopeUtil.scopesToArray(false);
		Assert.assertEquals(0, scopes.length);

		scopes = ScopeUtil.scopesToArray(false, NULL_SCOPE);
		Assert.assertEquals(0, scopes.length);

		scopes = ScopeUtil.scopesToArray(false, EMPTY_COMPOSITE_SCOPE);
		Assert.assertEquals(0, scopes.length);
	}

	/**
	 * Scopes to string test case.
	 */
	@Test
	public void scopesToStringTest() {
		String scopesKey = ScopeUtil.scopesToString(PARENT);
		Assert.assertEquals("[LOCAL,PERSISTENCE_SERVICE,SERVICE,TEST,TEST1,TEST2]", scopesKey);

		// case with null or empty scopes
		scopesKey = ScopeUtil.scopesToString();
		Assert.assertEquals("", scopesKey);

		scopesKey = ScopeUtil.scopesToString(NULL_SCOPE);
		Assert.assertEquals("", scopesKey);

		scopesKey = ScopeUtil.scopesToString(EMPTY_COMPOSITE_SCOPE);
		Assert.assertEquals("", scopesKey);
	}

}
