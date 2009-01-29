/*******************************************************************************
 * Copyright (c) 2008 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.parser.packrat.consumers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.parser.packrat.ICharSequenceWithOffset;
import org.eclipse.xtext.parser.packrat.IHiddenTokenHandler;
import org.eclipse.xtext.parser.packrat.IMarkerFactory;
import org.eclipse.xtext.parser.packrat.IHiddenTokenHandler.IHiddenTokenState;
import org.eclipse.xtext.parser.packrat.IMarkerFactory.IMarker;
import org.eclipse.xtext.parser.packrat.matching.ICharacterClass;
import org.eclipse.xtext.parser.packrat.matching.ISequenceMatcher;
import org.eclipse.xtext.parser.packrat.tokens.ErrorToken;
import org.eclipse.xtext.parser.packrat.tokens.IParsedTokenAcceptor;
import org.eclipse.xtext.parser.packrat.tokens.ParsedNonTerminal;
import org.eclipse.xtext.parser.packrat.tokens.ParsedNonTerminalEnd;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
public abstract class NonTerminalConsumer implements INonTerminalConsumer {
	
	private final IParsedTokenAcceptor acceptor;
	
	private final IHiddenTokenHandler hiddenTokenHandler;
	
	private final IMarkerFactory markerFactory;
	
	private final ICharSequenceWithOffset input;

	private final ITerminalConsumer[] hiddenTokens;
	
	private final IConsumerUtility consumerUtil;

	/**
	 * @param consumerUtil TODO
	 * @param abstractPackratParser
	 */
	protected NonTerminalConsumer(ICharSequenceWithOffset input, IMarkerFactory markerFactory, 
			IParsedTokenAcceptor tokenAcceptor,	IHiddenTokenHandler hiddenTokenHandler,
			IConsumerUtility consumerUtil, ITerminalConsumer[] hiddenTokens) {
		this.input = input;
		this.markerFactory = markerFactory;
		this.acceptor = tokenAcceptor;
		this.hiddenTokenHandler = hiddenTokenHandler;
		this.consumerUtil = consumerUtil;
		this.hiddenTokens = hiddenTokens;
	}

	public int consume(String feature, boolean isMany, boolean isDatatype, AbstractElement grammarElement) throws Exception {
		IHiddenTokenState prevState = hiddenTokenHandler.replaceHiddenTokens(hiddenTokens);
		IMarker marker = mark();
		int prevOffset = getOffset();
		acceptor.accept(new ParsedNonTerminal(input.getOffset(), grammarElement != null ? grammarElement : getGrammarElement(), getDefaultTypeName()));
		int result = doConsume();
		if (result != ConsumeResult.SUCCESS) {
			acceptor.accept(new ErrorToken(prevOffset, 0, null, "Expected " + getDefaultTypeName() + ", but could not find."));
		}
		acceptor.accept(new ParsedNonTerminalEnd(input.getOffset(), feature, isMany, isDatatype));
		marker.commit();
		prevState.restore();
		return result;
	}
	
	public boolean isDefiningHiddens() {
		return hiddenTokens != null;
	}

	public int consumeAsRoot(IRootConsumerListener listener) throws Exception {
		IHiddenTokenState prevState = hiddenTokenHandler.replaceHiddenTokens(hiddenTokens);
		IMarker marker = mark();
		acceptor.accept(new ParsedNonTerminal(input.getOffset(), getGrammarElement(), getDefaultTypeName()));
		int prevOffset = getOffset();
		int result = doConsume();
		if (result == ConsumeResult.SUCCESS) {
			result = listener.beforeNonTerminalEnd(this);
			if (result != ConsumeResult.SUCCESS)
				acceptor.accept(new ErrorToken(getOffset(), 0, null, "Expected <EOF>."));
			acceptor.accept(new ParsedNonTerminalEnd(input.getOffset(), null, false, false));
		} else {
			acceptor.accept(new ErrorToken(prevOffset, 0, null, "Expected " + getDefaultTypeName() + ", but could not find."));
		}
		marker.commit();
		prevState.restore();
		return result;
	}
	
	protected final IMarker mark() {
		return markerFactory.mark();
	}
	
	protected final void error(String message, AbstractElement grammarElement) {
		acceptor.accept(new ErrorToken(getOffset(), 0, grammarElement, message));
	}
	
	protected final int getOffset() {
		return input.getOffset();
	}
	
	protected final int consumeKeyword(Keyword keyword, String feature, boolean isMany, boolean isBoolean, ICharacterClass notFollowedBy) {
		return consumerUtil.consumeKeyword(keyword, feature, isMany, isBoolean, notFollowedBy);
	}
	
	protected final int consumeTerminal(ITerminalConsumer consumer, String feature, boolean isMany, boolean isBoolean, AbstractElement grammarElement, ISequenceMatcher notMatching) {
		return consumerUtil.consumeTerminal(consumer, feature, isMany, isBoolean, grammarElement, notMatching);
	}
	
	protected final int consumeNonTerminal(INonTerminalConsumer consumer, String feature, boolean isMany, boolean isDatatype, AbstractElement grammarElement) throws Exception {
		return consumerUtil.consumeNonTerminal(consumer, feature, isMany, isDatatype, grammarElement);
	}
	
	protected final void consumeAction(Action action, String typeName, boolean isMany) {
		consumerUtil.consumeAction(action, typeName, isMany);
	}
	
	protected abstract int doConsume() throws Exception;
	
	// TODO: replace by getDefaultType: EClassifier
	protected abstract String getDefaultTypeName(); 
	
	protected abstract EObject getGrammarElement();

	@Override
	public String toString() {
		return "NonTerminalConsumer " + getClass().getSimpleName() + " for type '" + getDefaultTypeName()  + "'";
	}
	
}