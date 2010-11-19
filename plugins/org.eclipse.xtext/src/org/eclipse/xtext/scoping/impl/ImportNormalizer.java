/*******************************************************************************
 * Copyright (c) 2010 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.scoping.impl;

import org.eclipse.xtext.naming.QualifiedName;

/**
 * @author koehnlein - Initial contribution and API
 */
public class ImportNormalizer {

	private final QualifiedName importedNamespacePrefix;

	private boolean hasWildCard;

	public ImportNormalizer(QualifiedName importedNamespace, boolean wildCard) {
		if (importedNamespace == null || importedNamespace.getSegmentCount() < 1) {
			throw new IllegalArgumentException("Imported namespace must not be null / empty");
		}
		hasWildCard = wildCard;
		this.importedNamespacePrefix = importedNamespace;
	}

	public QualifiedName deresolve(QualifiedName fullyQualifiedName) {
		if (hasWildCard) {
			if (fullyQualifiedName.startsWith(importedNamespacePrefix)
					&& fullyQualifiedName.getSegmentCount() != importedNamespacePrefix.getSegmentCount())
				return fullyQualifiedName.skipFirst(importedNamespacePrefix.getSegmentCount());
		} else {
			if (fullyQualifiedName.equals(importedNamespacePrefix))
				return QualifiedName.create(fullyQualifiedName.getLastSegment());
		}
		return null;
	}

	public QualifiedName resolve(QualifiedName relativeName) {
		if (hasWildCard) {
			return importedNamespacePrefix.append(relativeName);
		}
		if (relativeName.getFirstSegment().equals(importedNamespacePrefix.getLastSegment())) {
			if (importedNamespacePrefix.getSegmentCount() == 1)
				return relativeName;
			else
				return importedNamespacePrefix.skipLast(1).append(relativeName);
		}
		return null;
	}

	@Override
	public String toString() {
		return importedNamespacePrefix.toString() + (hasWildCard ? ".*" : "");
	}

}
