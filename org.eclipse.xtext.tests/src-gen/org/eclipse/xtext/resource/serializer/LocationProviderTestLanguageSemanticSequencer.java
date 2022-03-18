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
import org.eclipse.xtext.resource.locationprovidertest.Bus;
import org.eclipse.xtext.resource.locationprovidertest.Data;
import org.eclipse.xtext.resource.locationprovidertest.Element;
import org.eclipse.xtext.resource.locationprovidertest.LocationprovidertestPackage;
import org.eclipse.xtext.resource.locationprovidertest.Mode;
import org.eclipse.xtext.resource.locationprovidertest.Model;
import org.eclipse.xtext.resource.locationprovidertest.Port;
import org.eclipse.xtext.resource.locationprovidertest.Processor;
import org.eclipse.xtext.resource.locationprovidertest.Transition;
import org.eclipse.xtext.resource.services.LocationProviderTestLanguageGrammarAccess;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class LocationProviderTestLanguageSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private LocationProviderTestLanguageGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == LocationprovidertestPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case LocationprovidertestPackage.BUS:
				if (rule == grammarAccess.getBusRule()) {
					sequence_Bus(context, (Bus) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getComponentRule()) {
					sequence_Bus_Component(context, (Bus) semanticObject); 
					return; 
				}
				else break;
			case LocationprovidertestPackage.DATA:
				sequence_Data(context, (Data) semanticObject); 
				return; 
			case LocationprovidertestPackage.ELEMENT:
				sequence_Element(context, (Element) semanticObject); 
				return; 
			case LocationprovidertestPackage.MODE:
				sequence_Mode(context, (Mode) semanticObject); 
				return; 
			case LocationprovidertestPackage.MODEL:
				sequence_Model(context, (Model) semanticObject); 
				return; 
			case LocationprovidertestPackage.PORT:
				sequence_Port(context, (Port) semanticObject); 
				return; 
			case LocationprovidertestPackage.PROCESSOR:
				if (rule == grammarAccess.getComponentRule()) {
					sequence_Component_Processor(context, (Processor) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getProcessorRule()) {
					sequence_Processor(context, (Processor) semanticObject); 
					return; 
				}
				else break;
			case LocationprovidertestPackage.TRANSITION:
				sequence_Transition(context, (Transition) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * <pre>
	 * Contexts:
	 *     Bus returns Bus
	 *
	 * Constraint:
	 *     (name=ID port+=Port*)
	 * </pre>
	 */
	protected void sequence_Bus(ISerializationContext context, Bus semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Component returns Bus
	 *
	 * Constraint:
	 *     (name=ID port+=Port* (mode+=Mode | transition+=Transition)*)
	 * </pre>
	 */
	protected void sequence_Bus_Component(ISerializationContext context, Bus semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Component returns Processor
	 *
	 * Constraint:
	 *     (name=ID data+=Data* (mode+=Mode | transition+=Transition)*)
	 * </pre>
	 */
	protected void sequence_Component_Processor(ISerializationContext context, Processor semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Data returns Data
	 *
	 * Constraint:
	 *     name=ID
	 * </pre>
	 */
	protected void sequence_Data(ISerializationContext context, Data semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, LocationprovidertestPackage.Literals.DATA__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, LocationprovidertestPackage.Literals.DATA__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getDataAccess().getNameIDTerminalRuleCall_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Element returns Element
	 *
	 * Constraint:
	 *     (name=ID singleref=[Element|ID]? multirefs+=[Element|ID]*)
	 * </pre>
	 */
	protected void sequence_Element(ISerializationContext context, Element semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Mode returns Mode
	 *
	 * Constraint:
	 *     name=ID
	 * </pre>
	 */
	protected void sequence_Mode(ISerializationContext context, Mode semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, LocationprovidertestPackage.Literals.MODE__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, LocationprovidertestPackage.Literals.MODE__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getModeAccess().getNameIDTerminalRuleCall_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Model returns Model
	 *
	 * Constraint:
	 *     ((elements+=Element+ components+=Component+) | components+=Component+)?
	 * </pre>
	 */
	protected void sequence_Model(ISerializationContext context, Model semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Port returns Port
	 *
	 * Constraint:
	 *     name=ID
	 * </pre>
	 */
	protected void sequence_Port(ISerializationContext context, Port semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, LocationprovidertestPackage.Literals.PORT__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, LocationprovidertestPackage.Literals.PORT__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getPortAccess().getNameIDTerminalRuleCall_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Processor returns Processor
	 *
	 * Constraint:
	 *     (name=ID data+=Data*)
	 * </pre>
	 */
	protected void sequence_Processor(ISerializationContext context, Processor semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Transition returns Transition
	 *
	 * Constraint:
	 *     (name=ID source=[Mode|ID] destination=[Mode|ID])
	 * </pre>
	 */
	protected void sequence_Transition(ISerializationContext context, Transition semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, LocationprovidertestPackage.Literals.TRANSITION__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, LocationprovidertestPackage.Literals.TRANSITION__NAME));
			if (transientValues.isValueTransient(semanticObject, LocationprovidertestPackage.Literals.TRANSITION__SOURCE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, LocationprovidertestPackage.Literals.TRANSITION__SOURCE));
			if (transientValues.isValueTransient(semanticObject, LocationprovidertestPackage.Literals.TRANSITION__DESTINATION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, LocationprovidertestPackage.Literals.TRANSITION__DESTINATION));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getTransitionAccess().getNameIDTerminalRuleCall_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getTransitionAccess().getSourceModeIDTerminalRuleCall_2_0_1(), semanticObject.eGet(LocationprovidertestPackage.Literals.TRANSITION__SOURCE, false));
		feeder.accept(grammarAccess.getTransitionAccess().getDestinationModeIDTerminalRuleCall_4_0_1(), semanticObject.eGet(LocationprovidertestPackage.Literals.TRANSITION__DESTINATION, false));
		feeder.finish();
	}
	
	
}
