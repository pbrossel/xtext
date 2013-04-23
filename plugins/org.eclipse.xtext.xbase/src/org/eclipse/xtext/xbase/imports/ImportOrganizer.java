/*******************************************************************************
 * Copyright (c) 2011 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.xbase.imports;

import static com.google.common.collect.Lists.*;
import static org.eclipse.xtext.util.Strings.*;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.xbase.conversion.StaticQualifierValueConverter;
import org.eclipse.xtext.xbase.conversion.XbaseQualifiedNameValueConverter;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author Sven Efftinge - Initial contribution and API
 */
public class ImportOrganizer {

	@Inject
	private RewritableImportSection.Factory importSectionFactory;
	
	@Inject
	private Provider<TypeUsageCollector> typeUsageCollectorProvider;
	
	@Inject 
	private ConflictResolver conflictResolver;
	
	@Inject
	private NonOverridableTypesProvider nonOverridableTypesProvider;
	
	@Inject(optional=true)
	private IUnresolvedTypeResolver unresolvedTypeResolver;
	
	@Inject
	private StaticQualifierValueConverter staticQualifierConverter;
	
	@Inject
	private XbaseQualifiedNameValueConverter nameValueConverter;
	
	public List<ReplaceRegion> getOrganizedImportChanges(XtextResource resource) {
		TypeUsageCollector typeUsageCollector = typeUsageCollectorProvider.get();
		TypeUsages typeUsages = typeUsageCollector.collectTypeUsages(resource);
		if(unresolvedTypeResolver != null) 
			unresolvedTypeResolver.resolve(typeUsages, resource);
		Map<String, JvmDeclaredType> name2type = conflictResolver.resolveConflicts(typeUsages, nonOverridableTypesProvider, resource);
		return getOrganizedImportChanges(resource, name2type, typeUsages); 
	}

	private List<ReplaceRegion> getOrganizedImportChanges(XtextResource resource, Map<String, JvmDeclaredType> resolvedConflicts, TypeUsages typeUsages) {
		RewritableImportSection newImportSection = importSectionFactory.createNewEmpty(resource);
		addImports(resolvedConflicts, typeUsages, newImportSection);
		List<ReplaceRegion> replaceRegions = getReplacedUsageSites(resolvedConflicts, typeUsages, newImportSection);
		for(JvmDeclaredType staticImport: typeUsages.getStaticImports()) 
			newImportSection.addStaticImport(staticImport);
		for(JvmDeclaredType extensionImport: typeUsages.getExtensionImports()) 
			newImportSection.addStaticExtensionImport(extensionImport);
		replaceRegions.addAll(newImportSection.rewrite());
		return replaceRegions;
	}

	private List<ReplaceRegion> getReplacedUsageSites(Map<String, JvmDeclaredType> resolvedConflicts, TypeUsages typeUsages,
			RewritableImportSection newImportSection) {
		List<ReplaceRegion> result = newArrayList();
		for(Map.Entry<String, JvmDeclaredType> textToType: resolvedConflicts.entrySet()) {
			getReplacedUsagesOf(textToType, typeUsages, newImportSection, result);
		}
		return result;
	}

	private void getReplacedUsagesOf(Map.Entry<String, JvmDeclaredType> nameToType, TypeUsages typeUsages, RewritableImportSection importSection,
			List<ReplaceRegion> result) {
		String nameToUse = nameToType.getKey();
		JvmDeclaredType type = nameToType.getValue();
		String packageLocalName = getPackageLocalName(type);
		for(TypeUsage typeUsage: typeUsages.getUsages(type)) {
			ReplaceRegion replaceRegion = getReplaceRegion(nameToUse, packageLocalName, type, typeUsage, importSection);
			if (replaceRegion != null) {
				result.add(replaceRegion);
			}
		}
	}

	@Nullable
	private ReplaceRegion getReplaceRegion(String nameToUse, String packageLocalName, JvmDeclaredType type,
			TypeUsage usage, RewritableImportSection importSection) {
		// if the resource contains two types with the same simple name, we don't add any import
		// but we can still use the package local name within the same package.
		if(equal(usage.getContextPackageName(), type.getPackageName())) {
			if(importSection.getImportedType(packageLocalName) == null) {
				nameToUse = packageLocalName;
			}
		}
		String textToUse = getConcreteSyntax(nameToUse, usage);
		if(!equal(usage.getText(), textToUse)) {
			return new ReplaceRegion(usage.getTextRegion(), textToUse);
		}
		return null;
	}

	private String getConcreteSyntax(String name, TypeUsage usage) {
		if (usage.isStaticAccess()) {
			if (usage.isTrailingDelimiterSuppressed()) {
				return staticQualifierConverter.toStringWithoutNamespaceDelimiter(name);
			}
			return staticQualifierConverter.toString(name);
		}
		return nameValueConverter.toString(name);
	}
	
	private void addImports(Map<String, JvmDeclaredType> resolvedConflicts, TypeUsages typeUsages, RewritableImportSection target) {
		for(Map.Entry<String, JvmDeclaredType> entry: resolvedConflicts.entrySet()) {
			String text = entry.getKey();
			JvmDeclaredType type = entry.getValue();
			Iterable<TypeUsage> usages = typeUsages.getUsages(type);
			if(needsImport(type, text, nonOverridableTypesProvider, usages)) {
				target.addImport(type);
			}
		}
	}

	protected String getPackageLocalName(JvmDeclaredType type) {
		String packageName = type.getPackageName();
		if(isEmpty(packageName)) 
			return type.getIdentifier();
		else 
			return type.getIdentifier().substring(packageName.length() + 1);
	}
	
	protected boolean needsImport(JvmDeclaredType type, String name, 
			NonOverridableTypesProvider nonOverridableTypesProvider, Iterable<TypeUsage> usages)  {
		return !((type.getIdentifier().equals(name))
			|| isUsedInLocalContextOnly(type, usages, nonOverridableTypesProvider, name));
	}
	
	protected boolean isUsedInLocalContextOnly(JvmDeclaredType type, Iterable<TypeUsage> usages, 
			NonOverridableTypesProvider nonOverridableTypesProvider, String name) {
		for(TypeUsage usage: usages) {
			if(nonOverridableTypesProvider.getVisibleType(usage.getContext(), name) == null
					&& !equal(usage.getContextPackageName(), type.getPackageName())
					) 
				return false;
		}
		return true;
	}
}
