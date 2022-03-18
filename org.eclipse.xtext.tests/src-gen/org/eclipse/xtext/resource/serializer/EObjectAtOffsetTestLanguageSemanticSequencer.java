/*******************************************************************************
 * Copyright (c) 2010, 2022 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.resource.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.resource.eObjectAtOffsetTestLanguage.Bar;
import org.eclipse.xtext.resource.eObjectAtOffsetTestLanguage.EObjectAtOffsetTestLanguagePackage;
import org.eclipse.xtext.resource.eObjectAtOffsetTestLanguage.Foo;
import org.eclipse.xtext.resource.eObjectAtOffsetTestLanguage.FooBar;
import org.eclipse.xtext.resource.eObjectAtOffsetTestLanguage.Model;
import org.eclipse.xtext.resource.services.EObjectAtOffsetTestLanguageGrammarAccess;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class EObjectAtOffsetTestLanguageSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private EObjectAtOffsetTestLanguageGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == EObjectAtOffsetTestLanguagePackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case EObjectAtOffsetTestLanguagePackage.BAR:
				if (rule == grammarAccess.getAbstractBarRule()) {
					sequence_AbstractBar_Bar(context, (Bar) semanticObject); 
					return; 
				}
				else if (action == grammarAccess.getAbstractBarAccess().getFooBarBarAction_3_0()
						|| rule == grammarAccess.getBarRule()) {
					sequence_Bar(context, (Bar) semanticObject); 
					return; 
				}
				else break;
			case EObjectAtOffsetTestLanguagePackage.FOO:
				sequence_Foo(context, (Foo) semanticObject); 
				return; 
			case EObjectAtOffsetTestLanguagePackage.FOO_BAR:
				sequence_AbstractBar(context, (FooBar) semanticObject); 
				return; 
			case EObjectAtOffsetTestLanguagePackage.MODEL:
				sequence_Model(context, (Model) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * <pre>
	 * Contexts:
	 *     AbstractBar returns Bar
	 *
	 * Constraint:
	 *     (name=ID foo+=[Foo|QualifiedNameWithOtherDelim] foo+=[Foo|QualifiedNameWithOtherDelim]* foo+=[Foo|QualifiedNameWithOtherDelim]?)
	 * </pre>
	 */
	protected void sequence_AbstractBar_Bar(ISerializationContext context, Bar semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     AbstractBar returns FooBar
	 *
	 * Constraint:
	 *     (bar=AbstractBar_FooBar_3_0 foo+=[Foo|QualifiedNameWithOtherDelim] foo+=[Foo|QualifiedNameWithOtherDelim]?)
	 * </pre>
	 */
	protected void sequence_AbstractBar(ISerializationContext context, FooBar semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     AbstractBar.FooBar_3_0 returns Bar
	 *     Bar returns Bar
	 *
	 * Constraint:
	 *     (name=ID foo+=[Foo|QualifiedNameWithOtherDelim] foo+=[Foo|QualifiedNameWithOtherDelim]*)
	 * </pre>
	 */
	protected void sequence_Bar(ISerializationContext context, Bar semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Foo returns Foo
	 *
	 * Constraint:
	 *     name=QualifiedNameWithOtherDelim
	 * </pre>
	 */
	protected void sequence_Foo(ISerializationContext context, Foo semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, EObjectAtOffsetTestLanguagePackage.Literals.FOO__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EObjectAtOffsetTestLanguagePackage.Literals.FOO__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getFooAccess().getNameQualifiedNameWithOtherDelimParserRuleCall_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Model returns Model
	 *
	 * Constraint:
	 *     (foos+=Foo | bars+=AbstractBar)+
	 * </pre>
	 */
	protected void sequence_Model(ISerializationContext context, Model semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
