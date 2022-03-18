/*******************************************************************************
 * Copyright (c) 2010, 2022 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.parsetree.reconstr.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.parsetree.reconstr.serializationerror.Indent;
import org.eclipse.xtext.parsetree.reconstr.serializationerror.Model;
import org.eclipse.xtext.parsetree.reconstr.serializationerror.SerializationerrorPackage;
import org.eclipse.xtext.parsetree.reconstr.serializationerror.TwoOptions;
import org.eclipse.xtext.parsetree.reconstr.serializationerror.TwoRequired;
import org.eclipse.xtext.parsetree.reconstr.services.SerializationErrorTestLanguageGrammarAccess;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class SerializationErrorTestLanguageSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private SerializationErrorTestLanguageGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == SerializationerrorPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case SerializationerrorPackage.INDENT:
				sequence_Indent(context, (Indent) semanticObject); 
				return; 
			case SerializationerrorPackage.MODEL:
				sequence_Model(context, (Model) semanticObject); 
				return; 
			case SerializationerrorPackage.TWO_OPTIONS:
				sequence_TwoOptions(context, (TwoOptions) semanticObject); 
				return; 
			case SerializationerrorPackage.TWO_REQUIRED:
				sequence_TwoRequired(context, (TwoRequired) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * <pre>
	 * Contexts:
	 *     Parenthesis returns Indent
	 *     Test returns Indent
	 *     Indent returns Indent
	 *
	 * Constraint:
	 *     ((req=TwoRequired? opt=TwoOptions indent+=Indent+) | (req=TwoRequired? indent+=Indent+) | indent+=Indent+)?
	 * </pre>
	 */
	protected void sequence_Indent(ISerializationContext context, Indent semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Model returns Model
	 *
	 * Constraint:
	 *     (test=Test | test=Parenthesis)
	 * </pre>
	 */
	protected void sequence_Model(ISerializationContext context, Model semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Parenthesis returns TwoOptions
	 *     Test returns TwoOptions
	 *     TwoOptions returns TwoOptions
	 *
	 * Constraint:
	 *     (one=ID | two=ID)
	 * </pre>
	 */
	protected void sequence_TwoOptions(ISerializationContext context, TwoOptions semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Parenthesis returns TwoRequired
	 *     Test returns TwoRequired
	 *     TwoRequired returns TwoRequired
	 *
	 * Constraint:
	 *     (one=ID two=ID)
	 * </pre>
	 */
	protected void sequence_TwoRequired(ISerializationContext context, TwoRequired semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, SerializationerrorPackage.Literals.TWO_REQUIRED__ONE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, SerializationerrorPackage.Literals.TWO_REQUIRED__ONE));
			if (transientValues.isValueTransient(semanticObject, SerializationerrorPackage.Literals.TWO_REQUIRED__TWO) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, SerializationerrorPackage.Literals.TWO_REQUIRED__TWO));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getTwoRequiredAccess().getOneIDTerminalRuleCall_1_0(), semanticObject.getOne());
		feeder.accept(grammarAccess.getTwoRequiredAccess().getTwoIDTerminalRuleCall_2_0(), semanticObject.getTwo());
		feeder.finish();
	}
	
	
}
