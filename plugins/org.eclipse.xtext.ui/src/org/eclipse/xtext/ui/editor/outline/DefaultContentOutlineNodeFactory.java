/*******************************************************************************
 * Copyright (c) 2009 Michael Clay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.xtext.ui.editor.outline;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.resource.EObjectHandleImpl;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.outline.actions.ContentOutlineNodeAdapter;
import org.eclipse.xtext.ui.editor.outline.actions.ContentOutlineNodeAdapterFactory;
import org.eclipse.xtext.util.TextLocation;
import org.eclipse.xtext.util.concurrent.IEObjectHandle;
import org.eclipse.xtext.util.concurrent.IStateAccess;

import com.google.inject.Inject;

/**
 * @author Michael Clay - Initial contribution and API
 */
public class DefaultContentOutlineNodeFactory implements IContentOutlineNodeFactory {

	@Inject
	@OutlineLabelProvider
	private ILabelProvider labelProvider;

	@Inject
	private ILocationInFileProvider locationProvider;

	public DefaultContentOutlineNodeFactory() {
		super();
	}

	public DefaultContentOutlineNodeFactory(ILabelProvider styledLabelProvider, ILocationInFileProvider locationProvider) {
		this.labelProvider = styledLabelProvider;
		this.locationProvider = locationProvider;
	}

	public ContentOutlineNode create(IStateAccess<XtextResource> resourceAccess, EObject semanticNode,
			ContentOutlineNode outlineParentNode) {
		ContentOutlineNode result = new ContentOutlineNode(getText(semanticNode), 
				getImage(semanticNode), getRegion(semanticNode), getEObjectHandle(semanticNode,
						resourceAccess), getEClass(semanticNode));

		if (outlineParentNode != null) {
			outlineParentNode.addChildren(result);
		}

		/*
		 * This adapter will be added to the semantic node in order to later be
		 * able to get the corresponding ContentOutlineNode. This is needed e.g.
		 * when we want to synch the outline with the currently selected editor
		 * position.
		 *
		 * XXX SZ: is this feasible? What if I want to create more than one one outline node
		 *         per semantic node? Can't we use the offset information of the outline node
		 *         and the editor to synchronize them?
		 *     PF: Reason for using an adapter was to not need to traverse a (more or less)
		 *         large part the outline model when syncing the outline to the editor selection.
		 *         However, SZ and I agreed on leaving it as-is and look into a tree traversal
		 *         approach if we run into memory problems.
		 */
		if (semanticNode != null) {
			ContentOutlineNodeAdapter outlineAdapter = (ContentOutlineNodeAdapter) ContentOutlineNodeAdapterFactory.INSTANCE
					.adapt(semanticNode, ContentOutlineNode.class);
			outlineAdapter.setContentOutlineNode(result);
		}

		return result;
	}

	protected EClass getEClass(EObject semanticNode) {
		if (null != semanticNode) {
			return semanticNode.eClass();
		}
		return null;
	}

	protected StyledString getText(EObject semanticNode) {
		StyledString text = (labelProvider instanceof IStyledLabelProvider) ? ((IStyledLabelProvider) labelProvider)
				.getStyledText(semanticNode) : new StyledString(labelProvider.getText(semanticNode));
		return text;
	}

	protected Image getImage(EObject semanticNode) {
		return labelProvider.getImage(semanticNode);
	}

	protected IRegion getRegion(EObject semanticNode) {
		TextLocation location = locationProvider.getLocation(semanticNode);
		return new Region(location.getOffset(), location.getLength());
	}

	protected IEObjectHandle<EObject> getEObjectHandle(EObject semanticNode, IStateAccess<XtextResource> resourceAccess) {
		if (null != semanticNode) {
			return new EObjectHandleImpl<EObject>(semanticNode, resourceAccess);
		}
		return null;
	}

	public ILabelProvider getLabelProvider() {
		return labelProvider;
	}

	public ILocationInFileProvider getLocationProvider() {
		return locationProvider;
	}
}
