/**
 * Copyright (c) 2010, 2022 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.xtext.formatting2.regionaccess.internal.regionaccesstestlanguage;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Named</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xtext.formatting2.regionaccess.internal.regionaccesstestlanguage.Named#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xtext.formatting2.regionaccess.internal.regionaccesstestlanguage.RegionaccesstestlanguagePackage#getNamed()
 * @model
 * @generated
 */
public interface Named extends Expression
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.eclipse.xtext.formatting2.regionaccess.internal.regionaccesstestlanguage.RegionaccesstestlanguagePackage#getNamed_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.eclipse.xtext.formatting2.regionaccess.internal.regionaccesstestlanguage.Named#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

} // Named
