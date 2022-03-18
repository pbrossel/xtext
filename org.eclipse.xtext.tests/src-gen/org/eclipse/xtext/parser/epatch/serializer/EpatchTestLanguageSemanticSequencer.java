/*******************************************************************************
 * Copyright (c) 2010, 2022 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.parser.epatch.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.parser.epatch.epatchTestLanguage.AssignmentValue;
import org.eclipse.xtext.parser.epatch.epatchTestLanguage.EPackageImport;
import org.eclipse.xtext.parser.epatch.epatchTestLanguage.EPatch;
import org.eclipse.xtext.parser.epatch.epatchTestLanguage.EpatchTestLanguagePackage;
import org.eclipse.xtext.parser.epatch.epatchTestLanguage.ExpressionExecutable;
import org.eclipse.xtext.parser.epatch.epatchTestLanguage.ExtensionImport;
import org.eclipse.xtext.parser.epatch.epatchTestLanguage.JavaExecutable;
import org.eclipse.xtext.parser.epatch.epatchTestLanguage.JavaImport;
import org.eclipse.xtext.parser.epatch.epatchTestLanguage.ListAssignment;
import org.eclipse.xtext.parser.epatch.epatchTestLanguage.Migration;
import org.eclipse.xtext.parser.epatch.epatchTestLanguage.NamedResource;
import org.eclipse.xtext.parser.epatch.epatchTestLanguage.ObjectCopy;
import org.eclipse.xtext.parser.epatch.epatchTestLanguage.ObjectNew;
import org.eclipse.xtext.parser.epatch.epatchTestLanguage.ObjectRef;
import org.eclipse.xtext.parser.epatch.epatchTestLanguage.ResourceImport;
import org.eclipse.xtext.parser.epatch.epatchTestLanguage.SingleAssignment;
import org.eclipse.xtext.parser.epatch.services.EpatchTestLanguageGrammarAccess;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class EpatchTestLanguageSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private EpatchTestLanguageGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == EpatchTestLanguagePackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case EpatchTestLanguagePackage.ASSIGNMENT_VALUE:
				if (rule == grammarAccess.getAssignmentValueRule()) {
					sequence_AssignmentValue(context, (AssignmentValue) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getListAssignmentValueRule()) {
					sequence_ListAssignmentValue(context, (AssignmentValue) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getSingleAssignmentValueRule()) {
					sequence_SingleAssignmentValue(context, (AssignmentValue) semanticObject); 
					return; 
				}
				else break;
			case EpatchTestLanguagePackage.EPACKAGE_IMPORT:
				sequence_EPackageImport(context, (EPackageImport) semanticObject); 
				return; 
			case EpatchTestLanguagePackage.EPATCH:
				sequence_EPatch(context, (EPatch) semanticObject); 
				return; 
			case EpatchTestLanguagePackage.EXPRESSION_EXECUTABLE:
				sequence_ExpressionExecutable(context, (ExpressionExecutable) semanticObject); 
				return; 
			case EpatchTestLanguagePackage.EXTENSION_IMPORT:
				sequence_ExtensionImport(context, (ExtensionImport) semanticObject); 
				return; 
			case EpatchTestLanguagePackage.JAVA_EXECUTABLE:
				sequence_JavaExecutable(context, (JavaExecutable) semanticObject); 
				return; 
			case EpatchTestLanguagePackage.JAVA_IMPORT:
				sequence_JavaImport(context, (JavaImport) semanticObject); 
				return; 
			case EpatchTestLanguagePackage.LIST_ASSIGNMENT:
				if (rule == grammarAccess.getBiListAssignmentRule()) {
					sequence_BiListAssignment(context, (ListAssignment) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getAssignmentRule()) {
					sequence_BiListAssignment_MonoListAssignment(context, (ListAssignment) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getMonoListAssignmentRule()) {
					sequence_MonoListAssignment(context, (ListAssignment) semanticObject); 
					return; 
				}
				else break;
			case EpatchTestLanguagePackage.MIGRATION:
				sequence_Migration(context, (Migration) semanticObject); 
				return; 
			case EpatchTestLanguagePackage.NAMED_RESOURCE:
				sequence_NamedResource(context, (NamedResource) semanticObject); 
				return; 
			case EpatchTestLanguagePackage.OBJECT_COPY:
				if (rule == grammarAccess.getNamedObjectRule()
						|| rule == grammarAccess.getCreatedObjectRule()) {
					sequence_CreatedObject_ObjectCopy(context, (ObjectCopy) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getObjectCopyRule()) {
					sequence_ObjectCopy(context, (ObjectCopy) semanticObject); 
					return; 
				}
				else break;
			case EpatchTestLanguagePackage.OBJECT_NEW:
				if (rule == grammarAccess.getNamedObjectRule()
						|| rule == grammarAccess.getCreatedObjectRule()) {
					sequence_CreatedObject_ObjectNew(context, (ObjectNew) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getObjectNewRule()) {
					sequence_ObjectNew(context, (ObjectNew) semanticObject); 
					return; 
				}
				else break;
			case EpatchTestLanguagePackage.OBJECT_REF:
				sequence_ObjectRef(context, (ObjectRef) semanticObject); 
				return; 
			case EpatchTestLanguagePackage.RESOURCE_IMPORT:
				sequence_ResourceImport(context, (ResourceImport) semanticObject); 
				return; 
			case EpatchTestLanguagePackage.SINGLE_ASSIGNMENT:
				if (rule == grammarAccess.getAssignmentRule()) {
					sequence_BiSingleAssignment_MonoSingleAssignment(context, (SingleAssignment) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getBiSingleAssignmentRule()) {
					sequence_BiSingleAssignment(context, (SingleAssignment) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getMonoSingleAssignmentRule()) {
					sequence_MonoSingleAssignment(context, (SingleAssignment) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * <pre>
	 * Contexts:
	 *     AssignmentValue returns AssignmentValue
	 *
	 * Constraint:
	 *     (value=STRING | (refObject=[NamedObject|ID] (refFeature=ID refIndex=INT?)?) | newObject=CreatedObject | (import=[Import|ID] impFrag=FRAGMENT))
	 * </pre>
	 */
	protected void sequence_AssignmentValue(ISerializationContext context, AssignmentValue semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     BiListAssignment returns ListAssignment
	 *
	 * Constraint:
	 *     (
	 *         feature=ID 
	 *         (leftValues+=ListAssignmentValue leftValues+=ListAssignmentValue*)? 
	 *         (rightValues+=ListAssignmentValue rightValues+=ListAssignmentValue*)?
	 *     )
	 * </pre>
	 */
	protected void sequence_BiListAssignment(ISerializationContext context, ListAssignment semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Assignment returns ListAssignment
	 *
	 * Constraint:
	 *     (
	 *         (
	 *             feature=ID 
	 *             (leftValues+=ListAssignmentValue leftValues+=ListAssignmentValue*)? 
	 *             (rightValues+=ListAssignmentValue rightValues+=ListAssignmentValue*)?
	 *         ) | 
	 *         (feature=ID (leftValues+=AssignmentValue leftValues+=AssignmentValue*)?)
	 *     )
	 * </pre>
	 */
	protected void sequence_BiListAssignment_MonoListAssignment(ISerializationContext context, ListAssignment semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Assignment returns SingleAssignment
	 *
	 * Constraint:
	 *     ((feature=ID leftValue=SingleAssignmentValue rightValue=SingleAssignmentValue) | (feature=ID leftValue=SingleAssignmentValue))
	 * </pre>
	 */
	protected void sequence_BiSingleAssignment_MonoSingleAssignment(ISerializationContext context, SingleAssignment semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     BiSingleAssignment returns SingleAssignment
	 *
	 * Constraint:
	 *     (feature=ID leftValue=SingleAssignmentValue rightValue=SingleAssignmentValue)
	 * </pre>
	 */
	protected void sequence_BiSingleAssignment(ISerializationContext context, SingleAssignment semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, EpatchTestLanguagePackage.Literals.ASSIGNMENT__FEATURE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EpatchTestLanguagePackage.Literals.ASSIGNMENT__FEATURE));
			if (transientValues.isValueTransient(semanticObject, EpatchTestLanguagePackage.Literals.SINGLE_ASSIGNMENT__LEFT_VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EpatchTestLanguagePackage.Literals.SINGLE_ASSIGNMENT__LEFT_VALUE));
			if (transientValues.isValueTransient(semanticObject, EpatchTestLanguagePackage.Literals.SINGLE_ASSIGNMENT__RIGHT_VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EpatchTestLanguagePackage.Literals.SINGLE_ASSIGNMENT__RIGHT_VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getBiSingleAssignmentAccess().getFeatureIDTerminalRuleCall_0_0(), semanticObject.getFeature());
		feeder.accept(grammarAccess.getBiSingleAssignmentAccess().getLeftValueSingleAssignmentValueParserRuleCall_2_0(), semanticObject.getLeftValue());
		feeder.accept(grammarAccess.getBiSingleAssignmentAccess().getRightValueSingleAssignmentValueParserRuleCall_4_0(), semanticObject.getRightValue());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     NamedObject returns ObjectCopy
	 *     CreatedObject returns ObjectCopy
	 *
	 * Constraint:
	 *     (
	 *         resource=[NamedResource|ID] 
	 *         fragment=FRAGMENT 
	 *         name=ID? 
	 *         ((assignments+=MonoSingleAssignment | assignments+=MonoListAssignment)+ leftMig=Migration?)?
	 *     )
	 * </pre>
	 */
	protected void sequence_CreatedObject_ObjectCopy(ISerializationContext context, ObjectCopy semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     NamedObject returns ObjectNew
	 *     CreatedObject returns ObjectNew
	 *
	 * Constraint:
	 *     (import=[Import|ID] impFrag=FRAGMENT name=ID? ((assignments+=MonoSingleAssignment | assignments+=MonoListAssignment)+ leftMig=Migration?)?)
	 * </pre>
	 */
	protected void sequence_CreatedObject_ObjectNew(ISerializationContext context, ObjectNew semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Import returns EPackageImport
	 *     ModelImport returns EPackageImport
	 *     EPackageImport returns EPackageImport
	 *
	 * Constraint:
	 *     (name=ID nsURI=STRING)
	 * </pre>
	 */
	protected void sequence_EPackageImport(ISerializationContext context, EPackageImport semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, EpatchTestLanguagePackage.Literals.MODEL_IMPORT__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EpatchTestLanguagePackage.Literals.MODEL_IMPORT__NAME));
			if (transientValues.isValueTransient(semanticObject, EpatchTestLanguagePackage.Literals.EPACKAGE_IMPORT__NS_URI) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EpatchTestLanguagePackage.Literals.EPACKAGE_IMPORT__NS_URI));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getEPackageImportAccess().getNameIDTerminalRuleCall_1_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getEPackageImportAccess().getNsURISTRINGTerminalRuleCall_3_0(), semanticObject.getNsURI());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     EPatch returns EPatch
	 *
	 * Constraint:
	 *     (name=ID imports+=Import* resources+=NamedResource* objects+=ObjectRef*)
	 * </pre>
	 */
	protected void sequence_EPatch(ISerializationContext context, EPatch semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Executable returns ExpressionExecutable
	 *     ExpressionExecutable returns ExpressionExecutable
	 *
	 * Constraint:
	 *     exprstr=STRING
	 * </pre>
	 */
	protected void sequence_ExpressionExecutable(ISerializationContext context, ExpressionExecutable semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, EpatchTestLanguagePackage.Literals.EXPRESSION_EXECUTABLE__EXPRSTR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EpatchTestLanguagePackage.Literals.EXPRESSION_EXECUTABLE__EXPRSTR));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getExpressionExecutableAccess().getExprstrSTRINGTerminalRuleCall_0(), semanticObject.getExprstr());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Import returns ExtensionImport
	 *     ExtensionImport returns ExtensionImport
	 *
	 * Constraint:
	 *     (path+=ID path+=ID*)
	 * </pre>
	 */
	protected void sequence_ExtensionImport(ISerializationContext context, ExtensionImport semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Executable returns JavaExecutable
	 *     JavaExecutable returns JavaExecutable
	 *
	 * Constraint:
	 *     method=ID
	 * </pre>
	 */
	protected void sequence_JavaExecutable(ISerializationContext context, JavaExecutable semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, EpatchTestLanguagePackage.Literals.JAVA_EXECUTABLE__METHOD) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EpatchTestLanguagePackage.Literals.JAVA_EXECUTABLE__METHOD));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getJavaExecutableAccess().getMethodIDTerminalRuleCall_1_0(), semanticObject.getMethod());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Import returns JavaImport
	 *     JavaImport returns JavaImport
	 *
	 * Constraint:
	 *     (path+=ID path+=ID*)
	 * </pre>
	 */
	protected void sequence_JavaImport(ISerializationContext context, JavaImport semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     ListAssignmentValue returns AssignmentValue
	 *
	 * Constraint:
	 *     (
	 *         index=INT 
	 *         (
	 *             refIndex=INT | 
	 *             value=STRING | 
	 *             (refObject=[NamedObject|ID] (refFeature=ID refIndex=INT?)?) | 
	 *             newObject=CreatedObject | 
	 *             (import=[Import|ID] impFrag=FRAGMENT)
	 *         )
	 *     )
	 * </pre>
	 */
	protected void sequence_ListAssignmentValue(ISerializationContext context, AssignmentValue semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Migration returns Migration
	 *
	 * Constraint:
	 *     (first=Executable? (asOp=Executable | eachOp=Executable)?)
	 * </pre>
	 */
	protected void sequence_Migration(ISerializationContext context, Migration semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     MonoListAssignment returns ListAssignment
	 *
	 * Constraint:
	 *     (feature=ID (leftValues+=AssignmentValue leftValues+=AssignmentValue*)?)
	 * </pre>
	 */
	protected void sequence_MonoListAssignment(ISerializationContext context, ListAssignment semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     MonoSingleAssignment returns SingleAssignment
	 *
	 * Constraint:
	 *     (feature=ID leftValue=SingleAssignmentValue)
	 * </pre>
	 */
	protected void sequence_MonoSingleAssignment(ISerializationContext context, SingleAssignment semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, EpatchTestLanguagePackage.Literals.ASSIGNMENT__FEATURE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EpatchTestLanguagePackage.Literals.ASSIGNMENT__FEATURE));
			if (transientValues.isValueTransient(semanticObject, EpatchTestLanguagePackage.Literals.SINGLE_ASSIGNMENT__LEFT_VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EpatchTestLanguagePackage.Literals.SINGLE_ASSIGNMENT__LEFT_VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getMonoSingleAssignmentAccess().getFeatureIDTerminalRuleCall_0_0(), semanticObject.getFeature());
		feeder.accept(grammarAccess.getMonoSingleAssignmentAccess().getLeftValueSingleAssignmentValueParserRuleCall_2_0(), semanticObject.getLeftValue());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     NamedResource returns NamedResource
	 *
	 * Constraint:
	 *     (name=ID (leftUri=STRING | leftRoot=CreatedObject) (rightUri=STRING | rightRoot=CreatedObject))
	 * </pre>
	 */
	protected void sequence_NamedResource(ISerializationContext context, NamedResource semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     ObjectCopy returns ObjectCopy
	 *
	 * Constraint:
	 *     (resource=[NamedResource|ID] fragment=FRAGMENT)
	 * </pre>
	 */
	protected void sequence_ObjectCopy(ISerializationContext context, ObjectCopy semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, EpatchTestLanguagePackage.Literals.OBJECT_COPY__RESOURCE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EpatchTestLanguagePackage.Literals.OBJECT_COPY__RESOURCE));
			if (transientValues.isValueTransient(semanticObject, EpatchTestLanguagePackage.Literals.OBJECT_COPY__FRAGMENT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EpatchTestLanguagePackage.Literals.OBJECT_COPY__FRAGMENT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getObjectCopyAccess().getResourceNamedResourceIDTerminalRuleCall_1_0_1(), semanticObject.eGet(EpatchTestLanguagePackage.Literals.OBJECT_COPY__RESOURCE, false));
		feeder.accept(grammarAccess.getObjectCopyAccess().getFragmentFRAGMENTTerminalRuleCall_2_0(), semanticObject.getFragment());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     ObjectNew returns ObjectNew
	 *
	 * Constraint:
	 *     (import=[Import|ID] impFrag=FRAGMENT)
	 * </pre>
	 */
	protected void sequence_ObjectNew(ISerializationContext context, ObjectNew semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, EpatchTestLanguagePackage.Literals.OBJECT_NEW__IMPORT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EpatchTestLanguagePackage.Literals.OBJECT_NEW__IMPORT));
			if (transientValues.isValueTransient(semanticObject, EpatchTestLanguagePackage.Literals.OBJECT_NEW__IMP_FRAG) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EpatchTestLanguagePackage.Literals.OBJECT_NEW__IMP_FRAG));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getObjectNewAccess().getImportImportIDTerminalRuleCall_1_0_1(), semanticObject.eGet(EpatchTestLanguagePackage.Literals.OBJECT_NEW__IMPORT, false));
		feeder.accept(grammarAccess.getObjectNewAccess().getImpFragFRAGMENTTerminalRuleCall_2_0(), semanticObject.getImpFrag());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     NamedObject returns ObjectRef
	 *     ObjectRef returns ObjectRef
	 *
	 * Constraint:
	 *     (
	 *         name=ID? 
	 *         ((leftRes=[NamedResource|ID] leftFrag=FRAGMENT) | (leftRes=[NamedResource|ID] leftFrag=FRAGMENT rightRes=[NamedResource|ID] rightFrag=FRAGMENT)) 
	 *         ((assignments+=BiSingleAssignment | assignments+=BiListAssignment)+ leftMig=Migration? rightMig=Migration?)?
	 *     )
	 * </pre>
	 */
	protected void sequence_ObjectRef(ISerializationContext context, ObjectRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Import returns ResourceImport
	 *     ModelImport returns ResourceImport
	 *     ResourceImport returns ResourceImport
	 *
	 * Constraint:
	 *     (name=ID uri=STRING)
	 * </pre>
	 */
	protected void sequence_ResourceImport(ISerializationContext context, ResourceImport semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, EpatchTestLanguagePackage.Literals.MODEL_IMPORT__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EpatchTestLanguagePackage.Literals.MODEL_IMPORT__NAME));
			if (transientValues.isValueTransient(semanticObject, EpatchTestLanguagePackage.Literals.RESOURCE_IMPORT__URI) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EpatchTestLanguagePackage.Literals.RESOURCE_IMPORT__URI));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getResourceImportAccess().getNameIDTerminalRuleCall_1_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getResourceImportAccess().getUriSTRINGTerminalRuleCall_3_0(), semanticObject.getUri());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     SingleAssignmentValue returns AssignmentValue
	 *
	 * Constraint:
	 *     (
	 *         keyword='null' | 
	 *         value=STRING | 
	 *         (refObject=[NamedObject|ID] (refFeature=ID refIndex=INT?)?) | 
	 *         newObject=CreatedObject | 
	 *         (import=[Import|ID] impFrag=FRAGMENT)
	 *     )
	 * </pre>
	 */
	protected void sequence_SingleAssignmentValue(ISerializationContext context, AssignmentValue semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
