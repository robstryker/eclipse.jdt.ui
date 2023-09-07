package org.eclipse.jdt.internal.ui.text.correction.proposals;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

import org.eclipse.jface.text.IDocument;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.NodeFinder;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.manipulation.CUCorrectionProposalCore;
import org.eclipse.jdt.core.manipulation.SharedASTProviderCore;

import org.eclipse.jdt.internal.corext.dom.LinkedNodeFinder;

public class RenameNodeCorrectionProposalCore extends CUCorrectionProposalCore {
	private String fNewName;
	private int fOffset;
	private int fLength;

	public RenameNodeCorrectionProposalCore(String name, ICompilationUnit cu, int offset, int length, String newName, int relevance) {
		super(name, cu, relevance);
		fOffset= offset;
		fLength= length;
		fNewName= newName;
	}

	@Override
	public void addEdits(IDocument doc, TextEdit root) throws CoreException {
		super.addEdits(doc, root);

		// build a full AST
		CompilationUnit unit= SharedASTProviderCore.getAST(getCompilationUnit(), SharedASTProviderCore.WAIT_YES, null);

		ASTNode name= NodeFinder.perform(unit, fOffset, fLength);
		if (name instanceof SimpleName) {

			SimpleName[] names= LinkedNodeFinder.findByProblems(unit, (SimpleName) name);
			if (names != null) {
				for (SimpleName curr : names) {
					root.addChild(new ReplaceEdit(curr.getStartPosition(), curr.getLength(), fNewName));
				}
				return;
			}
		}
		root.addChild(new ReplaceEdit(fOffset, fLength, fNewName));
	}
}