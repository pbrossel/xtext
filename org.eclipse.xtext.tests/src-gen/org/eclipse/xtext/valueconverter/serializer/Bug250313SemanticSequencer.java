/*******************************************************************************
 * Copyright (c) 2010, 2022 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.valueconverter.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;
import org.eclipse.xtext.valueconverter.bug250313.Bug250313Package;
import org.eclipse.xtext.valueconverter.bug250313.Child1;
import org.eclipse.xtext.valueconverter.bug250313.Child2;
import org.eclipse.xtext.valueconverter.bug250313.Model;
import org.eclipse.xtext.valueconverter.services.Bug250313GrammarAccess;

@SuppressWarnings("all")
public class Bug250313SemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private Bug250313GrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == Bug250313Package.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case Bug250313Package.CHILD1:
				sequence_Child1(context, (Child1) semanticObject); 
				return; 
			case Bug250313Package.CHILD2:
				sequence_Child2(context, (Child2) semanticObject); 
				return; 
			case Bug250313Package.MODEL:
				sequence_Model(context, (Model) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * <pre>
	 * Contexts:
	 *     Child returns Child1
	 *     Child1 returns Child1
	 *
	 * Constraint:
	 *     name=ID
	 * </pre>
	 */
	protected void sequence_Child1(ISerializationContext context, Child1 semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, Bug250313Package.Literals.CHILD__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, Bug250313Package.Literals.CHILD__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getChild1Access().getNameIDTerminalRuleCall_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Child returns Child2
	 *     Child2 returns Child2
	 *
	 * Constraint:
	 *     name=STRING
	 * </pre>
	 */
	protected void sequence_Child2(ISerializationContext context, Child2 semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, Bug250313Package.Literals.CHILD__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, Bug250313Package.Literals.CHILD__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getChild2Access().getNameSTRINGTerminalRuleCall_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Model returns Model
	 *
	 * Constraint:
	 *     (
	 *         value='mykeyword1' | 
	 *         value=STRING | 
	 *         value=NestedDatatype | 
	 *         value=Datatype | 
	 *         value=ID | 
	 *         multiValue+='mykeyword1' | 
	 *         multiValue+=STRING | 
	 *         multiValue+=NestedDatatype | 
	 *         multiValue+=Datatype | 
	 *         multiValue+=ID | 
	 *         value=STRING | 
	 *         multiValue+=STRING | 
	 *         value=Datatype | 
	 *         multiValue+=Datatype | 
	 *         value=NestedDatatype | 
	 *         multiValue+=NestedDatatype | 
	 *         children=Child
	 *     )
	 * </pre>
	 */
	protected void sequence_Model(ISerializationContext context, Model semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
