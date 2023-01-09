/*******************************************************************************
 * Copyright (c) 2010, 2023 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.builder.tests.ui;

import com.google.inject.Injector;
import org.eclipse.xtext.builder.tests.internal.TestsActivator;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class BuilderTestLanguageExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return FrameworkUtil.getBundle(TestsActivator.class);
	}
	
	@Override
	protected Injector getInjector() {
		TestsActivator activator = TestsActivator.getInstance();
		return activator != null ? activator.getInjector(TestsActivator.ORG_ECLIPSE_XTEXT_BUILDER_TESTS_BUILDERTESTLANGUAGE) : null;
	}

}
