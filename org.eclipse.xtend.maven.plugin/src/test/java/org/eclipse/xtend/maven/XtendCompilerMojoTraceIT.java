/*******************************************************************************
 * Copyright (c) 2013, 2016 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtend.maven;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.maven.it.VerificationException;
import org.apache.maven.it.Verifier;
import org.eclipse.xtext.generator.trace.AbstractTraceRegion;
import org.eclipse.xtext.generator.trace.TraceRegionSerializer;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class XtendCompilerMojoTraceIT {

	private static String ROOT = MavenVerifierUtil.IT_ROOT + "/compile";
	
	@BeforeClass
	static public void setUpOnce() throws IOException, VerificationException {
		MavenVerifierUtil.checkMavenExecutable(ROOT);
	}
	
	private String loadTraceSourcePath(String file) throws FileNotFoundException, IOException {
		try (FileInputStream in = new FileInputStream(file)) {
			AbstractTraceRegion region = new TraceRegionSerializer().readTraceRegionFrom(in);
			return region.getAssociatedSrcRelativePath().toString();
		}
	}

	@Test
	public void traceEnabled() throws Exception {
		Verifier verifier = verifyErrorFreeLog(ROOT + "/trace_enabled");
		String xtendGenDir = verifier.getBasedir() + "/src/main/generated-sources/xtend/";
		verifier.verifyFilePresent(xtendGenDir + "test/.XtendA.java._trace");
		verifier.verifyFilePresent(xtendGenDir + "test/.XtendA2.java._trace");
		Assert.assertEquals("test/XtendA.xtend", loadTraceSourcePath(xtendGenDir + "test/.XtendA.java._trace"));
		Assert.assertEquals("test/XtendA.xtend", loadTraceSourcePath(xtendGenDir + "test/.XtendA2.java._trace"));
	}

	@Test
	public void traceDisabled() throws Exception {
		Verifier verifier = verifyErrorFreeLog(ROOT + "/trace_disabled");
		String xtendGenDir = verifier.getBasedir() + "/src/main/generated-sources/xtend/";
		verifier.verifyFileNotPresent(xtendGenDir + "test/.XtendA.java._trace");
		verifier.verifyFileNotPresent(xtendGenDir + "test/.XtendA2.java._trace");
	}

	@Test
	public void traceWithTestSrc() throws Exception {
		Verifier verifier = verifyErrorFreeLog(ROOT + "/trace_withtestsrc");
		String xtendGenDir = verifier.getBasedir() + "/src/";
		verifier.verifyFileNotPresent(xtendGenDir + "main/generated-sources/xtend/test/.FooTest.java._trace");
		verifier.verifyFileNotPresent(xtendGenDir + "test/generated-sources/xtend/test/.FooTest.java._trace");
	}

	private Verifier verifyErrorFreeLog(String pathToTestProject) throws IOException, VerificationException {
		return verifyErrorFreeLog(pathToTestProject, "verify");
	}

	private Verifier verifyErrorFreeLog(String pathToTestProject, String goal) throws IOException,
			VerificationException {
		Verifier verifier = newVerifier(pathToTestProject);
		verifier.executeGoal(goal);
		verifier.verifyErrorFreeLog();
		verifier.setDebug(true);
		verifier.resetStreams();
		return verifier;
	}

	private Verifier newVerifier(String pathToTestProject) throws IOException, VerificationException {
		return MavenVerifierUtil.newVerifier(pathToTestProject);
	}
}
