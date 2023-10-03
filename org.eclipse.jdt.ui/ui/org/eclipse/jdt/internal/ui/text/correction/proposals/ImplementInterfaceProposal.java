/*******************************************************************************
 * Copyright (c) 2000, 2023 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Red Hat Inc - separate core logic from UI images
 *******************************************************************************/
package org.eclipse.jdt.internal.ui.text.correction.proposals;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import org.eclipse.jdt.internal.ui.JavaPluginImages;

public class ImplementInterfaceProposal extends LinkedCorrectionProposal {

	public ImplementInterfaceProposal(ICompilationUnit targetCU, ITypeBinding binding, CompilationUnit astRoot, ITypeBinding newInterface, int relevance) {
		super("", targetCU, null, relevance, JavaPluginImages.get(JavaPluginImages.IMG_CORRECTION_CHANGE), new ImplementInterfaceProposalCore(targetCU, binding, astRoot, newInterface, relevance)); //$NON-NLS-1$
	}

	@Override
	protected ASTRewrite getRewrite() throws CoreException {
		return ((ImplementInterfaceProposalCore) getDelegate()).getRewrite();
	}
}
