/*******************************************************************************
 * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.xtext.ui.wizard.releng;

import org.eclipse.xtext.ui.wizard.DefaultProjectInfo;

/**
 * @author Dennis Huebner - Initial contribution and API
 */
public class RelengProjectInfo extends DefaultProjectInfo {

	private String featureProjectName;

	public void setFeatureProjectName(String featureProjectName) {
		this.featureProjectName = featureProjectName;
	}

	public String getFeatureProjectName() {
		return featureProjectName;
	}
}
