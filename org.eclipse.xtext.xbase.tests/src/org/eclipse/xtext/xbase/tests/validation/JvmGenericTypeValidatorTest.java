/*******************************************************************************
 * Copyright (c) 2024 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.xbase.tests.validation;

import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.testlanguages.jvmGenericTypeValidatorTestLang.JvmGenericTypeValidatorTestLangModel;
import org.eclipse.xtext.xbase.testlanguages.tests.JvmGenericTypeValidatorTestLangInjectorProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.eclipse.xtext.xbase.validation.IssueCodes.*;
import static org.eclipse.xtext.xbase.testlanguages.jvmGenericTypeValidatorTestLang.JvmGenericTypeValidatorTestLangPackage.Literals.*;

import com.google.inject.Inject;

/**
 * @author Lorenzo Bettini - Initial contribution and API
 */
@RunWith(XtextRunner.class)
@InjectWith(JvmGenericTypeValidatorTestLangInjectorProvider.class)
public class JvmGenericTypeValidatorTest {
	@Inject
	private ParseHelper<JvmGenericTypeValidatorTestLangModel> parseHelper;
	@Inject
	private ValidationTestHelper validationHelper;

	@Test public void testValidModel() throws Exception {
		var model = parse(
			"package mypackage\n"
			+ "import java.util.ArrayList\n"
			+ "import java.io.Serializable\n"
			+ "class MyClass extends ArrayList<String> implements Serializable {\n"
			+ "}\n"
			+ "interface MyInterface extends Serializable {\n"
			+ "}");
		validationHelper.assertNoErrors(model);
	}

	@Test public void testMissingConstructor() throws Exception {
		var source = "package mypackage\n"
			+ "import test.NoDefaultConstructor\n"
			+ "class MyClass extends NoDefaultConstructor {\n"
			+ "}";
		var model = parse(
			source);
		validationHelper.assertError(model, MY_CLASS, MISSING_CONSTRUCTOR,
				source.indexOf("MyClass"), "MyClass".length(),
				"must define an explicit constructor");
	}

	private JvmGenericTypeValidatorTestLangModel parse(CharSequence programText) throws Exception {
		return parseHelper.parse(programText);
	}
}
