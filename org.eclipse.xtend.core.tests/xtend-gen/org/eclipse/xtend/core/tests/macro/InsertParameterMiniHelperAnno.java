/**
 * Copyright (c) 2023 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.xtend.core.tests.macro;

import org.eclipse.xtend.lib.macro.Active;

@Active(InsertParameterMiniHelperAnnoProcessor.class)
@SuppressWarnings("all")
public @interface InsertParameterMiniHelperAnno {
  public Class<?> classRef();
  public int position() default 0;
}
