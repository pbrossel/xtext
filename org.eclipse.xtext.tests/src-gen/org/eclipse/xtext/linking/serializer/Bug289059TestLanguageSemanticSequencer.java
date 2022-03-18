/*******************************************************************************
 * Copyright (c) 2010, 2022 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.linking.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.linking.bug289059Test.Bug289059TestPackage;
import org.eclipse.xtext.linking.bug289059Test.Model;
import org.eclipse.xtext.linking.bug289059Test.UnassignedAction;
import org.eclipse.xtext.linking.services.Bug289059TestLanguageGrammarAccess;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;

@SuppressWarnings("all")
public class Bug289059TestLanguageSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private Bug289059TestLanguageGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == Bug289059TestPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case Bug289059TestPackage.MODEL:
				sequence_Model(context, (Model) semanticObject); 
				return; 
			case Bug289059TestPackage.UNASSIGNED_ACTION:
				sequence_UnassignedAction(context, (UnassignedAction) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * <pre>
	 * Contexts:
	 *     Model returns Model
	 *
	 * Constraint:
	 *     (name=ID enabled=UnassignedAction? reference=[Model|ID]?)
	 * </pre>
	 */
	protected void sequence_Model(ISerializationContext context, Model semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     UnassignedAction returns UnassignedAction
	 *
	 * Constraint:
	 *     {UnassignedAction}
	 * </pre>
	 */
	protected void sequence_UnassignedAction(ISerializationContext context, UnassignedAction semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
