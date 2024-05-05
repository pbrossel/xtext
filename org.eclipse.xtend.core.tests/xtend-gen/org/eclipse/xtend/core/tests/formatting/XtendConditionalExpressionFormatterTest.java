/**
 * Copyright (c) 2019 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.xtend.core.tests.formatting;

import org.eclipse.xtend2.lib.StringConcatenation;
import org.junit.Test;

/**
 * @author Eva Poell - Initial contribution and API
 */
@SuppressWarnings("all")
public class XtendConditionalExpressionFormatterTest extends AbstractXtendFormatterTest {
  @Test
  public void formatIfElseCondExpSL1() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("c = (a < b) ? 3 : 4");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("c = (a < b) ? 3 : 4");
    _builder_1.newLine();
    this.assertFormattedExpression(_builder.toString(), _builder_1);
  }

  @Test
  public void formatIfElseCondExpSL2() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("c = (a < b) ? 3 : 4");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("c =(a<b)?3:4");
    _builder_1.newLine();
    this.assertFormattedExpression(_builder.toString(), _builder_1);
  }

  @Test
  public void formatIfElseCondExpSL3() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("c = (a < b) ? (b < d) ? 5 : (b < d) ? 7 : 8");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("c =(a<b)?(b<d)?5:(b<d)?7:8");
    _builder_1.newLine();
    this.assertFormattedExpression(_builder.toString(), _builder_1);
  }

  @Test
  public void formatIfElseCondExpML() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("c = (a < b)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("? 3");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(": 4");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("c=(a<b)?");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("3:");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("4");
    _builder_1.newLine();
    this.assertFormattedExpression(_builder.toString(), _builder_1);
  }

  @Test
  public void issue_2337() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("c = (a < b)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("? (x == y) //");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("? 10 //");
    _builder.newLine();
    _builder.append("      ");
    _builder.append(": 11");
    _builder.newLine();
    _builder.append("    ");
    _builder.append(": 4");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("c = (a < b)");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("? (x == y) ");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("? ");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("{ ");
    _builder_1.newLine();
    _builder_1.append("      \t  ");
    _builder_1.append("10 ");
    _builder_1.newLine();
    _builder_1.append("      \t  ");
    _builder_1.append("11");
    _builder_1.newLine();
    _builder_1.append("      \t");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("      \t   ");
    _builder_1.append(": 11");
    _builder_1.newLine();
    _builder_1.append("      \t     ");
    _builder_1.append(": 4");
    _builder_1.newLine();
    this.assertFormattedExpression(_builder.toString(), _builder_1);
    StringConcatenation _builder_2 = new StringConcatenation();
    _builder_2.append("c = (a < b)");
    _builder_2.newLine();
    _builder_2.append("    ");
    _builder_2.append("? (x == y) //");
    _builder_2.newLine();
    _builder_2.append("      ");
    _builder_2.append("? 10 //");
    _builder_2.newLine();
    _builder_2.append("      ");
    _builder_2.append(": 11");
    _builder_2.newLine();
    _builder_2.append("    ");
    _builder_2.append(": 4");
    _builder_2.newLine();
    StringConcatenation _builder_3 = new StringConcatenation();
    _builder_3.append("c = (a < b)");
    _builder_3.newLine();
    _builder_3.append("    ");
    _builder_3.append("? (x == y) //");
    _builder_3.newLine();
    _builder_3.append("      ");
    _builder_3.append("? 10 //");
    _builder_3.newLine();
    _builder_3.append("          ");
    _builder_3.append(": 11");
    _builder_3.newLine();
    _builder_3.append("            ");
    _builder_3.append(": 4");
    _builder_3.newLine();
    this.assertFormattedExpression(_builder_2.toString(), _builder_3);
  }

  @Test
  public void issue_2337_if() {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method xxxxxx(int) is undefined"
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects."
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects."
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects."
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects."
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects."
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects."
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects."
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects."
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects.");
  }

  @Test
  public void issue_2337_if_brackets() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("c = (a < b)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("? (x == y) //");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("? 10 //");
    _builder.newLine();
    _builder.append("      ");
    _builder.append(": 11");
    _builder.newLine();
    _builder.append("    ");
    _builder.append(": 4");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("\t\t\t\t\t\t");
    _builder_1.append("(a < b)\t\t\t\t? (x == y)\t\t\t\t? ");
    _builder_1.newLine();
    _builder_1.append("\t\t\t");
    _builder_1.append("(");
    _builder_1.newLine();
    _builder_1.append("z1 =");
    _builder_1.newLine();
    _builder_1.append(" ");
    _builder_1.append("z2) ? {77");
    _builder_1.newLine();
    _builder_1.append("} ");
    _builder_1.newLine();
    _builder_1.append("\t\t\t");
    _builder_1.newLine();
    _builder_1.append("\t\t\t");
    _builder_1.append(": {88}");
    _builder_1.newLine();
    _builder_1.append("\t\t\t\t");
    _builder_1.newLine();
    _builder_1.append("\t \t");
    _builder_1.append(": ");
    _builder_1.newLine();
    _builder_1.append("\t\t\t\t");
    _builder_1.append("{\t11\t\t}");
    _builder_1.newLine();
    _builder_1.append("\t\t\t\t");
    _builder_1.append(": {");
    _builder_1.newLine();
    _builder_1.append("\t\t\t\t");
    _builder_1.append("4");
    _builder_1.newLine();
    _builder_1.append("\t\t\t");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("\t\t\t");
    _builder_1.newLine();
    _builder_1.append("\t\t\t");
    _builder_1.append("if 4a < b4");
    _builder_1.newLine();
    _builder_1.append("\t\t\t\t");
    _builder_1.append("if  (x == b)");
    _builder_1.newLine();
    _builder_1.append("\t\t\t\t ");
    _builder_1.newLine();
    _builder_1.append("\t\t\t\t");
    _builder_1.append("10");
    _builder_1.newLine();
    _builder_1.append("\t\t\t");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("\t\t\t\t");
    _builder_1.append("else\t}");
    _builder_1.newLine();
    _builder_1.append("\t\t\t\t");
    _builder_1.append("11");
    _builder_1.newLine();
    _builder_1.append("\t\t\t");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("\t\t\t\t");
    _builder_1.append("else\t{");
    _builder_1.newLine();
    _builder_1.append("\t\t\t\t");
    _builder_1.append("4");
    _builder_1.newLine();
    _builder_1.append("\t\t\t");
    _builder_1.append("}\t\t");
    _builder_1.newLine();
    _builder_1.append("\t\t\t");
    _builder_1.newLine();
    this.assertFormattedExpression(_builder.toString(), _builder_1);
  }

  @Test
  public void issue_2337_if_thenBlock_elseBlock() {
    int _xifexpression = (int) 0;
    if ((1 == 2)) {
      _xifexpression = 1;
    } else {
      _xifexpression = 2;
    }
    int x = _xifexpression;
    int _xifexpression_1 = (int) 0;
    if ((1 == 2)) {
      _xifexpression_1 = 1;
    } else {
      _xifexpression_1 = 2;
    }
    int y = _xifexpression_1;
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(1 == 2)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("? {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("1");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("} : {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("2");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("(1 == 2) ? {1} : {2}");
    _builder_1.newLine();
    this.assertFormattedExpression(_builder.toString(), _builder_1);
  }

  @Test
  public void issue_2337_if_thenSimple_elseBlock() {
    int _xifexpression = (int) 0;
    if ((1 == 2)) {
      _xifexpression = 1;
    } else {
      _xifexpression = 2;
    }
    int x = _xifexpression;
    int _xifexpression_1 = (int) 0;
    if ((1 == 2)) {
      _xifexpression_1 = 1;
    } else {
      _xifexpression_1 = 2;
    }
    int y = _xifexpression_1;
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(1 == 2) ? 1 : {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("2");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("(1 == 2)\t? 1\t: {2}");
    _builder_1.newLine();
    this.assertFormattedExpression(_builder.toString(), _builder_1);
  }

  @Test
  public void issue_2337_if_thenIf_thenSimple_elseSimple_elseSimple() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(1 == 2) ? (3 == 4) ? 1 : 2 : 3");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("(1 == 2)\t\t\t? (3 == 4) \t\t\t\t\t? ");
    _builder_1.newLine();
    _builder_1.append("1\t: 2 : ");
    _builder_1.newLine();
    _builder_1.append("3");
    _builder_1.newLine();
    this.assertFormattedExpression(_builder.toString(), _builder_1);
  }

  @Test
  public void issue_2337_if_thenIf_thenSimple_elseBlock_elseSimple() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(1 < 2)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("? 1");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(": {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("2");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("(1 < 2)\t? (3 == 4) ? 1\t: {2} : 3");
    _builder_1.newLine();
    this.assertFormattedExpression(_builder.toString(), _builder_1);
  }

  @Test
  public void issue_2337_if_thenBlock_elseSimple() {
    int _xifexpression = (int) 0;
    if ((1 == 2)) {
      _xifexpression = 1;
    } else {
      _xifexpression = 2;
    }
    int x = _xifexpression;
    int _xifexpression_1 = (int) 0;
    if ((1 == 2)) {
      _xifexpression_1 = 1;
    } else {
      _xifexpression_1 = 2;
    }
    int y = _xifexpression_1;
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(1 == 2)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("? {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("1");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("} : 2");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("(1 == 2) ? {1}: 2");
    _builder_1.newLine();
    this.assertFormattedExpression(_builder.toString(), _builder_1);
  }

  @Test
  public void issue_2337_if_thenSimple_elseSimple() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(1 == 2) ? 1 : 2");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("(1 == 2) ? 1 : 2");
    _builder_1.newLine();
    this.assertFormattedExpression(_builder.toString(), _builder_1);
  }

  @Test
  public void issue_2337_if_thenSimple_elseSimple_multiline() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(1 == 2)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("? 1");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(": 2");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("(1 == 2) ? 1 : ");
    _builder_1.newLine();
    _builder_1.append("2");
    _builder_1.newLine();
    this.assertFormattedExpression(_builder.toString(), _builder_1);
  }

  @Test
  public void issue_2337_if_thenIf_thenIf_thenBlock_elseBlock_elseBlock_elseBlock_01() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(a < b)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("? (x == y)");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("? (z1 = z2)");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("? {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("1");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("} ");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append(": {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("2");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append(": {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("3");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(": {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("4");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("(a < b)\t\t\t\t? (x == y)\t\t\t\t? ");
    _builder_1.newLine();
    _builder_1.append("(z1 = z2) ? {1}");
    _builder_1.newLine();
    _builder_1.append(": {2}");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append(": {\t3\t\t}");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append(": {");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("4");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertFormattedExpression(_builder.toString(), _builder_1);
  }
}
