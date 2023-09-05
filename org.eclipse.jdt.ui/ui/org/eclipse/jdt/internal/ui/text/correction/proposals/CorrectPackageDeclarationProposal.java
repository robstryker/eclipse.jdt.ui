/*******************************************************************************
 * Copyright (c) 2000, 2018 IBM Corporation and others.
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
 *******************************************************************************/

package org.eclipse.jdt.internal.ui.text.correction.proposals;

import org.eclipse.jdt.core.ICompilationUnit;

import org.eclipse.jdt.ui.text.java.IProblemLocation;
import org.eclipse.jdt.ui.text.java.correction.CUCorrectionProposal;

import org.eclipse.jdt.internal.ui.JavaPluginImages;
import org.eclipse.jdt.internal.ui.text.correction.CorrectionMessages;

public class CorrectPackageDeclarationProposal extends CUCorrectionProposal {

	public CorrectPackageDeclarationProposal(ICompilationUnit cu, IProblemLocation location, int relevance) {
		super(CorrectionMessages.CorrectPackageDeclarationProposal_name, cu, relevance, JavaPluginImages.get(JavaPluginImages.IMG_OBJS_PACKDECL));
		setDelegate(new CorrectPackageDeclarationProposalCore(cu, location, relevance));
	}
}