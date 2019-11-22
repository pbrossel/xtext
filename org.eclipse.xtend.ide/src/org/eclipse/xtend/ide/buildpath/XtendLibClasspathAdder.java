/*******************************************************************************
 * Copyright (c) 2011 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtend.ide.buildpath;

import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;

/**
 * @author Jan Koehnlein - Initial contribution and API
 */
public class XtendLibClasspathAdder extends AbstractLibClasspathAdder {

	@Override
	protected IClasspathEntry createContainerClasspathEntry() {
		return JavaCore.newContainerEntry(XtendContainerInitializer.XTEND_LIBRARY_PATH);
	}

	@Override
	protected String[] getBundleIds() {
		return XtendClasspathContainer.BUNDLE_IDS_TO_INCLUDE;
	}
}
