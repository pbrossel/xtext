/*******************************************************************************
 * Copyright (c) 2010, 2022 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.formatting2.regionaccess.internal.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.formatting2.regionaccess.internal.regionaccesstestlanguage.Add;
import org.eclipse.xtext.formatting2.regionaccess.internal.regionaccesstestlanguage.AssignedAction;
import org.eclipse.xtext.formatting2.regionaccess.internal.regionaccesstestlanguage.Delegate;
import org.eclipse.xtext.formatting2.regionaccess.internal.regionaccesstestlanguage.Delegation;
import org.eclipse.xtext.formatting2.regionaccess.internal.regionaccesstestlanguage.Mixed;
import org.eclipse.xtext.formatting2.regionaccess.internal.regionaccesstestlanguage.Named;
import org.eclipse.xtext.formatting2.regionaccess.internal.regionaccesstestlanguage.PrefixedUnassigned;
import org.eclipse.xtext.formatting2.regionaccess.internal.regionaccesstestlanguage.RegionaccesstestlanguagePackage;
import org.eclipse.xtext.formatting2.regionaccess.internal.regionaccesstestlanguage.Root;
import org.eclipse.xtext.formatting2.regionaccess.internal.regionaccesstestlanguage.RootAction;
import org.eclipse.xtext.formatting2.regionaccess.internal.regionaccesstestlanguage.Simple;
import org.eclipse.xtext.formatting2.regionaccess.internal.regionaccesstestlanguage.ValueList;
import org.eclipse.xtext.formatting2.regionaccess.internal.services.RegionAccessTestLanguageGrammarAccess;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class RegionAccessTestLanguageSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private RegionAccessTestLanguageGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == RegionaccesstestlanguagePackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case RegionaccesstestlanguagePackage.ACTION:
				sequence_Fragment_Mixed(context, (org.eclipse.xtext.formatting2.regionaccess.internal.regionaccesstestlanguage.Action) semanticObject); 
				return; 
			case RegionaccesstestlanguagePackage.ADD:
				sequence_Expression(context, (Add) semanticObject); 
				return; 
			case RegionaccesstestlanguagePackage.ASSIGNED_ACTION:
				sequence_Mixed(context, (AssignedAction) semanticObject); 
				return; 
			case RegionaccesstestlanguagePackage.DELEGATE:
				sequence_Delegate(context, (Delegate) semanticObject); 
				return; 
			case RegionaccesstestlanguagePackage.DELEGATION:
				sequence_Delegation(context, (Delegation) semanticObject); 
				return; 
			case RegionaccesstestlanguagePackage.MIXED:
				sequence_Mixed(context, (Mixed) semanticObject); 
				return; 
			case RegionaccesstestlanguagePackage.NAMED:
				sequence_Primary(context, (Named) semanticObject); 
				return; 
			case RegionaccesstestlanguagePackage.PREFIXED_UNASSIGNED:
				sequence_PrefixedUnassigned(context, (PrefixedUnassigned) semanticObject); 
				return; 
			case RegionaccesstestlanguagePackage.ROOT:
				sequence_Root(context, (Root) semanticObject); 
				return; 
			case RegionaccesstestlanguagePackage.ROOT_ACTION:
				sequence_Root(context, (RootAction) semanticObject); 
				return; 
			case RegionaccesstestlanguagePackage.SIMPLE:
				sequence_Simple(context, (Simple) semanticObject); 
				return; 
			case RegionaccesstestlanguagePackage.VALUE_LIST:
				sequence_ValueList(context, (ValueList) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * <pre>
	 * Contexts:
	 *     Root returns Delegate
	 *     Unassigned returns Delegate
	 *     PrefixedDelegate returns Delegate
	 *     Delegate returns Delegate
	 *
	 * Constraint:
	 *     name=ID
	 * </pre>
	 */
	protected void sequence_Delegate(ISerializationContext context, Delegate semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, RegionaccesstestlanguagePackage.Literals.DELEGATE__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegionaccesstestlanguagePackage.Literals.DELEGATE__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getDelegateAccess().getNameIDTerminalRuleCall_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Root returns Delegation
	 *     Delegation returns Delegation
	 *
	 * Constraint:
	 *     delegate=Delegate
	 * </pre>
	 */
	protected void sequence_Delegation(ISerializationContext context, Delegation semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, RegionaccesstestlanguagePackage.Literals.DELEGATION__DELEGATE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegionaccesstestlanguagePackage.Literals.DELEGATION__DELEGATE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getDelegationAccess().getDelegateDelegateParserRuleCall_1_0(), semanticObject.getDelegate());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Root returns Add
	 *     Expression returns Add
	 *     Expression.Add_1_0 returns Add
	 *     Primary returns Add
	 *     Parenthesized returns Add
	 *
	 * Constraint:
	 *     (left=Expression_Add_1_0 right=Primary)
	 * </pre>
	 */
	protected void sequence_Expression(ISerializationContext context, Add semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, RegionaccesstestlanguagePackage.Literals.ADD__LEFT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegionaccesstestlanguagePackage.Literals.ADD__LEFT));
			if (transientValues.isValueTransient(semanticObject, RegionaccesstestlanguagePackage.Literals.ADD__RIGHT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegionaccesstestlanguagePackage.Literals.ADD__RIGHT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getExpressionAccess().getAddLeftAction_1_0(), semanticObject.getLeft());
		feeder.accept(grammarAccess.getExpressionAccess().getRightPrimaryParserRuleCall_1_2_0(), semanticObject.getRight());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Root returns Action
	 *     Mixed returns Action
	 *     Mixed.AssignedAction_4_0 returns Action
	 *
	 * Constraint:
	 *     (fragName=ID | mixed=Mixed)?
	 * </pre>
	 */
	protected void sequence_Fragment_Mixed(ISerializationContext context, org.eclipse.xtext.formatting2.regionaccess.internal.regionaccesstestlanguage.Action semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Root returns AssignedAction
	 *     Mixed returns AssignedAction
	 *     Mixed.AssignedAction_4_0 returns AssignedAction
	 *
	 * Constraint:
	 *     (child=Mixed_AssignedAction_4_0 body=Mixed?)
	 * </pre>
	 */
	protected void sequence_Mixed(ISerializationContext context, AssignedAction semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Root returns Mixed
	 *     Mixed returns Mixed
	 *     Mixed.AssignedAction_4_0 returns Mixed
	 *
	 * Constraint:
	 *     (name=ID | eobj=Mixed | datatype=Datatype | ref=[Mixed|ID] | lit=Enum)
	 * </pre>
	 */
	protected void sequence_Mixed(ISerializationContext context, Mixed semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Root returns PrefixedUnassigned
	 *     PrefixedUnassigned returns PrefixedUnassigned
	 *
	 * Constraint:
	 *     delegate=PrefixedDelegate
	 * </pre>
	 */
	protected void sequence_PrefixedUnassigned(ISerializationContext context, PrefixedUnassigned semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, RegionaccesstestlanguagePackage.Literals.PREFIXED_UNASSIGNED__DELEGATE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegionaccesstestlanguagePackage.Literals.PREFIXED_UNASSIGNED__DELEGATE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getPrefixedUnassignedAccess().getDelegatePrefixedDelegateParserRuleCall_1_0(), semanticObject.getDelegate());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Root returns Named
	 *     Expression returns Named
	 *     Expression.Add_1_0 returns Named
	 *     Primary returns Named
	 *     Parenthesized returns Named
	 *
	 * Constraint:
	 *     name=ID
	 * </pre>
	 */
	protected void sequence_Primary(ISerializationContext context, Named semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, RegionaccesstestlanguagePackage.Literals.NAMED__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegionaccesstestlanguagePackage.Literals.NAMED__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getPrimaryAccess().getNameIDTerminalRuleCall_0_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Root returns Root
	 *
	 * Constraint:
	 *     mixed=Mixed
	 * </pre>
	 */
	protected void sequence_Root(ISerializationContext context, Root semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, RegionaccesstestlanguagePackage.Literals.ROOT__MIXED) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegionaccesstestlanguagePackage.Literals.ROOT__MIXED));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getRootAccess().getMixedMixedParserRuleCall_6_2_0(), semanticObject.getMixed());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Root returns RootAction
	 *
	 * Constraint:
	 *     mixed=Mixed
	 * </pre>
	 */
	protected void sequence_Root(ISerializationContext context, RootAction semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, RegionaccesstestlanguagePackage.Literals.ROOT__MIXED) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegionaccesstestlanguagePackage.Literals.ROOT__MIXED));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getRootAccess().getMixedMixedParserRuleCall_6_2_0(), semanticObject.getMixed());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Root returns Simple
	 *     Simple returns Simple
	 *
	 * Constraint:
	 *     name=ID
	 * </pre>
	 */
	protected void sequence_Simple(ISerializationContext context, Simple semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, RegionaccesstestlanguagePackage.Literals.SIMPLE__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, RegionaccesstestlanguagePackage.Literals.SIMPLE__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getSimpleAccess().getNameIDTerminalRuleCall_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Root returns ValueList
	 *     ValueList returns ValueList
	 *
	 * Constraint:
	 *     name+=ID*
	 * </pre>
	 */
	protected void sequence_ValueList(ISerializationContext context, ValueList semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
