/*******************************************************************************
 * Copyright (c) 2010 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.generator.parser.antlr.debug.formatting;

import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter;
import org.eclipse.xtext.formatting.impl.FormattingConfig;
import org.eclipse.xtext.generator.parser.antlr.debug.services.SimpleAntlrGrammarAccess;

/**
 * This class contains custom formatting description.
 *
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#formatting
 * on how and when to use it
 *
 * Also see {@link org.eclipse.xtext.xtext.XtextFormatter} as an example
 * @since 2.0
 */
public class SimpleAntlrFormatter extends AbstractDeclarativeFormatter {

	@Override
	protected void configureFormatting(FormattingConfig c) {
		c.setLinewrap(2, 2, 2).before(getGrammarAccess().getSL_COMMENTRule());
		c.setLinewrap(0, 1, 2).before(getGrammarAccess().getML_COMMENTRule());
		c.setLinewrap(0, 1, 1).after(getGrammarAccess().getML_COMMENTRule());
		c.setSpace("\n\n").before(getGrammarAccess().getSL_COMMENTRule());
		c.setIndentation(getGrammarAccess().getParenthesizedAccess().getLeftParenthesisKeyword_0(), getGrammarAccess().getParenthesizedAccess().getRightParenthesisKeyword_3());
		c.setIndentation(getGrammarAccess().getRuleAccess().getColonKeyword_2(), getGrammarAccess().getRuleAccess().getSemicolonKeyword_4());
		c.setLinewrap().after(getGrammarAccess().getAlternativesAccess().getVerticalLineKeyword_1_1_0());
		c.setNoSpace().before(getGrammarAccess().getAtomAccess().getCardinalityAssignment_0_1_1());
		c.setLinewrap().around(getGrammarAccess().getAlternativesRule());
		c.setLinewrap().after(getGrammarAccess().getRuleAccess().getColonKeyword_2());
		c.setLinewrap().before(getGrammarAccess().getRuleAccess().getSemicolonKeyword_4());
		c.setLinewrap(2).after(getGrammarAccess().getRuleAccess().getSemicolonKeyword_4());
		c.setLinewrap().between(getGrammarAccess().getML_COMMENTRule(),	getGrammarAccess().getAntlrGrammarAccess().getGrammarKeyword_0());
	}

	@Override
	protected SimpleAntlrGrammarAccess getGrammarAccess() {
		return (SimpleAntlrGrammarAccess) super.getGrammarAccess();
	}
}
