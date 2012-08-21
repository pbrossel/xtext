/*******************************************************************************
 * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.xbase.scoping.batch;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.common.types.JvmFeature;
import org.eclipse.xtext.common.types.JvmField;
import org.eclipse.xtext.common.types.JvmOperation;
import org.eclipse.xtext.common.types.JvmType;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.xbase.XAbstractFeatureCall;

import com.google.common.collect.Lists;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
public abstract class AbstractStaticImportsScope extends AbstractSessionBasedScope {
	
	public AbstractStaticImportsScope(IScope parent, IFeatureScopeSession session, XAbstractFeatureCall context) {
		super(parent, session, context);
	}
	
	@Override
	protected Iterable<IEObjectDescription> getAllLocalElements() {
		List<TypeBucket> buckets = getBuckets();
		if (buckets.isEmpty()) {
			return Collections.emptyList();
		}
		List<IEObjectDescription> result = Lists.newArrayList();
		for(TypeBucket bucket: buckets) {
			for(JvmType type: bucket.getTypes()) {
				if (type instanceof JvmDeclaredType) {
					Iterable<JvmFeature> features = ((JvmDeclaredType) type).getAllFeatures();
					for(JvmFeature feature: features) {
						if (isStatic(feature))
							addDescriptions(feature, bucket, result);
					}
				}
			}
		}
		return result;
	}
	
	protected boolean isStatic(JvmFeature feature) {
		if (feature instanceof JvmField) {
			return ((JvmField) feature).isStatic();
		}
		if (feature instanceof JvmOperation) {
			return ((JvmOperation) feature).isStatic();
		}
		return false;
	}

	protected void addDescriptions(JvmFeature feature, TypeBucket bucket, List<IEObjectDescription> result) {
		result.add(new BucketedEObjectDescription(QualifiedName.create(feature.getSimpleName()), feature, bucket.getId(), getSession().isVisible(feature)));
	}

	protected abstract List<TypeBucket> getBuckets();
	
	@Override
	protected void processFeatureNames(QualifiedName name, NameAcceptor acceptor) {
		super.processFeatureNames(name, acceptor);
		String aliasedGetter = "get" + Strings.toFirstUpper(name.toString());
		acceptor.accept(aliasedGetter, 2);
		String aliasedBooleanGetter = "is" + Strings.toFirstUpper(name.toString());
		acceptor.accept(aliasedBooleanGetter, 2);
	}
	
	@Override
	protected Collection<IEObjectDescription> getLocalElementsByName(final QualifiedName name) {
		final List<TypeBucket> buckets = getBuckets();
		if (buckets.isEmpty()) {
			return Collections.emptyList();
		}
		final List<IEObjectDescription> result = Lists.newArrayList();
		processFeatureNames(name, new NameAcceptor() {
			public void accept(String simpleName, int order) {
				for(TypeBucket bucket: buckets) {
					for(JvmType type: bucket.getTypes()) {
						if (type instanceof JvmDeclaredType) {
							Iterable<JvmFeature> features = ((JvmDeclaredType) type).findAllFeaturesByName(simpleName);
							for(JvmFeature feature: features) {
								if (isStatic(feature))
									result.add(createNamedDescription(name, feature, bucket));
							}
							
						}
					}
				}
			}
		});
		
		// TODO cache the result in the session explicitly for the static types
		// especially interesting for cache misses
		return result;
	}

	protected BucketedEObjectDescription createNamedDescription(QualifiedName name, JvmFeature feature,
			TypeBucket bucket) {
		return new BucketedEObjectDescription(name, feature, bucket.getId(), getSession().isVisible(feature));
	}
}
