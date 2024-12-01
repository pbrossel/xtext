/*******************************************************************************
 * Copyright (c) 2013, 2022 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtend.m2e;

import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.HIDE_LOCAL_SYNTHETIC_VARIABLES;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.INSTALL_DSL_AS_PRIMARY_SOURCE;
import static org.eclipse.xtext.builder.EclipseOutputConfigurationProvider.USE_OUTPUT_PER_SOURCE_FOLDER;
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
import org.eclipse.xtend.core.compiler.XtendOutputConfigurationProvider;
import org.eclipse.xtext.builder.preferences.BuilderConfigurationBlock;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.generator.OutputConfiguration.SourceMapping;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.ui.preferences.OptionsConfigurationBlock;
import org.eclipse.xtext.util.RuntimeIOException;
import org.osgi.service.prefs.BackingStoreException;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;

public class XtendProjectConfigurator extends AbstractProjectConfigurator {

	@Override
	public void configure(ProjectConfigurationRequest request, IProgressMonitor monitor) throws CoreException {
		addNature(getProject(request), XtextProjectHelper.NATURE_ID, monitor);

		OutputConfiguration config = new XtendOutputConfigurationProvider().getOutputConfigurations().iterator().next();

		List<MojoExecution> executions = getMojoExecutions(request, monitor);
		SubMonitor progress = SubMonitor.convert(monitor, executions.size());
		for (MojoExecution execution : executions) {
			String goal = execution.getGoal();
			if (goal.equals("compile")) {
				readCompileConfig(config, request, execution, progress.split(1));
			} else if (goal.equals("testCompile")) {
				readTestCompileConfig(config, request, execution, progress.split(1));
			} else if (goal.equals("xtend-install-debug-info")) {
				readDebugInfoConfig(config, request, execution, progress.split(1));
			} else if (goal.equals("xtend-test-install-debug-info")) {
				readTestDebugInfoConfig(config, request, execution, progress.split(1));
			}
		}

		writePreferences(config, getProject(request));
	}

	private void writePreferences(OutputConfiguration configuration,
			IProject project) {
		ProjectScope projectPreferences = new ProjectScope(project);
		IEclipsePreferences languagePreferences = projectPreferences
				.getNode("org.eclipse.xtend.core.Xtend");
		languagePreferences.putBoolean(
				OptionsConfigurationBlock.isProjectSpecificPropertyKey(BuilderConfigurationBlock.PROPERTY_PREFIX), true);
		languagePreferences.putBoolean(
				getKey(configuration, INSTALL_DSL_AS_PRIMARY_SOURCE),
				configuration.isInstallDslAsPrimarySource());
		languagePreferences.putBoolean(
				getKey(configuration, HIDE_LOCAL_SYNTHETIC_VARIABLES),
				configuration.isHideSyntheticLocalVariables());
		languagePreferences.putBoolean(
				getKey(configuration, USE_OUTPUT_PER_SOURCE_FOLDER),
				true);
		for (SourceMapping sourceMapping : configuration.getSourceMappings()) {
			languagePreferences.put(
					getOutputForSourceFolderKey(configuration,
							sourceMapping.getSourceFolder()),
					Strings.nullToEmpty(sourceMapping.getOutputDirectory()));
		}

		try {
			languagePreferences.flush();
		} catch (BackingStoreException e) {
			throw new RuntimeIOException(e);
		}
	}

	private void readCompileConfig(OutputConfiguration config, ProjectConfigurationRequest request,
			MojoExecution execution, SubMonitor progress) throws CoreException {
		List<String> roots = getMavenProject(request).getCompileSourceRoots();
		progress = SubMonitor.convert(progress, roots.size());
		for (String source : roots) {
			SourceMapping mapping = new SourceMapping(makeProjectRelative(source, request));
			String outputDirectory = mojoParameterValue("outputDirectory", String.class, request, execution, progress);
			mapping.setOutputDirectory(makeProjectRelative(outputDirectory, request));
			config.getSourceMappings().add(mapping);
		}
	}

	private void readTestCompileConfig(OutputConfiguration config, ProjectConfigurationRequest request,
			MojoExecution execution, SubMonitor progress) throws CoreException {
		List<String> roots = getMavenProject(request).getTestCompileSourceRoots();
		progress = SubMonitor.convert(progress, roots.size());
		for (String source : roots) {
			SourceMapping mapping = new SourceMapping(makeProjectRelative(source, request));
			String testOutputDirectory = mojoParameterValue("testOutputDirectory", String.class, request, execution, progress);
			mapping.setOutputDirectory(makeProjectRelative(testOutputDirectory, request));
			config.getSourceMappings().add(mapping);
		}
	}

	private String makeProjectRelative(String fileName, ProjectConfigurationRequest request) {
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

	private void readDebugInfoConfig(OutputConfiguration config, ProjectConfigurationRequest request,
			MojoExecution execution, SubMonitor progress) throws CoreException {
		progress = SubMonitor.convert(progress, 2);
		config.setHideSyntheticLocalVariables(
				mojoParameterValue("hideSyntheticVariables", Boolean.class, request, execution, progress));
		config.setInstallDslAsPrimarySource(
				mojoParameterValue("xtendAsPrimaryDebugSource", Boolean.class, request, execution, progress));
	}

	private void readTestDebugInfoConfig(OutputConfiguration config, ProjectConfigurationRequest request,
			MojoExecution execution, SubMonitor progress) throws CoreException {
		progress = SubMonitor.convert(progress, 2);
		config.setHideSyntheticLocalVariables(
				mojoParameterValue("hideSyntheticVariables", Boolean.class, request, execution, progress));
		config.setInstallDslAsPrimarySource(
				mojoParameterValue("xtendAsPrimaryDebugSource", Boolean.class, request, execution, progress));
	}

	private <T> T mojoParameterValue(String paramName, Class<T> paramType, ProjectConfigurationRequest request,
			MojoExecution execution, SubMonitor progress) throws CoreException {
		return maven.getMojoParameterValue(getMavenProject(request), execution, paramName, paramType, progress.split(1));
	}

	static IProject getProject(ProjectConfigurationRequest request) {
		return request.mavenProjectFacade().getProject();
	}

	static MavenProject getMavenProject(ProjectConfigurationRequest request) {
		return request.mavenProject();
	}
}