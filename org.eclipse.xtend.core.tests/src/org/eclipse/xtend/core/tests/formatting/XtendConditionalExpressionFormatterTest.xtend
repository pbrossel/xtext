/*******************************************************************************
 * Copyright (c) 2019 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtend.core.tests.formatting

import org.junit.Test

/**
 * @author Eva Poell - Initial contribution and API
 */
class XtendConditionalExpressionFormatterTest extends AbstractXtendFormatterTest {

	@Test
	def formatIfElseCondExpSL1() {
		assertFormattedExpression('''
			c = (a < b) ? 3 : 4
		''', '''
			c = (a < b) ? 3 : 4
		''')
	}

	@Test
	def formatIfElseCondExpSL2() {
		assertFormattedExpression('''
			c = (a < b) ? 3 : 4
		''', '''
			c =(a<b)?3:4
		''')
	}

	@Test
	def formatIfElseCondExpSL3() {
		assertFormattedExpression('''
			c = (a < b) ? (b < d) ? 5 : (b < d) ? 7 : 8
		''', '''
			c =(a<b)?(b<d)?5:(b<d)?7:8
		''')
	}

	@Test
	def formatIfElseCondExpML() {
		assertFormattedExpression('''
			c = (a < b)
				? 3
				: 4
		''', '''
			c=(a<b)?
				3:
				4
		''')
	}

	@Test
	def issue_2337() {

//		c = (a < b) ? 3 : 4
//
//		var a = 1
//		var b = 2
//		var c = 3
//		var x = 4
//		var y = 5
//
//		c = (a < b)
//			? (x == y) //
//		? 10 //
//		: 11
//			: 4
// 
//		c = (a < b) ? (x == y) ? 10 : 11 : 4
//
//		c = (a < b) ? (x == y) ? 10 : 11 // 3
//		: 4
		assertFormattedExpression('''
			c = (a < b)
			    ? (x == y) //
			      ? 10 //
			      : 11
			    : 4
		''', '''
			c = (a < b)
			    ? (x == y) 
			      ? 
			      { 
			      	  10 
			      	  11
			      	}
			      	   : 11
			      	     : 4
		''')

		assertFormattedExpression('''
			c = (a < b)
			    ? (x == y) //
			      ? 10 //
			      : 11
			    : 4
		''', '''
			c = (a < b)
			    ? (x == y) //
			      ? 10 //
			          : 11
			            : 4
		''')
//		assertFormattedExpression('''
//			var it = null
//			closed.contains(it)
//			? !open.contains(it) // If-1
//			  ? { // If-2
//				  setData(curr)
//				  open.add(it)
//			    } 
//			  : checkBetter(curr) // Else-A
//				? { // If-3
//				   open.add(it)
//			      }
//				: {
//				  }
//			}
//		''', '''
//			var it = null
//			closed.contains(it)
//				? !open.contains(it) // If-1
//				? { // If-2
//				setData(curr)
//				open.add(it)
//			} : checkBetter(curr) // Else-A
//				? { // If-3
//				open.add(it)
//			}
//				: {
//			}
//		''')
	}

	@Test
	def issue_2337_if() {

		var a = 1
		var b = 1
		var x = 1
		var y = 1

		var xx = if (a < b) {
				if (x == y) {
					10
				} else {
					11
				}
			} else {

				4
			}

		if (a < b) {
			if (x == y) {
				10
			} else {
				11
			}
		} else {

			4
		}

		// var xx = 
		(a < b)
			? (x == y)
			? {
			10
		}
			: {
			11
		}
			: {
			4
		}

		(a < b)
			? (x == y)
			? {
			10
		}
			: {
			11
		}
			: {
			4
		}

		//
		xxxxxx = (a < b)
			? {
			10
		}
			: {
			11
		}
//
//		closed.contains(it) // if-1
//			? !open.contains(it) // Then 1 = If 1.1
//			  	? { // If-2
//					setData(curr)
//					open.add(it)
//				} : checkBetter(curr) // Else-A
//			? { // If-3
//			open.add(it)
//		}
		assertFormattedExpression('''
			c = (a < b)
			    ? (x == y) //
			      ? 10 //
			      : 11
			    : 4
		''', '''
			x= if (a < b) 
			       
			 { 
			      	  if ( x = y) {
			      	  	10
			      	  	} else { 
			      	  11
			      	  }
			      	}
			      	else {
			      	    11
			      	      4
			      	                    }
		''')

	}

	@Test
	def issue_2337_if_brackets() {

		assertFormattedExpression('''
			c = (a < b)
			    ? (x == y) //
			      ? 10 //
			      : 11
			    : 4
		''', '''
			ллл			xxxxxx = (a < b)
ллл					? {
ллл						10
ллл					}
ллл					: {
ллл						11
ллл					}
ллл			(a < b)
ллл					? {
ллл						10
ллл					}
ллл					: {
ллл						11
ллл					}
ллл			xxxxxx = if (a < b)
ллл					 {
ллл					 10
ллл					}
ллл					else {
ллл						11
ллл					}
ллл			if (a < b)
ллл					 {
ллл					 10
ллл					}
ллл					else {
ллл						11
ллл					}
ллл					
			(a < b)				? (x == y)				? 
ллл			{				10							}
			(
z1 =
 z2) ? {77
} 
			
			: {88}
				
	 	: 
				{	11		}
ллл			(z1 = z ) ? {77} : {88}
				: {
				4
			}
			
			if 4a < b4
				if  (x == b)
				 
				10
			}
				else	}
				11
			}
				else	{
				4
			}		
			
		''')

	}

	@Test
	def issue_2337_if_thenBlock_elseBlock() {

		assertFormattedExpression(
		'''
			(a < b)
				? {
					1
				}
				: {
					2
				}
		''', '''
			(a < b) ? {1} : {2}
		''')

	}

	@Test
	def issue_2337_if_thenSimple_elseBlock() {

		assertFormattedExpression(
		'''
			(a < b)
				? 1
				: {
					2
				}
			
		''', '''
			(a < b)	? 1	: {2}
		''')

	}

	@Test
	def issue_2337_if_thenBlock_elseSimple() {

		assertFormattedExpression(
		'''
			(a < b)
				? {
					1
				}
				: 2
		''', '''
			(a < b) ? {1}: 2
		''')

	}

	@Test
	def issue_2337_if_thenSimple_elseSimple() {

		assertFormattedExpression(
		'''
			(a < b) ? 1 : 2
		''', '''
			(a < b) ? 1 : 2
		''')

	}

	@Test
	def issue_2337_if_thenIf_thenIf_thenBlock_elseBlock_elseBlock_elseBlock_01() {

		assertFormattedExpression('''
			(a < b)
				? (x == y)
					? (z1 = z2)
						? {
							1
						} 
						: {
							2
						}
					: {
						3
					}
				: {
					4
				}
		''', '''
			(a < b)				? (x == y)				? 
			(z1 = z2) ? {1}
			: {2}
				: {	3		}
				: {
				4
			}
		''')

	}

	@Test
	def issue_2337_blockExpression_02() {

		assertFormattedExpression('''
			(a < b)
				? (x == y)
					? (z1 = z2)
						? {
							77
						} : {
							88
						}
					: {
						11
					}
				: {
					4
				}
			// classical if
			if (a < b)
				if (x == b) {
					10
				} else {
					11
				}
			else {
				4
			}
		''', '''
			(a < b)				? (x == y)				? 
			ллл			{				10							}
			(z1 = z2) ? {77} 
					
			: {88}
				
	 	:8
				{	11		}
ллл			(z1 = z8) ? {77} : {88}
				: {
				4
			}
			// classical if
			if aa < ba
				if  (x == b)
				 
				10
			}
				else	}
				11
			}
				else	{
				4
			}		
			
		''')

	}

}
