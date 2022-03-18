/*******************************************************************************
 * Copyright (c) 2010, 2022 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.parser.assignments.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.parser.assignments.bug287184Test.AssociatedDetail;
import org.eclipse.xtext.parser.assignments.bug287184Test.Bug287184TestPackage;
import org.eclipse.xtext.parser.assignments.bug287184Test.Detail;
import org.eclipse.xtext.parser.assignments.bug287184Test.Model;
import org.eclipse.xtext.parser.assignments.services.Bug287184TestLanguageGrammarAccess;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class Bug287184TestLanguageSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private Bug287184TestLanguageGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == Bug287184TestPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case Bug287184TestPackage.ASSOCIATED_DETAIL:
				sequence_AssociatedDetail(context, (AssociatedDetail) semanticObject); 
				return; 
			case Bug287184TestPackage.DETAIL:
				sequence_Detail(context, (Detail) semanticObject); 
				return; 
			case Bug287184TestPackage.MODEL:
				sequence_Model(context, (Model) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * <pre>
	 * Contexts:
	 *     AbstractDetail returns AssociatedDetail
	 *     AssociatedDetail returns AssociatedDetail
	 *
	 * Constraint:
	 *     detailClass=[Model|FQN]
	 * </pre>
	 */
	protected void sequence_AssociatedDetail(ISerializationContext context, AssociatedDetail semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, Bug287184TestPackage.Literals.ABSTRACT_DETAIL__DETAIL_CLASS) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, Bug287184TestPackage.Literals.ABSTRACT_DETAIL__DETAIL_CLASS));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getAssociatedDetailAccess().getDetailClassModelFQNParserRuleCall_1_0_1(), semanticObject.eGet(Bug287184TestPackage.Literals.ABSTRACT_DETAIL__DETAIL_CLASS, false));
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     AbstractDetail returns Detail
	 *     Detail returns Detail
	 *
	 * Constraint:
	 *     ((visibility='private' | visibility='protected' | visibility='public')? detailClass=[Model|FQN])
	 * </pre>
	 */
	protected void sequence_Detail(ISerializationContext context, Detail semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Model returns Model
	 *
	 * Constraint:
	 *     (name=FQN (detail+=Detail | detail+=AssociatedDetail)+)
	 * </pre>
	 */
	protected void sequence_Model(ISerializationContext context, Model semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
