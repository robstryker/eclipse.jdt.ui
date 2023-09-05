package org.eclipse.jdt.internal.ui.text.correction.proposals;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

import org.eclipse.jface.text.IDocument;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.manipulation.CUCorrectionProposalCore;

public class ReplaceCorrectionProposalCore extends CUCorrectionProposalCore {
	private String fReplacementString;
	private int fOffset;
	private int fLength;

	public ReplaceCorrectionProposalCore(String name, ICompilationUnit cu, int offset, int length, String replacementString, int relevance) {
		super(name, cu, relevance);
		fReplacementString= replacementString;
		fOffset= offset;
		fLength= length;
	}

	@Override
	public void addEdits(IDocument doc, TextEdit rootEdit) throws CoreException {
		super.addEdits(doc, rootEdit);
		TextEdit edit= new ReplaceEdit(fOffset, fLength, fReplacementString);
		rootEdit.addChild(edit);
	}
}