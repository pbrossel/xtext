/**
 * Copyright (c) 2015, 2024 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.xtend.core.tests.compiler;

import org.eclipse.xtend2.lib.StringConcatenation;
import org.junit.Test;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
@SuppressWarnings("all")
public class CompilerBug461923Test extends AbstractXtendCompilerTest {
  @Test
  public void test_01() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.util.List");
    _builder.newLine();
    _builder.append("import com.google.common.collect.ImmutableList");
    _builder.newLine();
    _builder.newLine();
    _builder.append("class C {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("def static <T> m(List<? extends T> list, T value) {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("ImmutableList.builder.addAll(list.filter[it != value]).build");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import com.google.common.collect.ImmutableList;");
    _builder_1.newLine();
    _builder_1.append("import java.util.List;");
    _builder_1.newLine();
    _builder_1.append("import java.util.Objects;");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.xtext.xbase.lib.Functions.Function1;");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.xtext.xbase.lib.IterableExtensions;");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("@SuppressWarnings(\"all\")");
    _builder_1.newLine();
    _builder_1.append("public class C {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("public static <T extends Object> ImmutableList<T> m(final List<? extends T> list, final T value) {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("final Function1<T, Boolean> _function = (T it) -> {");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("return Boolean.valueOf((!Objects.equals(it, value)));");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("};");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("return ImmutableList.<T>builder().addAll(IterableExtensions.filter(list, _function)).build();");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertCompilesTo(_builder, _builder_1);
  }

  @Test
  public void test_02() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.util.List");
    _builder.newLine();
    _builder.append("import com.google.common.collect.ImmutableList");
    _builder.newLine();
    _builder.newLine();
    _builder.append("class C {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("def static <T> m(List<T> list, T value) {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("ImmutableList.builder.addAll(list.filter[it != value]).build");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import com.google.common.collect.ImmutableList;");
    _builder_1.newLine();
    _builder_1.append("import java.util.List;");
    _builder_1.newLine();
    _builder_1.append("import java.util.Objects;");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.xtext.xbase.lib.Functions.Function1;");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.xtext.xbase.lib.IterableExtensions;");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("@SuppressWarnings(\"all\")");
    _builder_1.newLine();
    _builder_1.append("public class C {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("public static <T extends Object> ImmutableList<T> m(final List<T> list, final T value) {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("final Function1<T, Boolean> _function = (T it) -> {");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("return Boolean.valueOf((!Objects.equals(it, value)));");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("};");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("return ImmutableList.<T>builder().addAll(IterableExtensions.<T>filter(list, _function)).build();");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertCompilesTo(_builder, _builder_1);
  }

  @Test
  public void test_03() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.util.List");
    _builder.newLine();
    _builder.append("import com.google.common.collect.ImmutableList");
    _builder.newLine();
    _builder.newLine();
    _builder.append("class C {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("def static <T> m(List<? super T> list, T value) {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("ImmutableList.builder.addAll(list.filter[it != value]).build");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import com.google.common.collect.ImmutableList;");
    _builder_1.newLine();
    _builder_1.append("import java.util.List;");
    _builder_1.newLine();
    _builder_1.append("import java.util.Objects;");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.xtext.xbase.lib.Functions.Function1;");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.xtext.xbase.lib.IterableExtensions;");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("@SuppressWarnings(\"all\")");
    _builder_1.newLine();
    _builder_1.append("public class C {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("public static <T extends Object> ImmutableList<Object> m(final List<? super T> list, final T value) {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("final Function1<Object, Boolean> _function = (Object it) -> {");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("return Boolean.valueOf((!Objects.equals(it, value)));");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("};");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("return ImmutableList.<Object>builder().addAll(IterableExtensions.filter(list, _function)).build();");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertCompilesTo(_builder, _builder_1);
  }

  @Test
  public void test_04() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.util.List");
    _builder.newLine();
    _builder.append("import com.google.common.collect.ImmutableList");
    _builder.newLine();
    _builder.newLine();
    _builder.append("class C {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("def static <T> m(List<? extends T> list) {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("ImmutableList.builder.addAll(list).addAll(list).build");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import com.google.common.collect.ImmutableList;");
    _builder_1.newLine();
    _builder_1.append("import java.util.List;");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("@SuppressWarnings(\"all\")");
    _builder_1.newLine();
    _builder_1.append("public class C {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("public static <T extends Object> ImmutableList<T> m(final List<? extends T> list) {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("return ImmutableList.<T>builder().addAll(list).addAll(list).build();");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertCompilesTo(_builder, _builder_1);
  }

  @Test
  public void test_05() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.util.List");
    _builder.newLine();
    _builder.append("import com.google.common.collect.ImmutableList");
    _builder.newLine();
    _builder.newLine();
    _builder.append("class C {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("def static <T> m(List<? extends T> list) {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("ImmutableList.builder.addAll(list.filter[true]).addAll(list).build");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import com.google.common.collect.ImmutableList;");
    _builder_1.newLine();
    _builder_1.append("import java.util.List;");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.xtext.xbase.lib.Functions.Function1;");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.xtext.xbase.lib.IterableExtensions;");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("@SuppressWarnings(\"all\")");
    _builder_1.newLine();
    _builder_1.append("public class C {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("public static <T extends Object> ImmutableList<T> m(final List<? extends T> list) {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("final Function1<T, Boolean> _function = (T it) -> {");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("return Boolean.valueOf(true);");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("};");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("return ImmutableList.<T>builder().addAll(IterableExtensions.filter(list, _function)).addAll(list).build();");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertCompilesTo(_builder, _builder_1);
  }

  @Test
  public void test_06() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.util.List");
    _builder.newLine();
    _builder.append("import com.google.common.collect.ImmutableList");
    _builder.newLine();
    _builder.newLine();
    _builder.append("class C {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("def static <T> m(List<? extends T> list) {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("ImmutableList.builder.addAll(list).addAll(list.filter[false]).build");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import com.google.common.collect.ImmutableList;");
    _builder_1.newLine();
    _builder_1.append("import java.util.List;");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.xtext.xbase.lib.Functions.Function1;");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.xtext.xbase.lib.IterableExtensions;");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("@SuppressWarnings(\"all\")");
    _builder_1.newLine();
    _builder_1.append("public class C {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("public static <T extends Object> ImmutableList<T> m(final List<? extends T> list) {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("final Function1<T, Boolean> _function = (T it) -> {");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("return Boolean.valueOf(false);");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("};");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("return ImmutableList.<T>builder().addAll(list).addAll(IterableExtensions.filter(list, _function)).build();");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertCompilesTo(_builder, _builder_1);
  }

  @Test
  public void test_07() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.util.List");
    _builder.newLine();
    _builder.append("import com.google.common.collect.ImmutableList");
    _builder.newLine();
    _builder.newLine();
    _builder.append("class C {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("def static <T> m(List<? extends T> list) {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("ImmutableList.builder.addAll(list.filter[true]).addAll(list.filter[false]).build");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import com.google.common.collect.ImmutableList;");
    _builder_1.newLine();
    _builder_1.append("import java.util.List;");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.xtext.xbase.lib.Functions.Function1;");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.xtext.xbase.lib.IterableExtensions;");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("@SuppressWarnings(\"all\")");
    _builder_1.newLine();
    _builder_1.append("public class C {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("public static <T extends Object> ImmutableList<T> m(final List<? extends T> list) {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("final Function1<T, Boolean> _function = (T it) -> {");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("return Boolean.valueOf(true);");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("};");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("final Function1<T, Boolean> _function_1 = (T it) -> {");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("return Boolean.valueOf(false);");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("};");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("return ImmutableList.<T>builder().addAll(IterableExtensions.filter(list, _function)).addAll(IterableExtensions.filter(list, _function_1)).build();");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertCompilesTo(_builder, _builder_1);
  }

  @Test
  public void test_08() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.util.List");
    _builder.newLine();
    _builder.append("import com.google.common.collect.ImmutableList");
    _builder.newLine();
    _builder.newLine();
    _builder.append("class C {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("def static <T> m(T[] arr, T value) {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("ImmutableList.builder.addAll(arr.filter[it != value]).build");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import com.google.common.collect.ImmutableList;");
    _builder_1.newLine();
    _builder_1.append("import java.util.Objects;");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.xtext.xbase.lib.Conversions;");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.xtext.xbase.lib.Functions.Function1;");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.xtext.xbase.lib.IterableExtensions;");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("@SuppressWarnings(\"all\")");
    _builder_1.newLine();
    _builder_1.append("public class C {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("public static <T extends Object> ImmutableList<T> m(final T[] arr, final T value) {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("final Function1<T, Boolean> _function = (T it) -> {");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("return Boolean.valueOf((!Objects.equals(it, value)));");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("};");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("return ImmutableList.<T>builder().addAll(IterableExtensions.<T>filter(((Iterable<T>)Conversions.doWrapArray(arr)), _function)).build();");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertCompilesTo(_builder, _builder_1);
  }
}
