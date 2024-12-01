/*******************************************************************************
 * Copyright (c) 2014, 2024 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.m2e;

import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.HIDE_LOCAL_SYNTHETIC_VARIABLES;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.INSTALL_DSL_AS_PRIMARY_SOURCE;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.OUTPUT_CLEANUP_DERIVED;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.OUTPUT_CLEAN_DIRECTORY;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.OUTPUT_CREATE_DIRECTORY;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.OUTPUT_DERIVED;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.OUTPUT_DESCRIPTION;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.OUTPUT_DIRECTORY;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.OUTPUT_KEEP_LOCAL_HISTORY;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.OUTPUT_NAME;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.OUTPUT_OVERRIDE;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.USE_OUTPUT_PER_SOURCE_FOLDER;
import static org.eclipse.xtext.builder.preferences.BuilderPreferenceAccess.PREF_AUTO_BUILDING;
import static org.eclipse.xtext.builder.preferences.BuilderPreferenceAccess.getIgnoreSourceFolderKey;
import static org.eclipse.xtext.builder.preferences.BuilderPreferenceAccess.getKey;
import static org.eclipse.xtext.builder.preferences.BuilderPreferenceAccess.getOutputForSourceFolderKey;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.project.MavenProject;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.m2e.core.project.configurator.AbstractProjectConfigurator;
import org.eclipse.m2e.core.project.configurator.ProjectConfigurationRequest;
import org.eclipse.xtext.builder.preferences.BuilderConfigurationBlock;
import org.eclipse.xtext.generator.OutputConfiguration.SourceMapping;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.ui.preferences.OptionsConfigurationBlock;
import org.osgi.service.prefs.BackingStoreException;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;

/**
 * The project configurator is applied if maven the build for a project has the xtext maven plugin configured.
 * 
 * It copies the configuration from the pom.xml into the Eclipse settings.
 */
public class XtextProjectConfigurator extends AbstractProjectConfigurator {

	@Override
	public void configure(ProjectConfigurationRequest request, IProgressMonitor monitor) throws CoreException {
		addNature(getProject(request), XtextProjectHelper.NATURE_ID, monitor);
		configureLanguages(request, monitor);
	}

	private void configureLanguages(ProjectConfigurationRequest request, IProgressMonitor monitor) throws CoreException {
		List<MojoExecution> executions = getMojoExecutions(request, monitor);
		SubMonitor progress = SubMonitor.convert(monitor, executions.size());
		for (MojoExecution execution : executions) {
			Languages languages = maven.getMojoParameterValue(getMavenProject(request), execution, "languages", Languages.class, progress.split(1));
			if (languages != null) {
				ProjectScope projectPreferences = new ProjectScope(getProject(request));
				for (Language language : languages) {
					configureLanguage(projectPreferences, language, request);
				}
			}
		}
	}

	private void configureLanguage(ProjectScope projectPreferences, Language language, ProjectConfigurationRequest request) throws CoreException {
		if (language.getOutputConfigurations().isEmpty()) return;
		
		IEclipsePreferences languagePreferences = projectPreferences.getNode(language.name());
		languagePreferences.putBoolean(OptionsConfigurationBlock.isProjectSpecificPropertyKey(BuilderConfigurationBlock.PROPERTY_PREFIX), true);
		languagePreferences.putBoolean(PREF_AUTO_BUILDING, true);
		for (OutputConfiguration outputConfiguration : language.getOutputConfigurations()) {
			configureOutlet(languagePreferences, outputConfiguration, request);
		}
		try {
			languagePreferences.flush();
		} catch (BackingStoreException e) {
			throw new RuntimeException(e);
		}
	}

	private void configureOutlet(IEclipsePreferences languagePreferences, OutputConfiguration mavenConfiguration, ProjectConfigurationRequest request) {
		org.eclipse.xtext.generator.OutputConfiguration configuration = mavenConfiguration.toGeneratorConfiguration();
		languagePreferences.put(getKey(configuration, OUTPUT_NAME), configuration.getName());
		languagePreferences.put(getKey(configuration, OUTPUT_DESCRIPTION), configuration.getDescription());
		languagePreferences.put(getKey(configuration, OUTPUT_DIRECTORY), makeProjectRelative(configuration.getOutputDirectory(), request));
		languagePreferences.putBoolean(getKey(configuration, OUTPUT_DERIVED), configuration.isSetDerivedProperty());
		languagePreferences.putBoolean(getKey(configuration, OUTPUT_CREATE_DIRECTORY), configuration.isCreateOutputDirectory());
		languagePreferences.putBoolean(getKey(configuration, OUTPUT_CLEAN_DIRECTORY), configuration.isCanClearOutputDirectory());
		languagePreferences.putBoolean(getKey(configuration, OUTPUT_OVERRIDE), configuration.isOverrideExistingResources());
		languagePreferences.putBoolean(getKey(configuration, OUTPUT_CLEANUP_DERIVED), configuration.isCleanUpDerivedResources());
		languagePreferences.putBoolean(getKey(configuration, INSTALL_DSL_AS_PRIMARY_SOURCE), configuration.isInstallDslAsPrimarySource());
		languagePreferences.putBoolean(getKey(configuration, HIDE_LOCAL_SYNTHETIC_VARIABLES), configuration.isHideSyntheticLocalVariables());
		languagePreferences.putBoolean(getKey(configuration, OUTPUT_KEEP_LOCAL_HISTORY), configuration.isKeepLocalHistory());
		languagePreferences.putBoolean(getKey(configuration, USE_OUTPUT_PER_SOURCE_FOLDER), configuration.isUseOutputPerSourceFolder());
		for (SourceMapping sourceMapping : configuration.getSourceMappings()) {
			languagePreferences.put(getOutputForSourceFolderKey(configuration, makeProjectRelative(sourceMapping.getSourceFolder(), request)),
					makeProjectRelative(Strings.nullToEmpty(sourceMapping.getOutputDirectory()), request));
			languagePreferences.putBoolean(getIgnoreSourceFolderKey(configuration, makeProjectRelative(sourceMapping.getSourceFolder(), request)),
					sourceMapping.isIgnore());
		}
	}
	
	private String makeProjectRelative(String fileName,
			ProjectConfigurationRequest request) {
		File baseDir = getMavenProject(request).getBasedir();
		File file = new File(fileName);
		String relativePath;
		if (file.isAbsolute()) {
			relativePath = baseDir.toURI().relativize(file.toURI()).getPath();
		} else {
			relativePath = file.getPath();
		}
		String unixDelimited = relativePath.replaceAll("\\\\", "/");
		return CharMatcher.is('/').trimFrom(unixDelimited);
	}

	static IProject getProject(ProjectConfigurationRequest request) {
		return request.mavenProjectFacade().getProject();
	}

	static MavenProject getMavenProject(ProjectConfigurationRequest request) {
		return request.mavenProject();
	}
}